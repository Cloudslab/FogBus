package gnu.kawa.xml;

public class Base64Binary extends BinaryObject {
    public static final String ENCODING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    public Base64Binary(byte[] data) {
        this.data = data;
    }

    public static Base64Binary valueOf(String str) {
        return new Base64Binary(str);
    }

    public Base64Binary(String str) {
        int i;
        int len = str.length();
        int blen = 0;
        for (i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (!(Character.isWhitespace(ch) || ch == '=')) {
                blen++;
            }
        }
        byte[] bytes = new byte[((blen * 3) / 4)];
        int value = 0;
        int buffered = 0;
        int padding = 0;
        i = 0;
        int blen2 = 0;
        while (i < len) {
            int v;
            ch = str.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                v = ch - 65;
            } else if (ch >= 'a' && ch <= 'z') {
                v = (ch - 97) + 26;
            } else if (ch >= '0' && ch <= '9') {
                v = (ch - 48) + 52;
            } else if (ch == '+') {
                v = 62;
            } else if (ch == '/') {
                v = 63;
            } else {
                if (Character.isWhitespace(ch)) {
                    blen = blen2;
                } else if (ch == '=') {
                    padding++;
                    blen = blen2;
                } else {
                    v = -1;
                }
                i++;
                blen2 = blen;
            }
            if (v < 0 || padding > 0) {
                throw new IllegalArgumentException("illegal character in base64Binary string at position " + i);
            }
            value = (value << 6) + v;
            buffered++;
            if (buffered == 4) {
                blen = blen2 + 1;
                bytes[blen2] = (byte) (value >> 16);
                blen2 = blen + 1;
                bytes[blen] = (byte) (value >> 8);
                blen = blen2 + 1;
                bytes[blen2] = (byte) value;
                buffered = 0;
            } else {
                blen = blen2;
            }
            i++;
            blen2 = blen;
        }
        if (buffered + padding <= 0 ? blen2 != bytes.length : !(buffered + padding == 4 && (((1 << padding) - 1) & value) == 0 && (blen2 + 3) - padding == bytes.length)) {
            throw new IllegalArgumentException();
        }
        switch (padding) {
            case 1:
                blen = blen2 + 1;
                bytes[blen2] = (byte) (value << 10);
                blen2 = blen + 1;
                bytes[blen] = (byte) (value >> 2);
                blen = blen2;
                break;
            case 2:
                blen = blen2 + 1;
                bytes[blen2] = (byte) (value >> 4);
                break;
            default:
                blen = blen2;
                break;
        }
        this.data = bytes;
    }

    public StringBuffer toString(StringBuffer sbuf) {
        byte[] bb = this.data;
        int len = bb.length;
        int value = 0;
        int i = 0;
        while (i < len) {
            value = (value << 8) | (bb[i] & 255);
            i++;
            if (i % 3 == 0) {
                sbuf.append(ENCODING.charAt((value >> 18) & 63));
                sbuf.append(ENCODING.charAt((value >> 12) & 63));
                sbuf.append(ENCODING.charAt((value >> 6) & 63));
                sbuf.append(ENCODING.charAt(value & 63));
            }
        }
        switch (len % 3) {
            case 1:
                sbuf.append(ENCODING.charAt((value >> 2) & 63));
                sbuf.append(ENCODING.charAt((value << 4) & 63));
                sbuf.append("==");
                break;
            case 2:
                sbuf.append(ENCODING.charAt((value >> 10) & 63));
                sbuf.append(ENCODING.charAt((value >> 4) & 63));
                sbuf.append(ENCODING.charAt((value << 2) & 63));
                sbuf.append('=');
                break;
        }
        return sbuf;
    }

    public String toString() {
        return toString(new StringBuffer()).toString();
    }
}
