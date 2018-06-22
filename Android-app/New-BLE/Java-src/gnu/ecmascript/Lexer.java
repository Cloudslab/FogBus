package gnu.ecmascript;

import android.support.v4.media.TransportMediator;
import gnu.expr.QuoteExp;
import gnu.lists.Sequence;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.Hashtable;

public class Lexer extends gnu.text.Lexer {
    public static final Char colonToken = Char.make(58);
    public static final Char commaToken = Char.make(44);
    public static final Char condToken = Char.make(63);
    public static final Char dotToken = Char.make(46);
    public static final Reserved elseToken = new Reserved("else", 38);
    public static final Object eofToken = Sequence.eofValue;
    public static final Object eolToken = Char.make(10);
    public static final Char equalToken = Char.make(61);
    public static final Char lbraceToken = Char.make(123);
    public static final Char lbracketToken = Char.make(91);
    public static final Char lparenToken = Char.make(40);
    public static final Reserved newToken = new Reserved("new", 39);
    public static final Char notToken = Char.make(33);
    public static final Char rbraceToken = Char.make(125);
    public static final Char rbracketToken = Char.make(93);
    static Hashtable reserved;
    public static final Char rparenToken = Char.make(41);
    public static final Char semicolonToken = Char.make(59);
    public static final Char tildeToken = Char.make(TransportMediator.KEYCODE_MEDIA_PLAY);
    private boolean prevWasCR = false;

    public Lexer(InPort port) {
        super(port);
    }

    static synchronized void initReserved() {
        synchronized (Lexer.class) {
            if (reserved == null) {
                reserved = new Hashtable(20);
                reserved.put("null", new QuoteExp(null));
                reserved.put("true", new QuoteExp(Boolean.TRUE));
                reserved.put("false", new QuoteExp(Boolean.FALSE));
                reserved.put("var", new Reserved("var", 30));
                reserved.put("if", new Reserved("if", 31));
                reserved.put("while", new Reserved("while", 32));
                reserved.put("for", new Reserved("for", 33));
                reserved.put("continue", new Reserved("continue", 34));
                reserved.put("break", new Reserved("break", 35));
                reserved.put("return", new Reserved("return", 36));
                reserved.put("with", new Reserved("with", 37));
                reserved.put("function", new Reserved("function", 41));
                reserved.put("this", new Reserved("this", 40));
                reserved.put("else", elseToken);
                reserved.put("new", newToken);
            }
        }
    }

    public static Object checkReserved(String name) {
        if (reserved == null) {
            initReserved();
        }
        return reserved.get(name);
    }

    public Double getNumericLiteral(int c) throws IOException {
        int radix = 10;
        if (c == 48) {
            c = read();
            if (c == 120 || c == 88) {
                radix = 16;
                c = read();
            } else if (!(c == 46 || c == 101 || c == 69)) {
                radix = 8;
            }
        }
        int i = this.port.pos;
        if (c >= 0) {
            i--;
        }
        this.port.pos = i;
        long ival = gnu.text.Lexer.readDigitsInBuffer(this.port, radix);
        boolean digit_seen = this.port.pos > i;
        if (digit_seen && this.port.pos < this.port.limit) {
            c = this.port.buffer[this.port.pos];
            if (!(Character.isLetterOrDigit((char) c) || c == 46)) {
                double dval;
                if (ival >= 0) {
                    dval = (double) ival;
                } else {
                    dval = IntNum.valueOf(this.port.buffer, i, this.port.pos - i, radix, false).doubleValue();
                }
                return new Double(dval);
            }
        }
        if (radix != 10) {
            error("invalid character in non-decimal number");
        }
        StringBuffer str = new StringBuffer(20);
        if (digit_seen) {
            str.append(this.port.buffer, i, this.port.pos - i);
        }
        int point_loc = -1;
        int exp = 0;
        while (true) {
            c = this.port.read();
            if (Character.digit((char) c, radix) >= 0) {
                digit_seen = true;
                str.append((char) c);
            } else {
                switch (c) {
                    case 46:
                        if (point_loc < 0) {
                            point_loc = str.length();
                            str.append('.');
                            break;
                        }
                        error("duplicate '.' in number");
                        continue;
                    case 69:
                    case 101:
                        if (radix == 10) {
                            int next = this.port.peek();
                            if (next == 43 || next == 45 || Character.digit((char) next, 10) >= 0) {
                                if (!digit_seen) {
                                    error("mantissa with no digits");
                                }
                                exp = readOptionalExponent();
                                c = read();
                                break;
                            }
                        }
                        break;
                    default:
                        break;
                }
                if (c >= 0) {
                    this.port.unread();
                }
                if (exp != 0) {
                    str.append('e');
                    str.append(exp);
                }
                return new Double(str.toString());
            }
        }
    }

    public String getStringLiteral(char quote) throws IOException, SyntaxException {
        int i = this.port.pos;
        int start = i;
        int limit = this.port.limit;
        char[] buffer = this.port.buffer;
        while (i < limit) {
            char c = buffer[i];
            if (c != quote) {
                if (c == '\\' || c == '\n' || c == '\r') {
                    break;
                }
                i++;
            } else {
                this.port.pos = i + 1;
                return new String(buffer, start, i - start);
            }
        }
        this.port.pos = i;
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(buffer, start, i - start);
        while (true) {
            int ch = this.port.read();
            if (ch == quote) {
                return sbuf.toString();
            }
            if (ch < '\u0000') {
                eofError("unterminated string literal");
            }
            if (ch == '\n' || ch == '\r') {
                fatal("string literal not terminated before end of line");
            }
            if (ch == '\\') {
                ch = this.port.read();
                int val;
                int d;
                switch (ch) {
                    case -1:
                        eofError("eof following '\\' in string");
                        break;
                    case 10:
                    case 13:
                        break;
                    case 34:
                    case 39:
                    case 92:
                        break;
                    case 98:
                        ch = 8;
                        continue;
                    case 102:
                        ch = 12;
                        continue;
                    case 110:
                        ch = 10;
                        continue;
                    case 114:
                        ch = 13;
                        continue;
                    case 116:
                        ch = 9;
                        continue;
                    case 117:
                    case 120:
                        val = 0;
                        i = ch == 120 ? 2 : 4;
                        while (true) {
                            i--;
                            if (i >= 0) {
                                d = this.port.read();
                                if (d < 0) {
                                    eofError("eof following '\\" + ((char) ch) + "' in string");
                                }
                                d = Character.forDigit((char) d, 16);
                                if (d < 0) {
                                    error("invalid char following '\\" + ((char) ch) + "' in string");
                                    val = 63;
                                } else {
                                    val = (val * 16) + d;
                                }
                            }
                            ch = val;
                            continue;
                        }
                    default:
                        if (ch >= 48 && ch <= 55) {
                            val = 0;
                            i = 3;
                            while (true) {
                                i--;
                                if (i >= 0) {
                                    d = this.port.read();
                                    if (d < 0) {
                                        eofError("eof in octal escape in string literal");
                                    }
                                    d = Character.forDigit((char) d, 8);
                                    if (d < 0) {
                                        this.port.unread_quick();
                                    } else {
                                        val = (val * 8) + d;
                                    }
                                }
                                ch = val;
                                break;
                            }
                        }
                }
                fatal("line terminator following '\\' in string");
            }
            sbuf.append((char) ch);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getIdentifier(int r8) throws java.io.IOException {
        /*
        r7 = this;
        r5 = r7.port;
        r1 = r5.pos;
        r4 = r1 + -1;
        r5 = r7.port;
        r2 = r5.limit;
        r5 = r7.port;
        r0 = r5.buffer;
    L_0x000e:
        if (r1 >= r2) goto L_0x001b;
    L_0x0010:
        r5 = r0[r1];
        r5 = java.lang.Character.isJavaIdentifierPart(r5);
        if (r5 == 0) goto L_0x001b;
    L_0x0018:
        r1 = r1 + 1;
        goto L_0x000e;
    L_0x001b:
        r5 = r7.port;
        r5.pos = r1;
        if (r1 >= r2) goto L_0x0029;
    L_0x0021:
        r5 = new java.lang.String;
        r6 = r1 - r4;
        r5.<init>(r0, r4, r6);
    L_0x0028:
        return r5;
    L_0x0029:
        r3 = new java.lang.StringBuffer;
        r3.<init>();
        r5 = r1 - r4;
        r3.append(r0, r4, r5);
    L_0x0033:
        r5 = r7.port;
        r8 = r5.read();
        if (r8 >= 0) goto L_0x0040;
    L_0x003b:
        r5 = r3.toString();
        goto L_0x0028;
    L_0x0040:
        r5 = (char) r8;
        r5 = java.lang.Character.isJavaIdentifierPart(r5);
        if (r5 == 0) goto L_0x004c;
    L_0x0047:
        r5 = (char) r8;
        r3.append(r5);
        goto L_0x0033;
    L_0x004c:
        r5 = r7.port;
        r5.unread_quick();
        goto L_0x003b;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.ecmascript.Lexer.getIdentifier(int):java.lang.String");
    }

    public Object maybeAssignment(Object token) throws IOException, SyntaxException {
        int ch = read();
        if (ch == 61) {
            error("assignment operation not implemented");
        }
        if (ch >= 0) {
            this.port.unread_quick();
        }
        return token;
    }

    public Object getToken() throws IOException, SyntaxException {
        int ch = read();
        while (ch >= 0) {
            if (!Character.isWhitespace((char) ch)) {
                switch (ch) {
                    case 33:
                        if (this.port.peek() != 61) {
                            return notToken;
                        }
                        this.port.skip_quick();
                        return Reserved.opNotEqual;
                    case 34:
                    case 39:
                        return new QuoteExp(getStringLiteral((char) ch));
                    case 37:
                        return maybeAssignment(Reserved.opRemainder);
                    case 38:
                        if (this.port.peek() != 38) {
                            return maybeAssignment(Reserved.opBitAnd);
                        }
                        this.port.skip_quick();
                        return maybeAssignment(Reserved.opBoolAnd);
                    case 40:
                        return lparenToken;
                    case 41:
                        return rparenToken;
                    case 42:
                        return maybeAssignment(Reserved.opTimes);
                    case 43:
                        if (this.port.peek() != 43) {
                            return maybeAssignment(Reserved.opPlus);
                        }
                        this.port.skip_quick();
                        return maybeAssignment(Reserved.opPlusPlus);
                    case 44:
                        return commaToken;
                    case 45:
                        if (this.port.peek() != 45) {
                            return maybeAssignment(Reserved.opMinus);
                        }
                        this.port.skip_quick();
                        return maybeAssignment(Reserved.opMinusMinus);
                    case 46:
                        ch = this.port.peek();
                        if (ch < 48 || ch > 57) {
                            return dotToken;
                        }
                        return new QuoteExp(getNumericLiteral(46));
                    case 47:
                        return maybeAssignment(Reserved.opDivide);
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                        return new QuoteExp(getNumericLiteral(ch));
                    case 58:
                        return colonToken;
                    case 59:
                        return semicolonToken;
                    case 60:
                        switch (this.port.peek()) {
                            case 60:
                                this.port.skip_quick();
                                return maybeAssignment(Reserved.opLshift);
                            case 61:
                                this.port.skip_quick();
                                return Reserved.opLessEqual;
                            default:
                                return Reserved.opLess;
                        }
                    case 61:
                        if (this.port.peek() != 61) {
                            return equalToken;
                        }
                        this.port.skip_quick();
                        return Reserved.opEqual;
                    case 62:
                        switch (this.port.peek()) {
                            case 61:
                                this.port.skip_quick();
                                return Reserved.opGreaterEqual;
                            case 62:
                                this.port.skip_quick();
                                if (this.port.peek() != 62) {
                                    return maybeAssignment(Reserved.opRshiftSigned);
                                }
                                this.port.skip_quick();
                                return maybeAssignment(Reserved.opRshiftUnsigned);
                            default:
                                return Reserved.opGreater;
                        }
                    case 63:
                        return condToken;
                    case 91:
                        return lbracketToken;
                    case 93:
                        return rbracketToken;
                    case 94:
                        return maybeAssignment(Reserved.opBitXor);
                    case 123:
                        return lbraceToken;
                    case 124:
                        if (this.port.peek() != 124) {
                            return maybeAssignment(Reserved.opBitOr);
                        }
                        this.port.skip_quick();
                        return maybeAssignment(Reserved.opBoolOr);
                    case 125:
                        return rbraceToken;
                    case TransportMediator.KEYCODE_MEDIA_PLAY /*126*/:
                        return tildeToken;
                    default:
                        if (!Character.isJavaIdentifierStart((char) ch)) {
                            return Char.make((char) ch);
                        }
                        String word = getIdentifier(ch).intern();
                        Object token = checkReserved(word);
                        if (token == null) {
                            return word;
                        }
                        return token;
                }
            } else if (ch == 13) {
                this.prevWasCR = true;
                return eolToken;
            } else if (ch == 10 && !this.prevWasCR) {
                return eolToken;
            } else {
                this.prevWasCR = false;
                ch = read();
            }
        }
        return eofToken;
    }

    public static Object getToken(InPort inp) throws IOException, SyntaxException {
        return new Lexer(inp).getToken();
    }

    public static void main(String[] args) {
        Lexer reader = new Lexer(InPort.inDefault());
        Object token;
        do {
            try {
                token = reader.getToken();
                OutPort out = OutPort.outDefault();
                out.print("token:");
                out.print(token);
                out.println(" [class:" + token.getClass() + "]");
            } catch (Exception ex) {
                System.err.println("caught exception:" + ex);
                return;
            }
        } while (token != Sequence.eofValue);
    }
}
