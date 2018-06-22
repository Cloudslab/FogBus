package gnu.kawa.xml;

public class HexBinary extends BinaryObject {
    public HexBinary(byte[] data) {
        this.data = data;
    }

    static HexBinary valueOf(String str) {
        return new HexBinary(parseHexBinary(str));
    }

    static byte[] parseHexBinary(String str) {
        str = str.trim();
        int len = str.length();
        if ((len & 1) != 0) {
            throw new IllegalArgumentException("hexBinary string length not a multiple of 2");
        }
        len >>= 1;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            int d1 = Character.digit(str.charAt(i * 2), 16);
            int d2 = Character.digit(str.charAt((i * 2) + 1), 16);
            int bad = -1;
            if (d1 < 0) {
                bad = i * 2;
            } else if (d2 < 0) {
                bad = (i * 2) + 1;
            }
            if (bad >= 0) {
                throw new IllegalArgumentException("invalid hexBinary character at position " + bad);
            }
            result[i] = (byte) ((d1 * 16) + d2);
        }
        return result;
    }

    static char forHexDigit(int val) {
        return val < 10 ? (char) (val + 48) : (char) ((val - 10) + 65);
    }

    public StringBuffer toString(StringBuffer sbuf) {
        for (byte b : this.data) {
            sbuf.append(forHexDigit((b >> 4) & 15));
            sbuf.append(forHexDigit(b & 15));
        }
        return sbuf;
    }

    public String toString() {
        return toString(new StringBuffer()).toString();
    }
}
