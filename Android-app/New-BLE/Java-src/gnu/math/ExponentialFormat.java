package gnu.math;

import com.google.appinventor.components.runtime.Component;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class ExponentialFormat extends Format {
    static final double LOG10 = Math.log(10.0d);
    public int expDigits;
    public char exponentChar = 'E';
    public boolean exponentShowSign;
    public int fracDigits = -1;
    public boolean general;
    public int intDigits;
    public char overflowChar;
    public char padChar;
    public boolean showPlus;
    public int width;

    static boolean addOne(StringBuffer sbuf, int digStart, int digEnd) {
        int j = digEnd;
        while (j != digStart) {
            j--;
            char ch = sbuf.charAt(j);
            if (ch != '9') {
                sbuf.setCharAt(j, (char) (ch + 1));
                return false;
            }
            sbuf.setCharAt(j, '0');
        }
        sbuf.insert(j, '1');
        return true;
    }

    public StringBuffer format(float value, StringBuffer sbuf, FieldPosition fpos) {
        return format((double) value, this.fracDigits < 0 ? Float.toString(value) : null, sbuf, fpos);
    }

    public StringBuffer format(double value, StringBuffer sbuf, FieldPosition fpos) {
        return format(value, this.fracDigits < 0 ? Double.toString(value) : null, sbuf, fpos);
    }

    StringBuffer format(double value, String dstr, StringBuffer sbuf, FieldPosition fpos) {
        int exponent;
        int i;
        int digits;
        int scale;
        int exponentAbs;
        int j;
        int k = this.intDigits;
        int d = this.fracDigits;
        boolean negative = value < 0.0d;
        if (negative) {
            value = -value;
        }
        int oldLen = sbuf.length();
        int signLen = 1;
        if (negative) {
            if (d >= 0) {
                sbuf.append('-');
            }
        } else if (this.showPlus) {
            sbuf.append('+');
        } else {
            signLen = 0;
        }
        int digStart = sbuf.length();
        boolean nonFinite = Double.isNaN(value) || Double.isInfinite(value);
        if (d < 0 || nonFinite) {
            if (dstr == null) {
                dstr = Double.toString(value);
            }
            int indexE = dstr.indexOf(69);
            if (indexE >= 0) {
                sbuf.append(dstr);
                indexE += digStart;
                boolean negexp = dstr.charAt(indexE + 1) == '-';
                exponent = 0;
                for (i = indexE + (negexp ? 2 : 1); i < sbuf.length(); i++) {
                    exponent = (exponent * 10) + (sbuf.charAt(i) - 48);
                }
                if (negexp) {
                    exponent = -exponent;
                }
                sbuf.setLength(indexE);
            } else {
                exponent = RealNum.toStringScientific(dstr, sbuf);
            }
            if (negative) {
                digStart++;
            }
            sbuf.deleteCharAt(digStart + 1);
            digits = sbuf.length() - digStart;
            if (digits > 1 && sbuf.charAt((digStart + digits) - 1) == '0') {
                digits--;
                sbuf.setLength(digStart + digits);
            }
            scale = (digits - exponent) - 1;
        } else {
            int i2;
            if (k > 0) {
                i2 = 1;
            } else {
                i2 = k;
            }
            digits = d + i2;
            int log = (int) ((Math.log(value) / LOG10) + 1000.0d);
            if (log == Integer.MIN_VALUE) {
                log = 0;
            } else {
                log += Component.LENGTH_PERCENT_TAG;
            }
            scale = (digits - log) - 1;
            RealNum.toScaledInt(value, scale).format(10, sbuf);
            exponent = (digits - 1) - scale;
        }
        exponent -= k - 1;
        if (exponent < 0) {
            exponentAbs = -exponent;
        } else {
            exponentAbs = exponent;
        }
        int exponentLen = exponentAbs >= 1000 ? 4 : exponentAbs >= 100 ? 3 : exponentAbs >= 10 ? 2 : 1;
        if (this.expDigits > exponentLen) {
            exponentLen = this.expDigits;
        }
        boolean showExponent = true;
        int ee = !this.general ? 0 : this.expDigits > 0 ? this.expDigits + 2 : 4;
        boolean fracUnspecified = d < 0;
        if (this.general || fracUnspecified) {
            int n = digits - scale;
            if (fracUnspecified) {
                d = n < 7 ? n : 7;
                if (digits > d) {
                    d = digits;
                }
            }
            int dd = d - n;
            if (this.general && n >= 0 && dd >= 0) {
                digits = d;
                k = n;
                showExponent = false;
            } else if (fracUnspecified) {
                if (this.width <= 0) {
                    digits = d;
                } else {
                    digits = ((this.width - signLen) - exponentLen) - 3;
                    if (k < 0) {
                        digits -= k;
                    }
                    if (digits > d) {
                        digits = d;
                    }
                }
                if (digits <= 0) {
                    digits = 1;
                }
            }
        }
        int digEnd = digStart + digits;
        while (sbuf.length() < digEnd) {
            sbuf.append('0');
        }
        if (((digEnd == sbuf.length() ? '0' : sbuf.charAt(digEnd)) >= '5') && addOne(sbuf, digStart, digEnd)) {
            scale++;
        }
        scale -= sbuf.length() - digEnd;
        sbuf.setLength(digEnd);
        int dot = digStart;
        if (k < 0) {
            j = k;
            while (true) {
                j++;
                if (j > 0) {
                    break;
                }
                sbuf.insert(digStart, '0');
            }
        } else {
            while (digStart + k > digEnd) {
                sbuf.append('0');
                digEnd++;
            }
            dot += k;
        }
        if (nonFinite) {
            showExponent = false;
        } else {
            sbuf.insert(dot, '.');
        }
        if (showExponent) {
            sbuf.append(this.exponentChar);
            if (this.exponentShowSign || exponent < 0) {
                sbuf.append(exponent >= 0 ? '+' : '-');
            }
            i = sbuf.length();
            sbuf.append(exponentAbs);
            int newLen = sbuf.length();
            j = this.expDigits - (newLen - i);
            if (j > 0) {
                newLen += j;
                while (true) {
                    j--;
                    if (j < 0) {
                        break;
                    }
                    sbuf.insert(i, '0');
                }
            }
        } else {
            exponentLen = 0;
        }
        i = this.width - (sbuf.length() - oldLen);
        if (fracUnspecified && ((dot + 1 == sbuf.length() || sbuf.charAt(dot + 1) == this.exponentChar) && (this.width <= 0 || i > 0))) {
            i--;
            sbuf.insert(dot + 1, '0');
        }
        if ((i >= 0 || this.width <= 0) && (!showExponent || exponentLen <= this.expDigits || this.expDigits <= 0 || this.overflowChar == '\u0000')) {
            if (k <= 0 && (i > 0 || this.width <= 0)) {
                sbuf.insert(digStart, '0');
                i--;
            }
            if (!showExponent && this.width > 0) {
                while (true) {
                    ee--;
                    if (ee < 0) {
                        break;
                    }
                    sbuf.append(' ');
                    i--;
                }
            }
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                sbuf.insert(oldLen, this.padChar);
            }
        } else if (this.overflowChar != '\u0000') {
            sbuf.setLength(oldLen);
            i = this.width;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                sbuf.append(this.overflowChar);
            }
        }
        return sbuf;
    }

    public StringBuffer format(long num, StringBuffer sbuf, FieldPosition fpos) {
        return format((double) num, sbuf, fpos);
    }

    public StringBuffer format(Object num, StringBuffer sbuf, FieldPosition fpos) {
        return format(((RealNum) num).doubleValue(), sbuf, fpos);
    }

    public Number parse(String text, ParsePosition status) {
        throw new Error("ExponentialFormat.parse - not implemented");
    }

    public Object parseObject(String text, ParsePosition status) {
        throw new Error("ExponentialFormat.parseObject - not implemented");
    }
}
