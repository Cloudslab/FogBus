package gnu.kawa.functions;

import gnu.math.RealNum;
import gnu.text.EnglishIntegerFormat;
import gnu.text.RomanIntegerFormat;
import java.text.Format;

public class IntegerFormat extends gnu.text.IntegerFormat {
    private static IntegerFormat plainDecimalFormat;

    public static IntegerFormat getInstance() {
        if (plainDecimalFormat == null) {
            plainDecimalFormat = new IntegerFormat();
        }
        return plainDecimalFormat;
    }

    public static Format getInstance(int base, int minWidth, int padChar, int commaChar, int commaInterval, int flags) {
        boolean seenColon = true;
        if (base == -1073741824) {
            if (padChar == -1073741824 && padChar == -1073741824 && commaChar == -1073741824 && commaInterval == -1073741824) {
                if ((flags & 1) == 0) {
                    seenColon = false;
                }
                if ((flags & 2) != 0) {
                    return RomanIntegerFormat.getInstance(seenColon);
                }
                return EnglishIntegerFormat.getInstance(seenColon);
            }
            base = 10;
        }
        if (minWidth == -1073741824) {
            minWidth = 1;
        }
        if (padChar == -1073741824) {
            padChar = 32;
        }
        if (commaChar == -1073741824) {
            commaChar = 44;
        }
        if (commaInterval == -1073741824) {
            commaInterval = 3;
        }
        if (base == 10 && minWidth == 1 && padChar == 32 && commaChar == 44 && commaInterval == 3 && flags == 0) {
            return getInstance();
        }
        Format fmt = new IntegerFormat();
        fmt.base = base;
        fmt.minWidth = minWidth;
        fmt.padChar = padChar;
        fmt.commaChar = commaChar;
        fmt.commaInterval = commaInterval;
        fmt.flags = flags;
        return fmt;
    }

    public String convertToIntegerString(Object arg, int radix) {
        if (arg instanceof RealNum) {
            return ((RealNum) arg).toExactInt(4).toString(radix);
        }
        return super.convertToIntegerString(arg, radix);
    }
}
