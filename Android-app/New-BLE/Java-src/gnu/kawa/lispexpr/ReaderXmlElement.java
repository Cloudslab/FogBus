package gnu.kawa.lispexpr;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.PrimProcedure;
import gnu.expr.Special;
import gnu.kawa.xml.CommentConstructor;
import gnu.kawa.xml.MakeAttribute;
import gnu.kawa.xml.MakeCDATA;
import gnu.kawa.xml.MakeProcInst;
import gnu.kawa.xml.MakeText;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Namespace;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import gnu.xml.XName;
import java.io.IOException;

public class ReaderXmlElement extends ReadTableEntry {
    static final String DEFAULT_ELEMENT_NAMESPACE = "[default-element-namespace]";

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        LispReader reader = (LispReader) in;
        return readXMLConstructor(reader, reader.readUnicodeChar(), false);
    }

    public static Pair quote(Object obj) {
        return LList.list2(Namespace.EmptyNamespace.getSymbol(LispLanguage.quote_sym), obj);
    }

    public static Object readQNameExpression(LispReader reader, int ch, boolean forElement) throws IOException, SyntaxException {
        String file = reader.getName();
        int line = reader.getLineNumber() + 1;
        int column = reader.getColumnNumber();
        reader.tokenBufferLength = 0;
        if (XName.isNameStart(ch)) {
            int colon = -1;
            while (true) {
                reader.tokenBufferAppend(ch);
                ch = reader.readUnicodeChar();
                if (ch == 58 && colon < 0) {
                    colon = reader.tokenBufferLength;
                } else if (!XName.isNamePart(ch)) {
                    break;
                }
            }
            reader.unread(ch);
            if (colon < 0 && !forElement) {
                return quote(Namespace.getDefaultSymbol(reader.tokenBufferString().intern()));
            }
            String prefix;
            String local = new String(reader.tokenBuffer, colon + 1, (reader.tokenBufferLength - colon) - 1).intern();
            if (colon < 0) {
                prefix = DEFAULT_ELEMENT_NAMESPACE;
            } else {
                prefix = new String(reader.tokenBuffer, 0, colon).intern();
            }
            return new Pair(ResolveNamespace.resolveQName, PairWithPosition.make(Namespace.EmptyNamespace.getSymbol(prefix), new Pair(local, LList.Empty), reader.getName(), line, column));
        } else if (ch == 123 || ch == 40) {
            return readEscapedExpression(reader, ch);
        } else {
            reader.error("missing element name");
            return null;
        }
    }

    static Object readEscapedExpression(LispReader reader, int ch) throws IOException, SyntaxException {
        if (ch == 40) {
            reader.unread(ch);
            return reader.readObject();
        }
        LineBufferedReader port = reader.getPort();
        char saveReadState = reader.pushNesting('{');
        int startLine = port.getLineNumber();
        int startColumn = port.getColumnNumber();
        try {
            Object makePair = reader.makePair(new PrimProcedure(Compilation.typeValues.getDeclaredMethod("values", 1)), startLine, startColumn);
            Pair last = makePair;
            ReadTable readTable = ReadTable.getCurrent();
            while (true) {
                int line = port.getLineNumber();
                int column = port.getColumnNumber();
                ch = port.read();
                if (ch == 125) {
                    break;
                }
                if (ch < 0) {
                    reader.eofError("unexpected EOF in list starting here", startLine + 1, startColumn);
                }
                Values value = reader.readValues(ch, readTable.lookup(ch), readTable);
                if (value != Values.empty) {
                    Pair pair = reader.makePair(reader.handlePostfix(value, readTable, line, column), line, column);
                    reader.setCdr(last, pair);
                    last = pair;
                }
            }
            reader.tokenBufferLength = 0;
            if (last == makePair.getCdr()) {
                makePair = last.getCar();
                return makePair;
            }
            reader.popNesting(saveReadState);
            return makePair;
        } finally {
            reader.popNesting(saveReadState);
        }
    }

    static Object readXMLConstructor(LispReader reader, int next, boolean inElementContent) throws IOException, SyntaxException {
        int startLine = reader.getLineNumber() + 1;
        int startColumn = reader.getColumnNumber() - 2;
        if (next == 33) {
            next = reader.read();
            if (next == 45) {
                next = reader.peek();
                if (next == 45) {
                    reader.skip();
                    if (!reader.readDelimited("-->")) {
                        reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file in XML comment starting here - expected \"-->\"");
                    }
                    return LList.list2(CommentConstructor.commentConstructor, reader.tokenBufferString());
                }
            }
            if (next == 91) {
                next = reader.read();
                if (next == 67) {
                    next = reader.read();
                    if (next == 68) {
                        next = reader.read();
                        if (next == 65) {
                            next = reader.read();
                            if (next == 84) {
                                next = reader.read();
                                if (next == 65) {
                                    next = reader.read();
                                    if (next == 91) {
                                        if (!reader.readDelimited("]]>")) {
                                            reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file in CDATA starting here - expected \"]]>\"");
                                        }
                                        return LList.list2(MakeCDATA.makeCDATA, reader.tokenBufferString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            reader.error('f', reader.getName(), startLine, startColumn, "'<!' must be followed by '--' or '[CDATA['");
            while (next >= 0 && next != 62 && next != 10 && next != 13) {
                next = reader.read();
            }
            return null;
        } else if (next != 63) {
            return readElementConstructor(reader, next);
        } else {
            next = reader.readUnicodeChar();
            if (next < 0 || !XName.isNameStart(next)) {
                reader.error("missing target after '<?'");
            }
            do {
                reader.tokenBufferAppend(next);
                next = reader.readUnicodeChar();
            } while (XName.isNamePart(next));
            String target = reader.tokenBufferString();
            int nspaces = 0;
            while (next >= 0 && Character.isWhitespace(next)) {
                nspaces++;
                next = reader.read();
            }
            reader.unread(next);
            char saveReadState = reader.pushNesting('?');
            try {
                if (!reader.readDelimited("?>")) {
                    reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file looking for \"?>\"");
                }
                reader.popNesting(saveReadState);
                if (nspaces == 0 && reader.tokenBufferLength > 0) {
                    reader.error("target must be followed by space or '?>'");
                }
                return LList.list3(MakeProcInst.makeProcInst, target, reader.tokenBufferString());
            } catch (Throwable th) {
                reader.popNesting(saveReadState);
            }
        }
    }

    public static Object readElementConstructor(LispReader reader, int ch) throws IOException, SyntaxException {
        boolean empty;
        int startLine = reader.getLineNumber() + 1;
        int startColumn = reader.getColumnNumber() - 2;
        Object tag = readQNameExpression(reader, ch, true);
        String startTag = reader.tokenBufferLength == 0 ? null : reader.tokenBufferString();
        Pair tagPair = PairWithPosition.make(tag, LList.Empty, reader.getName(), startLine, startColumn);
        Pair resultTail = tagPair;
        LList namespaceList = LList.Empty;
        while (true) {
            boolean sawSpace = false;
            ch = reader.readUnicodeChar();
            while (ch >= 0 && Character.isWhitespace(ch)) {
                ch = reader.read();
                sawSpace = true;
            }
            if (ch < 0 || ch == 62 || ch == 47) {
                empty = false;
            } else {
                if (!sawSpace) {
                    reader.error("missing space before attribute");
                }
                Object attrName = readQNameExpression(reader, ch, false);
                int line = reader.getLineNumber() + 1;
                int column = (reader.getColumnNumber() + 1) - reader.tokenBufferLength;
                String definingNamespace = null;
                if (reader.tokenBufferLength >= 5 && reader.tokenBuffer[0] == 'x' && reader.tokenBuffer[1] == 'm' && reader.tokenBuffer[2] == 'l' && reader.tokenBuffer[3] == 'n' && reader.tokenBuffer[4] == 's') {
                    if (reader.tokenBufferLength == 5) {
                        definingNamespace = "";
                    } else if (reader.tokenBuffer[5] == ':') {
                        definingNamespace = new String(reader.tokenBuffer, 6, reader.tokenBufferLength - 6);
                    }
                }
                if (skipSpace(reader, 32) != 61) {
                    reader.error("missing '=' after attribute");
                }
                ch = skipSpace(reader, 32);
                Pair attrList = PairWithPosition.make(MakeAttribute.makeAttribute, LList.Empty, reader.getName(), startLine, startColumn);
                Pair attrTail = attrList;
                Pair attrPair = PairWithPosition.make(attrName, LList.Empty, reader.getName(), startLine, startColumn);
                reader.setCdr(attrTail, attrPair);
                attrTail = readContent(reader, (char) ch, attrPair);
                if (definingNamespace != null) {
                    namespaceList = new PairWithPosition(attrPair, Pair.make(definingNamespace, attrPair.getCdr()), namespaceList);
                } else {
                    Pair pair = PairWithPosition.make(attrList, reader.makeNil(), null, -1, -1);
                    resultTail.setCdrBackdoor(pair);
                    resultTail = pair;
                }
            }
        }
        empty = false;
        if (ch == 47) {
            ch = reader.read();
            if (ch == 62) {
                empty = true;
            } else {
                reader.unread(ch);
            }
        }
        if (!empty) {
            if (ch != 62) {
                reader.error("missing '>' after start element");
                return Boolean.FALSE;
            }
            resultTail = readContent(reader, '<', resultTail);
            ch = reader.readUnicodeChar();
            if (XName.isNameStart(ch)) {
                reader.tokenBufferLength = 0;
                while (true) {
                    reader.tokenBufferAppend(ch);
                    ch = reader.readUnicodeChar();
                    if (!XName.isNamePart(ch) && ch != 58) {
                        break;
                    }
                }
                String endTag = reader.tokenBufferString();
                if (startTag == null || !endTag.equals(startTag)) {
                    reader.error('e', reader.getName(), reader.getLineNumber() + 1, reader.getColumnNumber() - reader.tokenBufferLength, startTag == null ? "computed start tag closed by '</" + endTag + ">'" : "'<" + startTag + ">' closed by '</" + endTag + ">'");
                }
                reader.tokenBufferLength = 0;
            }
            if (skipSpace(reader, ch) != 62) {
                reader.error("missing '>' after end element");
            }
        }
        return PairWithPosition.make(MakeXmlElement.makeXml, Pair.make(LList.reverseInPlace(namespaceList), tagPair), reader.getName(), startLine, startColumn);
    }

    public static Pair readContent(LispReader reader, char delimiter, Pair resultTail) throws IOException, SyntaxException {
        reader.tokenBufferLength = 0;
        boolean prevWasEnclosed = false;
        while (true) {
            Object obj = null;
            String text = null;
            int line = reader.getLineNumber() + 1;
            int column = reader.getColumnNumber();
            int next = reader.read();
            if (next < 0) {
                reader.error("unexpected end-of-file");
                obj = Special.eof;
            } else if (next == delimiter) {
                if (delimiter == '<') {
                    if (reader.tokenBufferLength > 0) {
                        text = reader.tokenBufferString();
                        reader.tokenBufferLength = 0;
                    }
                    next = reader.read();
                    if (next == 47) {
                        obj = Special.eof;
                    } else {
                        obj = readXMLConstructor(reader, next, true);
                    }
                } else if (reader.checkNext(delimiter)) {
                    reader.tokenBufferAppend(delimiter);
                } else {
                    obj = Special.eof;
                }
                prevWasEnclosed = false;
            } else if (next == '&') {
                next = reader.read();
                if (next == 35) {
                    readCharRef(reader);
                } else if (next == 40 || next == 123) {
                    if (reader.tokenBufferLength > 0 || prevWasEnclosed) {
                        text = reader.tokenBufferString();
                    }
                    reader.tokenBufferLength = 0;
                    obj = readEscapedExpression(reader, next);
                } else {
                    obj = readEntity(reader, next);
                    if (prevWasEnclosed && reader.tokenBufferLength == 0) {
                        text = "";
                    }
                }
                prevWasEnclosed = true;
            } else {
                if (delimiter != '<' && (next == '\t' || next == '\n' || next == '\r')) {
                    next = 32;
                }
                if (next == 60) {
                    reader.error('e', "'<' must be quoted in a direct attribute value");
                }
                reader.tokenBufferAppend((char) next);
            }
            if (obj != null && reader.tokenBufferLength > 0) {
                text = reader.tokenBufferString();
                reader.tokenBufferLength = 0;
            }
            if (text != null) {
                Pair pair = PairWithPosition.make(LList.list2(MakeText.makeText, text), reader.makeNil(), null, -1, -1);
                resultTail.setCdrBackdoor(pair);
                resultTail = pair;
            }
            if (obj == Special.eof) {
                return resultTail;
            }
            if (obj != null) {
                pair = PairWithPosition.make(obj, reader.makeNil(), null, line, column);
                resultTail.setCdrBackdoor(pair);
                resultTail = pair;
            }
        }
    }

    static void readCharRef(LispReader reader) throws IOException, SyntaxException {
        int base;
        int next = reader.read();
        if (next == 120) {
            base = 16;
            next = reader.read();
        } else {
            base = 10;
        }
        int value = 0;
        while (next >= 0) {
            int digit = Character.digit((char) next, base);
            if (digit < 0 || value >= Declaration.PACKAGE_ACCESS) {
                break;
            }
            value = (value * base) + digit;
            next = reader.read();
        }
        if (next != 59) {
            reader.unread(next);
            reader.error("invalid character reference");
        } else if ((value <= 0 || value > 55295) && ((value < 57344 || value > 65533) && (value < 65536 || value > 1114111))) {
            reader.error("invalid character value " + value);
        } else {
            reader.tokenBufferAppend(value);
        }
    }

    static Object readEntity(LispReader reader, int next) throws IOException, SyntaxException {
        String result = "?";
        int saveLength = reader.tokenBufferLength;
        while (next >= 0) {
            char ch = (char) next;
            if (!XName.isNamePart(ch)) {
                break;
            }
            reader.tokenBufferAppend(ch);
            next = reader.read();
        }
        if (next != 59) {
            reader.unread(next);
            reader.error("invalid entity reference");
            return result;
        }
        String ref = new String(reader.tokenBuffer, saveLength, reader.tokenBufferLength - saveLength);
        reader.tokenBufferLength = saveLength;
        namedEntity(reader, ref);
        return null;
    }

    public static void namedEntity(LispReader reader, String name) {
        name = name.intern();
        char ch = '?';
        if (name == "lt") {
            ch = '<';
        } else if (name == "gt") {
            ch = '>';
        } else if (name == "amp") {
            ch = '&';
        } else if (name == "quot") {
            ch = '\"';
        } else if (name == "apos") {
            ch = '\'';
        } else {
            reader.error("unknown enity reference: '" + name + "'");
        }
        reader.tokenBufferAppend(ch);
    }

    static int skipSpace(LispReader reader, int ch) throws IOException, SyntaxException {
        while (ch >= 0 && Character.isWhitespace(ch)) {
            ch = reader.readUnicodeChar();
        }
        return ch;
    }
}
