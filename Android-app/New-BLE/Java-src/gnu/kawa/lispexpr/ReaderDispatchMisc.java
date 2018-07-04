package gnu.kawa.lispexpr;

import gnu.bytecode.Access;
import gnu.bytecode.Type;
import gnu.expr.Keyword;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.util.GeneralHashTable;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.InPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.regex.Pattern;

public class ReaderDispatchMisc extends ReadTableEntry {
    private static ReaderDispatchMisc instance = new ReaderDispatchMisc();
    protected int code;

    public static ReaderDispatchMisc getInstance() {
        return instance;
    }

    public ReaderDispatchMisc() {
        this.code = -1;
    }

    public ReaderDispatchMisc(int code) {
        this.code = code;
    }

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        String name;
        LispReader reader = (LispReader) in;
        char saveReadState = '\u0000';
        if (this.code >= 0) {
            ch = this.code;
        }
        GeneralHashTable<Integer, Object> map;
        LineBufferedReader port;
        switch (ch) {
            case 33:
                return LispReader.readSpecial(reader);
            case 35:
                if (in instanceof LispReader) {
                    map = ((LispReader) in).sharedStructureTable;
                    if (map != null) {
                        Lexer object = map.get(Integer.valueOf(count), in);
                        if (object != in) {
                            return object;
                        }
                    }
                }
                in.error("an unrecognized #n# back-reference was read");
                return Values.empty;
            case 44:
                if (reader.getPort().peek() == 40) {
                    Object list = reader.readObject();
                    int length = LList.listLength(list, false);
                    if (length > 0 && (((Pair) list).getCar() instanceof Symbol)) {
                        name = ((Pair) list).getCar().toString();
                        Object proc = ReadTable.getCurrent().getReaderCtor(name);
                        if (proc == null) {
                            in.error("unknown reader constructor " + name);
                        } else if ((proc instanceof Procedure) || (proc instanceof Type)) {
                            length--;
                            int parg = proc instanceof Type ? 1 : 0;
                            Object[] args = new Object[(parg + length)];
                            Pair argList = ((Pair) list).getCdr();
                            for (int i = 0; i < length; i++) {
                                Pair pair = argList;
                                args[parg + i] = pair.getCar();
                                argList = pair.getCdr();
                            }
                            if (parg <= 0) {
                                return ((Procedure) proc).applyN(args);
                            }
                            try {
                                args[0] = proc;
                                return Invoke.make.applyN(args);
                            } catch (Throwable ex) {
                                in.error("caught " + ex + " applying reader constructor " + name);
                            }
                        } else {
                            in.error("reader constructor must be procedure or type name");
                        }
                        return Boolean.FALSE;
                    }
                }
                in.error("a non-empty list starting with a symbol must follow #,");
                return Boolean.FALSE;
            case 47:
                return readRegex(in, ch, count);
            case 58:
                int startPos = reader.tokenBufferLength;
                reader.readToken(reader.read(), 'P', ReadTable.getCurrent());
                name = new String(reader.tokenBuffer, startPos, reader.tokenBufferLength - startPos);
                reader.tokenBufferLength = startPos;
                return Keyword.make(name.intern());
            case 59:
                port = reader.getPort();
                if (port instanceof InPort) {
                    saveReadState = ((InPort) port).readState;
                    ((InPort) port).readState = ';';
                }
                try {
                    reader.readObject();
                    return Values.empty;
                } finally {
                    if (port instanceof InPort) {
                        ((InPort) port).readState = saveReadState;
                    }
                }
            case 61:
                Object object2 = reader.readObject();
                if (!(in instanceof LispReader)) {
                    return object2;
                }
                LispReader lin = (LispReader) in;
                map = lin.sharedStructureTable;
                if (map == null) {
                    map = new GeneralHashTable();
                    lin.sharedStructureTable = map;
                }
                map.put(Integer.valueOf(count), object2);
                return object2;
            case 66:
                return LispReader.readNumberWithRadix(0, reader, 2);
            case 68:
                return LispReader.readNumberWithRadix(0, reader, 10);
            case 69:
            case 73:
                reader.tokenBufferAppend(35);
                reader.tokenBufferAppend(ch);
                return LispReader.readNumberWithRadix(2, reader, 0);
            case 70:
                if (Character.isDigit((char) in.peek())) {
                    return LispReader.readSimpleVector(reader, Access.FIELD_CONTEXT);
                }
                return Boolean.FALSE;
            case 79:
                return LispReader.readNumberWithRadix(0, reader, 8);
            case 82:
                if (count > 36) {
                    in.error("the radix " + count + " is too big (max is 36)");
                    count = 36;
                }
                return LispReader.readNumberWithRadix(0, reader, count);
            case 83:
            case 85:
                return LispReader.readSimpleVector(reader, (char) ch);
            case 84:
                return Boolean.TRUE;
            case 88:
                return LispReader.readNumberWithRadix(0, reader, 16);
            case 92:
                return LispReader.readCharacter(reader);
            case 124:
                port = reader.getPort();
                if (port instanceof InPort) {
                    saveReadState = ((InPort) port).readState;
                    ((InPort) port).readState = '|';
                }
                try {
                    reader.readNestedComment('#', '|');
                    return Values.empty;
                } finally {
                    if (port instanceof InPort) {
                        ((InPort) port).readState = saveReadState;
                    }
                }
            default:
                in.error("An invalid #-construct was read.");
                return Values.empty;
        }
    }

    public static Pattern readRegex(Lexer in, int ch, int count) throws IOException, SyntaxException {
        int startPos = in.tokenBufferLength;
        LineBufferedReader port = in.getPort();
        char saveReadState = '\u0000';
        int flags = 0;
        if (port instanceof InPort) {
            saveReadState = ((InPort) port).readState;
            ((InPort) port).readState = '/';
        }
        while (true) {
            int c = port.read();
            if (c < 0) {
                in.eofError("unexpected EOF in regex literal");
            }
            if (c == ch) {
                break;
            }
            if (c == 92) {
                try {
                    c = port.read();
                    if ((c == 32 || c == 9 || c == 13 || c == 10) && (in instanceof LispReader)) {
                        c = ((LispReader) in).readEscape(c);
                        if (c == -2) {
                        }
                    }
                    if (c < 0) {
                        in.eofError("unexpected EOF in regex literal");
                    }
                    if (c != ch) {
                        in.tokenBufferAppend(92);
                    }
                } finally {
                    in.tokenBufferLength = startPos;
                    if (port instanceof InPort) {
                        ((InPort) port).readState = saveReadState;
                    }
                }
            }
            in.tokenBufferAppend(c);
        }
        String pattern = new String(in.tokenBuffer, startPos, in.tokenBufferLength - startPos);
        while (true) {
            c = in.peek();
            if (c != 105 && c != 73) {
                if (c != 115 && c != 83) {
                    if (c != 109 && c != 77) {
                        if (!Character.isLetter(c)) {
                            break;
                        }
                        in.error("unrecognized regex option '" + ((char) c) + '\'');
                    } else {
                        flags |= 8;
                    }
                } else {
                    flags |= 32;
                }
            } else {
                flags |= 66;
            }
            in.skip();
        }
        Pattern compile = Pattern.compile(pattern, flags);
        return compile;
    }
}
