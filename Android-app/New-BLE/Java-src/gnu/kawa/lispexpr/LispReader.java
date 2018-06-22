package gnu.kawa.lispexpr;

import android.support.v4.internal.view.SupportMenu;
import gnu.bytecode.Access;
import gnu.expr.Keyword;
import gnu.expr.QuoteExp;
import gnu.expr.Special;
import gnu.kawa.util.GeneralHashTable;
import gnu.lists.Convert;
import gnu.lists.F32Vector;
import gnu.lists.F64Vector;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.S16Vector;
import gnu.lists.S32Vector;
import gnu.lists.S64Vector;
import gnu.lists.S8Vector;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import gnu.lists.U16Vector;
import gnu.lists.U32Vector;
import gnu.lists.U64Vector;
import gnu.lists.U8Vector;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.math.Complex;
import gnu.math.DComplex;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.RatNum;
import gnu.math.RealNum;
import gnu.text.Char;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.math.BigDecimal;

public class LispReader extends Lexer {
    static final int SCM_COMPLEX = 1;
    public static final int SCM_NUMBERS = 1;
    public static final char TOKEN_ESCAPE_CHAR = '￿';
    protected boolean seenEscapes;
    GeneralHashTable<Integer, Object> sharedStructureTable;

    public LispReader(LineBufferedReader port) {
        super(port);
    }

    public LispReader(LineBufferedReader port, SourceMessages messages) {
        super(port, messages);
    }

    public final void readNestedComment(char c1, char c2) throws IOException, SyntaxException {
        int commentNesting = 1;
        int startLine = this.port.getLineNumber();
        int startColumn = this.port.getColumnNumber();
        do {
            int c;
            char c3 = read();
            if (c3 == '|') {
                c = read();
                if (c == c1) {
                    commentNesting--;
                }
            } else if (c3 == c1) {
                c3 = read();
                if (c3 == c2) {
                    commentNesting++;
                }
            }
            if (c < 0) {
                eofError("unexpected end-of-file in " + c1 + c2 + " comment starting here", startLine + 1, startColumn - 1);
                return;
            }
        } while (commentNesting > 0);
    }

    static char getReadCase() {
        try {
            char read_case = Environment.getCurrent().get("symbol-read-case", (Object) "P").toString().charAt(0);
            if (read_case == 'P') {
                return read_case;
            }
            if (read_case == 'u') {
                return 'U';
            }
            if (read_case == 'd' || read_case == 'l' || read_case == 'L') {
                return 'D';
            }
            if (read_case == 'i') {
                return Access.INNERCLASS_CONTEXT;
            }
            return read_case;
        } catch (Exception e) {
            return 'P';
        }
    }

    public Object readValues(int ch, ReadTable rtable) throws IOException, SyntaxException {
        return readValues(ch, rtable.lookup(ch), rtable);
    }

    public Object readValues(int ch, ReadTableEntry entry, ReadTable rtable) throws IOException, SyntaxException {
        int startPos = this.tokenBufferLength;
        this.seenEscapes = false;
        switch (entry.getKind()) {
            case 0:
                String err = "invalid character #\\" + ((char) ch);
                if (this.interactive) {
                    fatal(err);
                } else {
                    error(err);
                }
                return Values.empty;
            case 1:
                return Values.empty;
            case 5:
            case 6:
                return entry.read(this, ch, -1);
            default:
                return readAndHandleToken(ch, startPos, rtable);
        }
    }

    protected Object readAndHandleToken(int ch, int startPos, ReadTable rtable) throws IOException, SyntaxException {
        int i;
        char ci;
        readToken(ch, getReadCase(), rtable);
        int endPos = this.tokenBufferLength;
        if (!this.seenEscapes) {
            Object value = parseNumber(this.tokenBuffer, startPos, endPos - startPos, '\u0000', 0, 1);
            if (!(value == null || (value instanceof String))) {
                return value;
            }
        }
        char readCase = getReadCase();
        if (readCase == 'I') {
            int upperCount = 0;
            int lowerCount = 0;
            i = startPos;
            while (i < endPos) {
                ci = this.tokenBuffer[i];
                if (ci == TOKEN_ESCAPE_CHAR) {
                    i++;
                } else if (Character.isLowerCase(ci)) {
                    lowerCount++;
                } else if (Character.isUpperCase(ci)) {
                    upperCount++;
                }
                i++;
            }
            if (lowerCount == 0) {
                readCase = 'D';
            } else if (upperCount == 0) {
                readCase = 'U';
            } else {
                readCase = 'P';
            }
        }
        boolean handleUri = endPos >= startPos + 2 && this.tokenBuffer[endPos - 1] == '}' && this.tokenBuffer[endPos - 2] != TOKEN_ESCAPE_CHAR && peek() == 58;
        int packageMarker = -1;
        int lbrace = -1;
        int rbrace = -1;
        int braceNesting = 0;
        i = startPos;
        int j = startPos;
        while (i < endPos) {
            int j2;
            ci = this.tokenBuffer[i];
            if (ci == TOKEN_ESCAPE_CHAR) {
                i++;
                if (i < endPos) {
                    j2 = j + 1;
                    this.tokenBuffer[j] = this.tokenBuffer[i];
                } else {
                    j2 = j;
                }
            } else {
                if (handleUri) {
                    if (ci == '{') {
                        if (lbrace < 0) {
                            lbrace = j;
                        } else if (braceNesting == 0) {
                        }
                        braceNesting++;
                    } else if (ci == '}') {
                        braceNesting--;
                        if (braceNesting >= 0) {
                            if (braceNesting == 0) {
                                if (rbrace < 0) {
                                    rbrace = j;
                                }
                            }
                        }
                    }
                }
                if (braceNesting <= 0) {
                    if (ci == ':') {
                        packageMarker = packageMarker >= 0 ? -1 : j;
                    } else if (readCase == 'U') {
                        ci = Character.toUpperCase(ci);
                    } else if (readCase == 'D') {
                        ci = Character.toLowerCase(ci);
                    }
                }
                j2 = j + 1;
                this.tokenBuffer[j] = ci;
            }
            i++;
            j = j2;
        }
        endPos = j;
        int len = endPos - startPos;
        if (lbrace >= 0 && rbrace > lbrace) {
            String str;
            String prefix;
            if (lbrace > 0) {
                str = new String(this.tokenBuffer, startPos, lbrace - startPos);
            } else {
                prefix = null;
            }
            lbrace++;
            str = new String(this.tokenBuffer, lbrace, rbrace - lbrace);
            ch = read();
            ch = read();
            Object rightOperand = readValues(ch, rtable.lookup(ch), rtable);
            if (!(rightOperand instanceof SimpleSymbol)) {
                error("expected identifier in symbol after '{URI}:'");
            }
            return Symbol.valueOf(rightOperand.toString(), str, prefix);
        } else if (rtable.initialColonIsKeyword && packageMarker == startPos && len > 1) {
            startPos++;
            return Keyword.make(new String(this.tokenBuffer, startPos, endPos - startPos).intern());
        } else if (rtable.finalColonIsKeyword && packageMarker == endPos - 1 && (len > 1 || this.seenEscapes)) {
            return Keyword.make(new String(this.tokenBuffer, startPos, len - 1).intern());
        } else {
            return rtable.makeSymbol(new String(this.tokenBuffer, startPos, len));
        }
    }

    void readToken(int ch, char readCase, ReadTable rtable) throws IOException, SyntaxException {
        boolean inEscapes = false;
        int braceNesting = 0;
        while (true) {
            if (ch < '\u0000') {
                if (inEscapes) {
                    eofError("unexpected EOF between escapes");
                } else {
                    return;
                }
            }
            ReadTableEntry entry = rtable.lookup(ch);
            int kind = entry.getKind();
            if (kind != 0) {
                if (ch == rtable.postfixLookupOperator && !inEscapes) {
                    char next = this.port.peek();
                    if (next == rtable.postfixLookupOperator) {
                        unread(ch);
                        return;
                    } else if (validPostfixLookupStart(next, rtable)) {
                        kind = 5;
                    }
                }
                if (kind == 3) {
                    ch = read();
                    if (ch < 0) {
                        eofError("unexpected EOF after single escape");
                    }
                    if (rtable.hexEscapeAfterBackslash && (ch == 120 || ch == 88)) {
                        ch = readHexEscape();
                    }
                    tokenBufferAppend(SupportMenu.USER_MASK);
                    tokenBufferAppend(ch);
                    this.seenEscapes = true;
                } else if (kind == 4) {
                    inEscapes = !inEscapes;
                    this.seenEscapes = true;
                } else if (inEscapes) {
                    tokenBufferAppend(SupportMenu.USER_MASK);
                    tokenBufferAppend(ch);
                } else {
                    switch (kind) {
                        case 1:
                            unread(ch);
                            return;
                        case 2:
                            if (ch == '{' && entry == ReadTableEntry.brace) {
                                braceNesting++;
                                break;
                            }
                        case 4:
                            inEscapes = true;
                            this.seenEscapes = true;
                            continue;
                        case 5:
                            unread(ch);
                            return;
                        case 6:
                            break;
                        default:
                            continue;
                    }
                    tokenBufferAppend(ch);
                }
            } else if (inEscapes) {
                tokenBufferAppend(SupportMenu.USER_MASK);
                tokenBufferAppend(ch);
            } else {
                if (ch == '}') {
                    braceNesting--;
                    if (braceNesting >= 0) {
                        tokenBufferAppend(ch);
                    }
                }
                unread(ch);
                return;
            }
            char ch2 = read();
        }
    }

    public Object readObject() throws IOException, SyntaxException {
        char saveReadState = ((InPort) this.port).readState;
        int startPos = this.tokenBufferLength;
        ((InPort) this.port).readState = ' ';
        try {
            Values value;
            int line;
            int column;
            Object obj;
            ReadTable rtable = ReadTable.getCurrent();
            do {
                line = this.port.getLineNumber();
                column = this.port.getColumnNumber();
                int ch = this.port.read();
                if (ch < 0) {
                    obj = Sequence.eofValue;
                    this.tokenBufferLength = startPos;
                    ((InPort) this.port).readState = saveReadState;
                    return obj;
                }
                value = readValues(ch, rtable);
            } while (value == Values.empty);
            obj = handlePostfix(value, rtable, line, column);
            this.tokenBufferLength = startPos;
            ((InPort) this.port).readState = saveReadState;
            return obj;
        } catch (Throwable th) {
            Throwable th2 = th;
            this.tokenBufferLength = startPos;
            ((InPort) this.port).readState = saveReadState;
        }
    }

    protected boolean validPostfixLookupStart(int ch, ReadTable rtable) throws IOException {
        if (ch < 0 || ch == 58 || ch == rtable.postfixLookupOperator) {
            return false;
        }
        if (ch == 44) {
            return true;
        }
        int kind = rtable.lookup(ch).getKind();
        if (kind == 2 || kind == 6 || kind == 4 || kind == 3) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    java.lang.Object handlePostfix(java.lang.Object r8, gnu.kawa.lispexpr.ReadTable r9, int r10, int r11) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r7 = this;
        r3 = gnu.expr.QuoteExp.voidExp;
        if (r8 != r3) goto L_0x0006;
    L_0x0004:
        r8 = gnu.mapping.Values.empty;
    L_0x0006:
        r3 = r7.port;
        r0 = r3.peek();
        if (r0 < 0) goto L_0x0012;
    L_0x000e:
        r3 = r9.postfixLookupOperator;
        if (r0 == r3) goto L_0x0013;
    L_0x0012:
        return r8;
    L_0x0013:
        r3 = r7.port;
        r3.read();
        r3 = r7.port;
        r1 = r3.peek();
        r3 = r7.validPostfixLookupStart(r1, r9);
        if (r3 != 0) goto L_0x0028;
    L_0x0024:
        r7.unread();
        goto L_0x0012;
    L_0x0028:
        r3 = r7.port;
        r0 = r3.read();
        r3 = r9.lookup(r0);
        r2 = r7.readValues(r0, r3, r9);
        r3 = "quasiquote";
        r3 = r9.makeSymbol(r3);
        r3 = gnu.lists.LList.list2(r3, r2);
        r8 = gnu.lists.LList.list2(r8, r3);
        r3 = gnu.kawa.lispexpr.LispLanguage.lookup_sym;
        r4 = r7.port;
        r4 = r4.getName();
        r5 = r10 + 1;
        r6 = r11 + 1;
        r8 = gnu.lists.PairWithPosition.make(r3, r8, r4, r5, r6);
        goto L_0x0006;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.LispReader.handlePostfix(java.lang.Object, gnu.kawa.lispexpr.ReadTable, int, int):java.lang.Object");
    }

    private boolean isPotentialNumber(char[] buffer, int start, int end) {
        boolean z = true;
        int sawDigits = 0;
        for (int i = start; i < end; i++) {
            char ch = buffer[i];
            if (Character.isDigit(ch)) {
                sawDigits++;
            } else if (ch == '-' || ch == '+') {
                if (i + 1 == end) {
                    return false;
                }
            } else if (ch == '#') {
                return true;
            } else {
                if (Character.isLetter(ch) || ch == '/' || ch == '_' || ch == '^') {
                    if (i == start) {
                        return false;
                    }
                } else if (ch != '.') {
                    return false;
                }
            }
        }
        if (sawDigits <= 0) {
            z = false;
        }
        return z;
    }

    public static Object parseNumber(CharSequence str, int radix) {
        char[] buf;
        if (str instanceof FString) {
            buf = ((FString) str).data;
        } else {
            buf = str.toString().toCharArray();
        }
        return parseNumber(buf, 0, str.length(), '\u0000', radix, 1);
    }

    public static Object parseNumber(char[] buffer, int start, int count, char exactness, int radix, int flags) {
        int end = start + count;
        int pos = start;
        if (pos >= end) {
            return "no digits";
        }
        int pos2 = pos + 1;
        char ch = buffer[pos];
        while (ch == '#') {
            if (pos2 >= end) {
                pos = pos2;
                return "no digits";
            }
            pos = pos2 + 1;
            ch = buffer[pos2];
            switch (ch) {
                case 'B':
                case 'b':
                    if (radix == 0) {
                        radix = 2;
                        pos2 = pos;
                        break;
                    }
                    return "duplicate radix specifier";
                case 'D':
                case 'd':
                    if (radix == 0) {
                        radix = 10;
                        pos2 = pos;
                        break;
                    }
                    return "duplicate radix specifier";
                case 'E':
                case 'I':
                case 'e':
                case 'i':
                    if (exactness == '\u0000') {
                        exactness = ch;
                        pos2 = pos;
                        break;
                    } else if (exactness == ' ') {
                        return "non-prefix exactness specifier";
                    } else {
                        return "duplicate exactness specifier";
                    }
                case 'O':
                case 'o':
                    if (radix == 0) {
                        radix = 8;
                        pos2 = pos;
                        break;
                    }
                    return "duplicate radix specifier";
                case 'X':
                case 'x':
                    if (radix == 0) {
                        radix = 16;
                        pos2 = pos;
                        break;
                    }
                    return "duplicate radix specifier";
                default:
                    int value = 0;
                    while (true) {
                        int dig = Character.digit(ch, 10);
                        if (dig >= 0) {
                            value = (value * 10) + dig;
                            if (pos >= end) {
                                return "missing letter after '#'";
                            }
                            pos2 = pos + 1;
                            ch = buffer[pos];
                            pos = pos2;
                        } else if (ch == 'R' || ch == 'r') {
                            if (radix == 0) {
                                if (value >= 2 && value <= 35) {
                                    radix = value;
                                    pos2 = pos;
                                    break;
                                }
                                return "invalid radix specifier";
                            }
                            return "duplicate radix specifier";
                        } else {
                            return "unknown modifier '#" + ch + '\'';
                        }
                    }
                    break;
            }
            if (pos2 >= end) {
                pos = pos2;
                return "no digits";
            }
            pos = pos2 + 1;
            ch = buffer[pos2];
            pos2 = pos;
        }
        if (exactness == '\u0000') {
            exactness = ' ';
        }
        if (radix == 0) {
            int i = count;
            do {
                i--;
                if (i < 0) {
                    radix = 10;
                }
            } while (buffer[start + i] != '.');
            radix = 10;
        }
        boolean negative = ch == '-';
        boolean numeratorNegative = negative;
        boolean sign_seen = ch == '-' || ch == '+';
        if (!sign_seen) {
            pos = pos2;
        } else if (pos2 >= end) {
            pos = pos2;
            return "no digits following sign";
        } else {
            pos = pos2 + 1;
            ch = buffer[pos2];
        }
        if ((ch == 'i' || ch == 'I') && pos == end && start == pos - 2 && (flags & 1) != 0) {
            char sign = buffer[start];
            if (sign != '+' && sign != '-') {
                return "no digits";
            }
            if (exactness == 'i' || exactness == 'I') {
                return new DComplex(0.0d, negative ? -1.0d : 1.0d);
            }
            return negative ? Complex.imMinusOne() : Complex.imOne();
        }
        int realStart = pos - 1;
        boolean hash_seen = false;
        int exp_seen = -1;
        int digits_start = -1;
        int decimal_point = -1;
        boolean underscore_seen = false;
        IntNum numerator = null;
        long lvalue = 0;
        while (true) {
            int digit = Character.digit(ch, radix);
            if (digit < 0) {
                switch (ch) {
                    case '.':
                        if (decimal_point < 0) {
                            if (radix == 10) {
                                decimal_point = pos - 1;
                                break;
                            }
                            return "'.' in non-decimal number";
                        }
                        return "duplicate '.' in number";
                    case '/':
                        if (numerator == null) {
                            if (digits_start >= 0) {
                                if (-1 < null && decimal_point < 0) {
                                    numerator = valueOf(buffer, digits_start, pos - digits_start, radix, negative, lvalue);
                                    digits_start = -1;
                                    lvalue = 0;
                                    negative = false;
                                    hash_seen = false;
                                    underscore_seen = false;
                                    break;
                                }
                                return "fraction symbol '/' following exponent or '.'";
                            }
                            return "no digits before fraction symbol '/'";
                        }
                        return "multiple fraction symbol '/'";
                        break;
                    case 'D':
                    case 'E':
                    case 'F':
                    case 'L':
                    case 'S':
                    case 'd':
                    case 'e':
                    case 'f':
                    case 'l':
                    case 's':
                        if (pos != end && radix == 10) {
                            char next = buffer[pos];
                            int exp_pos = pos - 1;
                            if (next != '+' && next != '-') {
                                if (Character.digit(next, 10) < 0) {
                                    pos--;
                                    break;
                                }
                            }
                            pos++;
                            if (pos >= end || Character.digit(buffer[pos], 10) < 0) {
                                return "missing exponent digits";
                            }
                            if (-1 < null) {
                                if (radix == 10) {
                                    if (digits_start >= 0) {
                                        exp_seen = exp_pos;
                                        do {
                                            pos++;
                                            if (pos >= end) {
                                                break;
                                            }
                                        } while (Character.digit(buffer[pos], 10) >= 0);
                                        break;
                                    }
                                    return "mantissa with no digits";
                                }
                                return "exponent in non-decimal number";
                            }
                            return "duplicate exponent";
                        }
                        pos--;
                        break;
                        break;
                    default:
                        pos--;
                        break;
                }
            } else if (hash_seen && decimal_point < 0) {
                return "digit after '#' in number";
            } else {
                if (digits_start < 0) {
                    digits_start = pos - 1;
                }
                lvalue = (((long) radix) * lvalue) + ((long) digit);
            }
            if (pos == end) {
                double d;
                RealNum number;
                char infnan = '\u0000';
                if (digits_start < 0) {
                    if (sign_seen && pos + 4 < end && buffer[pos + 3] == '.' && buffer[pos + 4] == '0') {
                        if (buffer[pos] == 'i' && buffer[pos + 1] == 'n' && buffer[pos + 2] == 'f') {
                            infnan = 'i';
                        } else if (buffer[pos] == 'n' && buffer[pos + 1] == 'a' && buffer[pos + 2] == 'n') {
                            infnan = 'n';
                        }
                    }
                    if (infnan == '\u0000') {
                        return "no digits";
                    }
                    pos2 = pos + 5;
                } else {
                    pos2 = pos;
                }
                if (hash_seen || underscore_seen) {
                }
                boolean inexact = exactness == 'i' || exactness == 'I' || (exactness == ' ' && hash_seen);
                char exp_char = '\u0000';
                RealNum dFloNum;
                if (infnan != '\u0000') {
                    d = infnan == 'i' ? Double.POSITIVE_INFINITY : Double.NaN;
                    if (negative) {
                        d = -d;
                    }
                    dFloNum = new DFloNum(d);
                } else if (exp_seen >= 0 || decimal_point >= 0) {
                    if (digits_start > decimal_point && decimal_point >= 0) {
                        digits_start = decimal_point;
                    }
                    if (numerator != null) {
                        pos = pos2;
                        return "floating-point number after fraction symbol '/'";
                    }
                    String str;
                    String str2 = new String(buffer, digits_start, pos2 - digits_start);
                    if (exp_seen >= 0) {
                        exp_char = Character.toLowerCase(buffer[exp_seen]);
                        if (exp_char != 'e') {
                            int prefix = exp_seen - digits_start;
                            str = str2.substring(0, prefix) + 'e' + str2.substring(prefix + 1);
                        }
                    }
                    d = Convert.parseDouble(str);
                    if (negative) {
                        d = -d;
                    }
                    dFloNum = new DFloNum(d);
                } else {
                    RealNum number2;
                    double d2;
                    RealNum iresult = valueOf(buffer, digits_start, pos2 - digits_start, radix, negative, lvalue);
                    if (numerator == null) {
                        number2 = iresult;
                    } else if (iresult.isZero()) {
                        boolean numeratorZero = numerator.isZero();
                        if (inexact) {
                            d2 = numeratorZero ? Double.NaN : numeratorNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
                            dFloNum = new DFloNum(d2);
                        } else if (numeratorZero) {
                            pos = pos2;
                            return "0/0 is undefined";
                        } else {
                            number = RatNum.make(numerator, iresult);
                        }
                        number2 = number;
                    } else {
                        number2 = RatNum.make(numerator, iresult);
                    }
                    if (inexact && number2.isExact()) {
                        d2 = (numeratorNegative && number2.isZero()) ? -0.0d : number2.doubleValue();
                        dFloNum = new DFloNum(d2);
                    } else {
                        number = number2;
                    }
                }
                if (exactness == 'e' || exactness == 'E') {
                    number = number.toExact();
                }
                if (pos2 < end) {
                    pos = pos2 + 1;
                    ch = buffer[pos2];
                    if (ch == '@') {
                        RealNum angle = parseNumber(buffer, pos, end - pos, exactness, 10, flags);
                        if (angle instanceof String) {
                            return angle;
                        }
                        if (!(angle instanceof RealNum)) {
                            return "invalid complex polar constant";
                        }
                        RealNum rangle = angle;
                        if (!number.isZero() || rangle.isExact()) {
                            return Complex.polar(number, rangle);
                        }
                        return new DFloNum(0.0d);
                    } else if (ch == '-' || ch == '+') {
                        pos--;
                        Complex imag = parseNumber(buffer, pos, end - pos, exactness, 10, flags);
                        if (imag instanceof String) {
                            return imag;
                        }
                        if (!(imag instanceof Complex)) {
                            return "invalid numeric constant (" + imag + ")";
                        }
                        Complex cimag = imag;
                        if (!cimag.re().isZero()) {
                            return "invalid numeric constant";
                        }
                        return Complex.make(number, cimag.im());
                    } else {
                        char prev;
                        int lcount = 0;
                        while (Character.isLetter(ch)) {
                            lcount++;
                            if (pos != end) {
                                pos2 = pos + 1;
                                ch = buffer[pos];
                                pos = pos2;
                            } else {
                                if (lcount == 1) {
                                    prev = buffer[pos - 1];
                                    if (prev == 'i' || prev == 'I') {
                                        if (pos >= end) {
                                            return "junk after imaginary suffix 'i'";
                                        }
                                        return Complex.make(IntNum.zero(), number);
                                    }
                                }
                                return "excess junk after number";
                            }
                        }
                        pos--;
                        if (lcount == 1) {
                            prev = buffer[pos - 1];
                            if (pos >= end) {
                                return Complex.make(IntNum.zero(), number);
                            }
                            return "junk after imaginary suffix 'i'";
                        }
                        return "excess junk after number";
                    }
                }
                if ((number instanceof DFloNum) && exp_char > '\u0000' && exp_char != 'e') {
                    d = number.doubleValue();
                    switch (exp_char) {
                        case 'd':
                            pos = pos2;
                            return Double.valueOf(d);
                        case 'f':
                        case 's':
                            pos = pos2;
                            return Float.valueOf((float) d);
                        case 'l':
                            pos = pos2;
                            return BigDecimal.valueOf(d);
                    }
                }
                pos = pos2;
                return number;
            }
            pos2 = pos + 1;
            ch = buffer[pos];
            pos = pos2;
        }
    }

    private static IntNum valueOf(char[] buffer, int digits_start, int number_of_digits, int radix, boolean negative, long lvalue) {
        if (number_of_digits + radix > 28) {
            return IntNum.valueOf(buffer, digits_start, number_of_digits, radix, negative);
        }
        if (negative) {
            lvalue = -lvalue;
        }
        return IntNum.make(lvalue);
    }

    public int readEscape() throws IOException, SyntaxException {
        int c = read();
        if (c >= 0) {
            return readEscape(c);
        }
        eofError("unexpected EOF in character literal");
        return -1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int readEscape(int r11) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r10 = this;
        r9 = 32;
        r8 = 9;
        r5 = -1;
        r4 = 63;
        r7 = 10;
        r6 = (char) r11;
        switch(r6) {
            case 9: goto L_0x0030;
            case 10: goto L_0x0030;
            case 13: goto L_0x0030;
            case 32: goto L_0x0030;
            case 34: goto L_0x0026;
            case 48: goto L_0x00ad;
            case 49: goto L_0x00ad;
            case 50: goto L_0x00ad;
            case 51: goto L_0x00ad;
            case 52: goto L_0x00ad;
            case 53: goto L_0x00ad;
            case 54: goto L_0x00ad;
            case 55: goto L_0x00ad;
            case 67: goto L_0x0088;
            case 77: goto L_0x006b;
            case 88: goto L_0x00f2;
            case 92: goto L_0x0029;
            case 94: goto L_0x0097;
            case 97: goto L_0x000f;
            case 98: goto L_0x0011;
            case 101: goto L_0x0023;
            case 102: goto L_0x001d;
            case 110: goto L_0x0017;
            case 114: goto L_0x0020;
            case 116: goto L_0x0014;
            case 117: goto L_0x00ce;
            case 118: goto L_0x001a;
            case 120: goto L_0x00f2;
            default: goto L_0x000d;
        };
    L_0x000d:
        r4 = r11;
    L_0x000e:
        return r4;
    L_0x000f:
        r11 = 7;
        goto L_0x000d;
    L_0x0011:
        r11 = 8;
        goto L_0x000d;
    L_0x0014:
        r11 = 9;
        goto L_0x000d;
    L_0x0017:
        r11 = 10;
        goto L_0x000d;
    L_0x001a:
        r11 = 11;
        goto L_0x000d;
    L_0x001d:
        r11 = 12;
        goto L_0x000d;
    L_0x0020:
        r11 = 13;
        goto L_0x000d;
    L_0x0023:
        r11 = 27;
        goto L_0x000d;
    L_0x0026:
        r11 = 34;
        goto L_0x000d;
    L_0x0029:
        r11 = 92;
        goto L_0x000d;
    L_0x002c:
        r11 = r10.read();
    L_0x0030:
        if (r11 >= 0) goto L_0x0039;
    L_0x0032:
        r4 = "unexpected EOF in literal";
        r10.eofError(r4);
        r4 = r5;
        goto L_0x000e;
    L_0x0039:
        if (r11 != r7) goto L_0x004a;
    L_0x003b:
        if (r11 != r7) goto L_0x000d;
    L_0x003d:
        r11 = r10.read();
        if (r11 >= 0) goto L_0x0062;
    L_0x0043:
        r4 = "unexpected EOF in literal";
        r10.eofError(r4);
        r4 = r5;
        goto L_0x000e;
    L_0x004a:
        r4 = 13;
        if (r11 != r4) goto L_0x005a;
    L_0x004e:
        r4 = r10.peek();
        if (r4 != r7) goto L_0x0057;
    L_0x0054:
        r10.skip();
    L_0x0057:
        r11 = 10;
        goto L_0x003b;
    L_0x005a:
        if (r11 == r9) goto L_0x002c;
    L_0x005c:
        if (r11 == r8) goto L_0x002c;
    L_0x005e:
        r10.unread(r11);
        goto L_0x003b;
    L_0x0062:
        if (r11 == r9) goto L_0x003d;
    L_0x0064:
        if (r11 == r8) goto L_0x003d;
    L_0x0066:
        r10.unread(r11);
        r4 = -2;
        goto L_0x000e;
    L_0x006b:
        r11 = r10.read();
        r5 = 45;
        if (r11 == r5) goto L_0x0079;
    L_0x0073:
        r5 = "Invalid escape character syntax";
        r10.error(r5);
        goto L_0x000e;
    L_0x0079:
        r11 = r10.read();
        r4 = 92;
        if (r11 != r4) goto L_0x0085;
    L_0x0081:
        r11 = r10.readEscape();
    L_0x0085:
        r4 = r11 | 128;
        goto L_0x000e;
    L_0x0088:
        r11 = r10.read();
        r5 = 45;
        if (r11 == r5) goto L_0x0097;
    L_0x0090:
        r5 = "Invalid escape character syntax";
        r10.error(r5);
        goto L_0x000e;
    L_0x0097:
        r11 = r10.read();
        r5 = 92;
        if (r11 != r5) goto L_0x00a3;
    L_0x009f:
        r11 = r10.readEscape();
    L_0x00a3:
        if (r11 != r4) goto L_0x00a9;
    L_0x00a5:
        r4 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        goto L_0x000e;
    L_0x00a9:
        r4 = r11 & 159;
        goto L_0x000e;
    L_0x00ad:
        r11 = r11 + -48;
        r0 = 0;
    L_0x00b0:
        r0 = r0 + 1;
        r4 = 3;
        if (r0 >= r4) goto L_0x000d;
    L_0x00b5:
        r1 = r10.read();
        r4 = (char) r1;
        r5 = 8;
        r3 = java.lang.Character.digit(r4, r5);
        if (r3 < 0) goto L_0x00c7;
    L_0x00c2:
        r4 = r11 << 3;
        r11 = r4 + r3;
        goto L_0x00b0;
    L_0x00c7:
        if (r1 < 0) goto L_0x000d;
    L_0x00c9:
        r10.unread(r1);
        goto L_0x000d;
    L_0x00ce:
        r11 = 0;
        r2 = 4;
    L_0x00d0:
        r2 = r2 + -1;
        if (r2 < 0) goto L_0x000d;
    L_0x00d4:
        r1 = r10.read();
        if (r1 >= 0) goto L_0x00df;
    L_0x00da:
        r4 = "premature EOF in \\u escape";
        r10.eofError(r4);
    L_0x00df:
        r4 = (char) r1;
        r5 = 16;
        r3 = java.lang.Character.digit(r4, r5);
        if (r3 >= 0) goto L_0x00ed;
    L_0x00e8:
        r4 = "non-hex character following \\u";
        r10.error(r4);
    L_0x00ed:
        r4 = r11 * 16;
        r11 = r4 + r3;
        goto L_0x00d0;
    L_0x00f2:
        r4 = r10.readHexEscape();
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.LispReader.readEscape(int):int");
    }

    public int readHexEscape() throws IOException, SyntaxException {
        int c = 0;
        while (true) {
            int d = read();
            int v = Character.digit((char) d, 16);
            if (v < 0) {
                break;
            }
            c = (c << 4) + v;
        }
        if (d != 59 && d >= 0) {
            unread(d);
        }
        return c;
    }

    public final Object readObject(int c) throws IOException, SyntaxException {
        unread(c);
        return readObject();
    }

    public Object readCommand() throws IOException, SyntaxException {
        return readObject();
    }

    protected Object makeNil() {
        return LList.Empty;
    }

    protected Pair makePair(Object car, int line, int column) {
        return makePair(car, LList.Empty, line, column);
    }

    protected Pair makePair(Object car, Object cdr, int line, int column) {
        String pname = this.port.getName();
        if (pname == null || line < 0) {
            return Pair.make(car, cdr);
        }
        return PairWithPosition.make(car, cdr, pname, line + 1, column + 1);
    }

    protected void setCdr(Object pair, Object cdr) {
        ((Pair) pair).setCdrBackdoor(cdr);
    }

    public static Object readNumberWithRadix(int previous, LispReader reader, int radix) throws IOException, SyntaxException {
        int startPos = reader.tokenBufferLength - previous;
        reader.readToken(reader.read(), 'P', ReadTable.getCurrent());
        int endPos = reader.tokenBufferLength;
        if (startPos == endPos) {
            reader.error("missing numeric token");
            return IntNum.zero();
        }
        Object result = parseNumber(reader.tokenBuffer, startPos, endPos - startPos, '\u0000', radix, 0);
        if (result instanceof String) {
            reader.error((String) result);
            return IntNum.zero();
        } else if (result != null) {
            return result;
        } else {
            reader.error("invalid numeric constant");
            return IntNum.zero();
        }
    }

    public static Object readCharacter(LispReader reader) throws IOException, SyntaxException {
        int ch = reader.read();
        if (ch < 0) {
            reader.eofError("unexpected EOF in character literal");
        }
        int startPos = reader.tokenBufferLength;
        reader.tokenBufferAppend(ch);
        reader.readToken(reader.read(), 'D', ReadTable.getCurrent());
        char[] tokenBuffer = reader.tokenBuffer;
        int length = reader.tokenBufferLength - startPos;
        if (length == 1) {
            return Char.make(tokenBuffer[startPos]);
        }
        String name = new String(tokenBuffer, startPos, length);
        ch = Char.nameToChar(name);
        if (ch >= 0) {
            return Char.make(ch);
        }
        int value;
        int i;
        ch = tokenBuffer[startPos];
        if (ch == 120 || ch == 88) {
            value = 0;
            i = 1;
            while (i != length) {
                int v = Character.digit(tokenBuffer[startPos + i], 16);
                if (v >= 0) {
                    value = (value * 16) + v;
                    if (value <= 1114111) {
                        i++;
                    }
                }
            }
            return Char.make(value);
        }
        ch = Character.digit(ch, 8);
        if (ch >= 0) {
            value = ch;
            i = 1;
            while (i != length) {
                ch = Character.digit(tokenBuffer[startPos + i], 8);
                if (ch >= 0) {
                    value = (value * 8) + ch;
                    i++;
                }
            }
            return Char.make(value);
        }
        reader.error("unknown character name: " + name);
        return Char.make(63);
    }

    public static Object readSpecial(LispReader reader) throws IOException, SyntaxException {
        int ch = reader.read();
        if (ch < 0) {
            reader.eofError("unexpected EOF in #! special form");
        }
        if (ch == 47 && reader.getLineNumber() == 0 && reader.getColumnNumber() == 3) {
            ReaderIgnoreRestOfLine.getInstance().read(reader, 35, 1);
            return Values.empty;
        }
        int startPos = reader.tokenBufferLength;
        reader.tokenBufferAppend(ch);
        reader.readToken(reader.read(), 'D', ReadTable.getCurrent());
        String name = new String(reader.tokenBuffer, startPos, reader.tokenBufferLength - startPos);
        if (name.equals("optional")) {
            return Special.optional;
        }
        if (name.equals("rest")) {
            return Special.rest;
        }
        if (name.equals("key")) {
            return Special.key;
        }
        if (name.equals("eof")) {
            return Special.eof;
        }
        if (name.equals("void")) {
            return QuoteExp.voidExp;
        }
        if (name.equals("default")) {
            return Special.dfault;
        }
        if (name.equals("undefined")) {
            return Special.undefined;
        }
        if (name.equals("abstract")) {
            return Special.abstractSpecial;
        }
        if (name.equals("null")) {
            return null;
        }
        reader.error("unknown named constant #!" + name);
        return null;
    }

    public static SimpleVector readSimpleVector(LispReader reader, char kind) throws IOException, SyntaxException {
        int size = 0;
        while (true) {
            int ch = reader.read();
            if (ch < 0) {
                reader.eofError("unexpected EOF reading uniform vector");
            }
            int digit = Character.digit((char) ch, 10);
            if (digit < 0) {
                break;
            }
            size = (size * 10) + digit;
        }
        if ((size == 8 || size == 16 || size == 32 || size == 64) && ((kind != Access.FIELD_CONTEXT || size >= 32) && ch == 40)) {
            Sequence list = ReaderParens.readList(reader, 40, -1, 41);
            if (LList.listLength(list, false) < 0) {
                reader.error("invalid elements in uniform vector syntax");
                return null;
            }
            Sequence q = list;
            switch (kind) {
                case 'F':
                    switch (size) {
                        case 32:
                            return new F32Vector(q);
                        case 64:
                            return new F64Vector(q);
                    }
                    break;
                case 'S':
                    break;
                case 'U':
                    break;
                default:
                    return null;
            }
            switch (size) {
                case 8:
                    return new S8Vector(q);
                case 16:
                    return new S16Vector(q);
                case 32:
                    return new S32Vector(q);
                case 64:
                    return new S64Vector(q);
            }
            switch (size) {
                case 8:
                    return new U8Vector(q);
                case 16:
                    return new U16Vector(q);
                case 32:
                    return new U32Vector(q);
                case 64:
                    return new U64Vector(q);
                default:
                    return null;
            }
        }
        reader.error("invalid uniform vector syntax");
        return null;
    }
}
