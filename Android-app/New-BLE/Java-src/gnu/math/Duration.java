package gnu.math;

import gnu.bytecode.Access;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Duration extends Quantity implements Externalizable {
    int months;
    int nanos;
    long seconds;
    public Unit unit;

    public static Duration make(int months, long seconds, int nanos, Unit unit) {
        Duration d = new Duration();
        d.months = months;
        d.seconds = seconds;
        d.nanos = nanos;
        d.unit = unit;
        return d;
    }

    public static Duration makeMonths(int months) {
        Duration d = new Duration();
        d.unit = Unit.month;
        d.months = months;
        return d;
    }

    public static Duration makeMinutes(int minutes) {
        Duration d = new Duration();
        d.unit = Unit.second;
        d.seconds = (long) (minutes * 60);
        return d;
    }

    public static Duration parse(String str, Unit unit) {
        Duration d = valueOf(str, unit);
        if (d != null) {
            return d;
        }
        throw new IllegalArgumentException("not a valid " + unit.getName() + " duration: '" + str + "'");
    }

    public static Duration parseDuration(String str) {
        return parse(str, Unit.duration);
    }

    public static Duration parseYearMonthDuration(String str) {
        return parse(str, Unit.month);
    }

    public static Duration parseDayTimeDuration(String str) {
        return parse(str, Unit.second);
    }

    public static Duration valueOf(String str, Unit unit) {
        boolean negative;
        str = str.trim();
        int pos = 0;
        int len = str.length();
        if (0 >= len || str.charAt(0) != '-') {
            negative = false;
        } else {
            negative = true;
            pos = 0 + 1;
        }
        if (pos + 1 >= len || str.charAt(pos) != 'P') {
            return null;
        }
        int months = 0;
        int nanos = 0;
        long seconds = 0;
        long part = scanPart(str, pos + 1);
        pos = ((int) part) >> 16;
        char ch = (char) ((int) part);
        if (unit == Unit.second && (ch == 'Y' || ch == Access.METHOD_CONTEXT)) {
            return null;
        }
        if (ch == 'Y') {
            months = ((int) (part >> 32)) * 12;
            pos = ((int) part) >> 16;
            part = scanPart(str, pos);
            ch = (char) ((int) part);
        }
        if (ch == Access.METHOD_CONTEXT) {
            months = (int) (((long) months) + (part >> 32));
            pos = ((int) part) >> 16;
            part = scanPart(str, pos);
            ch = (char) ((int) part);
        }
        if (unit == Unit.month && pos != len) {
            return null;
        }
        if (ch == 'D') {
            if (unit == Unit.month) {
                return null;
            }
            seconds = 86400 * ((long) ((int) (part >> 32)));
            pos = ((int) part) >> 16;
            part = scanPart(str, pos);
        }
        if (part != ((long) (pos << 16))) {
            return null;
        }
        if (pos != len) {
            if (str.charAt(pos) == 'T') {
                pos++;
                if (pos != len) {
                    if (unit == Unit.month) {
                        return null;
                    }
                    part = scanPart(str, pos);
                    ch = (char) ((int) part);
                    if (ch == 'H') {
                        seconds += (long) (((int) (part >> 32)) * 3600);
                        pos = ((int) part) >> 16;
                        part = scanPart(str, pos);
                        ch = (char) ((int) part);
                    }
                    if (ch == Access.METHOD_CONTEXT) {
                        seconds += (long) (((int) (part >> 32)) * 60);
                        pos = ((int) part) >> 16;
                        part = scanPart(str, pos);
                        ch = (char) ((int) part);
                    }
                    if (ch == 'S' || ch == '.') {
                        seconds += (long) ((int) (part >> 32));
                        pos = ((int) part) >> 16;
                    }
                    if (ch == '.' && pos + 1 < len && Character.digit(str.charAt(pos), 10) >= 0) {
                        int i;
                        int nfrac = 0;
                        int pos2 = pos;
                        while (pos2 < len) {
                            pos = pos2 + 1;
                            ch = str.charAt(pos2);
                            int dig = Character.digit(ch, 10);
                            if (dig < 0) {
                                i = nfrac;
                                break;
                            }
                            if (nfrac < 9) {
                                nanos = (nanos * 10) + dig;
                            } else if (nfrac == 9 && dig >= 5) {
                                nanos++;
                            }
                            nfrac++;
                            pos2 = pos;
                        }
                        i = nfrac;
                        pos = pos2;
                        while (true) {
                            nfrac = i + 1;
                            if (i >= 9) {
                                break;
                            }
                            nanos *= 10;
                            i = nfrac;
                        }
                        if (ch != 'S') {
                            return null;
                        }
                    }
                }
            }
            return null;
        }
        if (pos != len) {
            return null;
        }
        Duration d = new Duration();
        if (negative) {
            months = -months;
            seconds = -seconds;
            nanos = -nanos;
        }
        d.months = months;
        d.seconds = seconds;
        d.nanos = nanos;
        d.unit = unit;
        return d;
    }

    public Numeric add(Object y, int k) {
        if (y instanceof Duration) {
            return add(this, (Duration) y, k);
        }
        if ((y instanceof DateTime) && k == 1) {
            return DateTime.add((DateTime) y, this, 1);
        }
        throw new IllegalArgumentException();
    }

    public Numeric mul(Object y) {
        if (y instanceof RealNum) {
            return times(this, ((RealNum) y).doubleValue());
        }
        return ((Numeric) y).mulReversed(this);
    }

    public Numeric mulReversed(Numeric x) {
        if (x instanceof RealNum) {
            return times(this, ((RealNum) x).doubleValue());
        }
        throw new IllegalArgumentException();
    }

    public static double div(Duration dur1, Duration dur2) {
        int months1 = dur1.months;
        int months2 = dur2.months;
        double sec1 = ((double) dur1.seconds) + (((double) dur1.nanos) * 1.0E-9d);
        double sec2 = ((double) dur2.seconds) + (((double) dur1.nanos) * 1.0E-9d);
        if (months2 == 0 && sec2 == 0.0d) {
            throw new ArithmeticException("divide duration by zero");
        }
        if (months2 == 0) {
            if (months1 == 0) {
                return sec1 / sec2;
            }
        } else if (sec2 == 0.0d && sec1 == 0.0d) {
            return ((double) months1) / ((double) months2);
        }
        throw new ArithmeticException("divide of incompatible durations");
    }

    public Numeric div(Object y) {
        if (y instanceof RealNum) {
            double dy = ((RealNum) y).doubleValue();
            if (dy != 0.0d && !Double.isNaN(dy)) {
                return times(this, 1.0d / dy);
            }
            throw new ArithmeticException("divide of duration by 0 or NaN");
        } else if (y instanceof Duration) {
            return new DFloNum(div(this, (Duration) y));
        } else {
            return ((Numeric) y).divReversed(this);
        }
    }

    public static Duration add(Duration x, Duration y, int k) {
        long months = ((long) x.months) + (((long) k) * ((long) y.months));
        long nanos = ((x.seconds * 1000000000) + ((long) x.nanos)) + (((long) k) * ((y.seconds * 1000000000) + ((long) y.nanos)));
        Duration d = new Duration();
        d.months = (int) months;
        d.seconds = (long) ((int) (nanos / 1000000000));
        d.nanos = (int) (nanos % 1000000000);
        if (x.unit != y.unit || x.unit == Unit.duration) {
            throw new ArithmeticException("cannot add these duration types");
        }
        d.unit = x.unit;
        return d;
    }

    public static Duration times(Duration x, double y) {
        if (x.unit == Unit.duration) {
            throw new IllegalArgumentException("cannot multiply general duration");
        }
        double months = ((double) x.months) * y;
        if (Double.isInfinite(months) || Double.isNaN(months)) {
            throw new ArithmeticException("overflow/NaN when multiplying a duration");
        }
        double nanos = ((double) ((x.seconds * 1000000000) + ((long) x.nanos))) * y;
        Duration d = new Duration();
        d.months = (int) Math.floor(0.5d + months);
        d.seconds = (long) ((int) (nanos / 1.0E9d));
        d.nanos = (int) (nanos % 1.0E9d);
        d.unit = x.unit;
        return d;
    }

    public static int compare(Duration x, Duration y) {
        long months = ((long) x.months) - ((long) y.months);
        long nanos = ((x.seconds * 1000000000) + ((long) x.nanos)) - ((y.seconds * 1000000000) + ((long) y.nanos));
        if (months < 0 && nanos <= 0) {
            return -1;
        }
        if (months > 0 && nanos >= 0) {
            return 1;
        }
        if (months != 0) {
            return -2;
        }
        if (nanos >= 0) {
            return nanos > 0 ? 1 : 0;
        } else {
            return -1;
        }
    }

    public int compare(Object obj) {
        if (obj instanceof Duration) {
            return compare(this, (Duration) obj);
        }
        throw new IllegalArgumentException();
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        int m = this.months;
        long s = this.seconds;
        int n = this.nanos;
        boolean neg = m < 0 || s < 0 || n < 0;
        if (neg) {
            m = -m;
            s = -s;
            n = -n;
            sbuf.append('-');
        }
        sbuf.append('P');
        int y = m / 12;
        if (y != 0) {
            sbuf.append(y);
            sbuf.append('Y');
            m -= y * 12;
        }
        if (m != 0) {
            sbuf.append(m);
            sbuf.append(Access.METHOD_CONTEXT);
        }
        long d = s / 86400;
        if (d != 0) {
            sbuf.append(d);
            sbuf.append('D');
            s -= 86400 * d;
        }
        if (s != 0 || n != 0) {
            sbuf.append('T');
            long hr = s / 3600;
            if (hr != 0) {
                sbuf.append(hr);
                sbuf.append('H');
                s -= 3600 * hr;
            }
            long mn = s / 60;
            if (mn != 0) {
                sbuf.append(mn);
                sbuf.append(Access.METHOD_CONTEXT);
                s -= 60 * mn;
            }
            if (!(s == 0 && n == 0)) {
                sbuf.append(s);
                appendNanoSeconds(n, sbuf);
                sbuf.append('S');
            }
        } else if (sbuf.length() == 1) {
            sbuf.append(this.unit == Unit.month ? "0M" : "T0S");
        }
        return sbuf.toString();
    }

    static void appendNanoSeconds(int nanoSeconds, StringBuffer sbuf) {
        if (nanoSeconds != 0) {
            sbuf.append('.');
            int pos = sbuf.length();
            sbuf.append(nanoSeconds);
            int pad = (pos + 9) - sbuf.length();
            while (true) {
                pad--;
                if (pad < 0) {
                    break;
                }
                sbuf.insert(pos, '0');
            }
            int len = pos + 9;
            do {
                len--;
            } while (sbuf.charAt(len) == '0');
            sbuf.setLength(len + 1);
        }
    }

    private static long scanPart(String str, int start) {
        int i = start;
        long val = -1;
        int len = str.length();
        while (i < len) {
            char ch = str.charAt(i);
            i++;
            int dig = Character.digit(ch, 10);
            if (dig >= 0) {
                val = val < 0 ? (long) dig : (10 * val) + ((long) dig);
                if (val > 2147483647L) {
                    return -1;
                }
            } else if (val < 0) {
                return (long) (start << 16);
            } else {
                return ((val << 32) | ((long) (i << 16))) | ((long) ch);
            }
        }
        if (val < 0) {
            return (long) (start << 16);
        }
        return -1;
    }

    public int getYears() {
        return this.months / 12;
    }

    public int getMonths() {
        return this.months % 12;
    }

    public int getDays() {
        return (int) (this.seconds / 86400);
    }

    public int getHours() {
        return (int) ((this.seconds / 3600) % 24);
    }

    public int getMinutes() {
        return (int) ((this.seconds / 60) % 60);
    }

    public int getSecondsOnly() {
        return (int) (this.seconds % 60);
    }

    public int getNanoSecondsOnly() {
        return this.nanos;
    }

    public int getTotalMonths() {
        return this.months;
    }

    public long getTotalSeconds() {
        return this.seconds;
    }

    public long getTotalMinutes() {
        return this.seconds / 60;
    }

    public long getNanoSeconds() {
        return (this.seconds * 1000000000) + ((long) this.nanos);
    }

    public boolean isZero() {
        return this.months == 0 && this.seconds == 0 && this.nanos == 0;
    }

    public boolean isExact() {
        return false;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.months);
        out.writeLong(this.seconds);
        out.writeInt(this.nanos);
        out.writeObject(this.unit);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.months = in.readInt();
        this.seconds = in.readLong();
        this.nanos = in.readInt();
        this.unit = (Unit) in.readObject();
    }

    public Unit unit() {
        return this.unit;
    }

    public Complex number() {
        throw new Error("number needs to be implemented!");
    }

    public int hashCode() {
        return (this.months ^ ((int) this.seconds)) ^ this.nanos;
    }

    public static boolean equals(Duration x, Duration y) {
        return x.months == y.months && x.seconds == y.seconds && x.nanos == y.nanos;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Duration)) {
            return false;
        }
        return equals(this, (Duration) obj);
    }
}
