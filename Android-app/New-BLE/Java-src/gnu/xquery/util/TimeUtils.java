package gnu.xquery.util;

import android.support.v4.media.TransportMediator;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XTimeType;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.DateTime;
import gnu.math.Duration;
import gnu.math.IntNum;
import gnu.xml.TextUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.TimeZone;

public class TimeUtils {
    static final ThreadLocal<DateTime> currentDateTimeLocal = new ThreadLocal();

    static DateTime coerceToDateTime(String fun, Object value) {
        if (XTimeType.dateTimeType.isInstance(value)) {
            return (DateTime) value;
        }
        if ((value instanceof KNode) || (value instanceof UntypedAtomic)) {
            return XTimeType.parseDateTime(TextUtils.stringValue(value), TransportMediator.KEYCODE_MEDIA_PLAY);
        }
        throw new WrongType(fun, 1, value, "xs:dateTime");
    }

    static DateTime coerceToDate(String fun, Object value) {
        if (XTimeType.dateType.isInstance(value)) {
            return (DateTime) value;
        }
        if ((value instanceof KNode) || (value instanceof UntypedAtomic)) {
            return XTimeType.parseDateTime(TextUtils.stringValue(value), 14);
        }
        throw new WrongType(fun, 1, value, "xs:date");
    }

    static DateTime coerceToTime(String fun, Object value) {
        if (XTimeType.timeType.isInstance(value)) {
            return (DateTime) value;
        }
        if ((value instanceof KNode) || (value instanceof UntypedAtomic)) {
            return XTimeType.parseDateTime(TextUtils.stringValue(value), DateTime.TIME_MASK);
        }
        throw new WrongType(fun, 1, value, "xs:time");
    }

    static Duration coerceToDuration(String fun, Object value) {
        if (value instanceof Duration) {
            return (Duration) value;
        }
        throw new WrongType(fun, 1, value, "xs:duration");
    }

    static Object timeZoneFromXTime(DateTime time) {
        if (time.isZoneUnspecified()) {
            return Values.empty;
        }
        return Duration.makeMinutes(time.getZoneMinutes());
    }

    static IntNum asInteger(int value) {
        return IntNum.make(value);
    }

    public static Object yearFromDateTime(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDateTime("year-from-dateTime", arg).getYear());
    }

    public static Object monthFromDateTime(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDateTime("month-from-dateTime", arg).getMonth());
    }

    public static Object dayFromDateTime(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDateTime("day-from-dateTime", arg).getDay());
    }

    public static Object hoursFromDateTime(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDateTime("hours-from-dateTime", arg).getHours());
    }

    public static Object minutesFromDateTime(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDateTime("minutes-from-dateTime", arg).getMinutes());
    }

    static Number getSeconds(DateTime date) {
        int seconds = date.getSecondsOnly();
        long nanos = (long) date.getNanoSecondsOnly();
        if (nanos == 0) {
            return IntNum.make(seconds);
        }
        return new BigDecimal(BigInteger.valueOf(nanos + (((long) seconds) * 1000000000)), 9);
    }

    public static Object secondsFromDateTime(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : getSeconds(coerceToDateTime("seconds-from-dateTime", arg));
    }

    public static Object timezoneFromDateTime(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : timeZoneFromXTime(coerceToDateTime("timezone-from-datetime", arg));
    }

    public static Object yearFromDate(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDate("year-from-date", arg).getYear());
    }

    public static Object monthFromDate(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDate("month-from-date", arg).getMonth());
    }

    public static Object dayFromDate(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDate("day-from-date", arg).getDay());
    }

    public static Object timezoneFromDate(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : timeZoneFromXTime(coerceToDate("timezone-from-date", arg));
    }

    public static Object hoursFromTime(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToTime("hours-from-time", arg).getHours());
    }

    public static Object minutesFromTime(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToTime("minutes-from-time", arg).getMinutes());
    }

    public static Object secondsFromTime(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : getSeconds(coerceToTime("seconds-from-time", arg));
    }

    public static Object timezoneFromTime(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : timeZoneFromXTime(coerceToTime("timezone-from-time", arg));
    }

    public static Object yearsFromDuration(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDuration("years-from-duration", arg).getYears());
    }

    public static Object monthsFromDuration(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDuration("months-from-duration", arg).getMonths());
    }

    public static Object daysFromDuration(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDuration("days-from-duration", arg).getDays());
    }

    public static Object hoursFromDuration(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDuration("hours-from-duration", arg).getHours());
    }

    public static Object minutesFromDuration(Object arg) {
        return (arg == null || arg == Values.empty) ? arg : asInteger(coerceToDuration("minutes-from-duration", arg).getMinutes());
    }

    public static BigDecimal secondsBigDecimalFromDuration(long s, int n) {
        if (n == 0) {
            return BigDecimal.valueOf(s);
        }
        int scale = 9;
        boolean huge = ((long) ((int) s)) != s;
        long ns = huge ? (long) n : (1000000000 * s) + ((long) n);
        while (ns % 10 == 0) {
            ns /= 10;
            scale--;
        }
        BigDecimal dec = new BigDecimal(BigInteger.valueOf(ns), scale);
        if (huge) {
            return BigDecimal.valueOf(s).add(dec);
        }
        return dec;
    }

    public static Object secondsFromDuration(Object arg) {
        if (arg == null || arg == Values.empty) {
            return arg;
        }
        Duration d = coerceToDuration("seconds-from-duration", arg);
        int s = d.getSecondsOnly();
        int n = d.getNanoSecondsOnly();
        if (n == 0) {
            return asInteger(s);
        }
        return secondsBigDecimalFromDuration((long) s, n);
    }

    public static Duration getImplicitTimezone() {
        return Duration.makeMinutes(TimeZone.getDefault().getRawOffset() / 60000);
    }

    public static Object adjustDateTimeToTimezone(Object time) {
        return adjustDateTimeToTimezone(time, getImplicitTimezone());
    }

    public static Object adjustDateTimeToTimezone(Object time, Object zone) {
        return (time == Values.empty || time == null) ? time : adjustDateTimeToTimezoneRaw(coerceToDateTime("adjust-dateTime-to-timezone", time), zone);
    }

    public static Object adjustDateToTimezone(Object time) {
        return adjustDateToTimezone(time, getImplicitTimezone());
    }

    public static Object adjustDateToTimezone(Object time, Object zone) {
        return (time == Values.empty || time == null) ? time : adjustDateTimeToTimezoneRaw(coerceToDate("adjust-date-to-timezone", time), zone);
    }

    public static Object adjustTimeToTimezone(Object time) {
        return adjustTimeToTimezone(time, getImplicitTimezone());
    }

    public static Object adjustTimeToTimezone(Object time, Object zone) {
        return (time == Values.empty || time == null) ? time : adjustDateTimeToTimezoneRaw(coerceToTime("adjust-time-to-timezone", time), zone);
    }

    static Object adjustDateTimeToTimezoneRaw(DateTime dtime, Object zone) {
        if (zone == Values.empty || zone == null) {
            return dtime.withZoneUnspecified();
        }
        Duration d = (Duration) zone;
        if (d.getNanoSecondsOnly() == 0 && d.getSecondsOnly() == 0) {
            int delta = (int) d.getTotalMinutes();
            if (delta >= -840 && delta <= 840) {
                return dtime.adjustTimezone(delta);
            }
            throw new IllegalArgumentException("timezone offset out of range");
        }
        throw new IllegalArgumentException("timezone offset with fractional minute");
    }

    public static DateTime now() {
        return XTimeType.dateTimeType.now();
    }

    public static Object dateTime(Object arg1, Object arg2) {
        if (arg1 == null || arg1 == Values.empty) {
            return arg1;
        }
        if (arg2 == null || arg2 == Values.empty) {
            return arg2;
        }
        boolean hasZone1;
        DateTime date = coerceToDate("dateTime", arg1);
        DateTime time = coerceToTime("dateTime", arg2);
        StringBuffer sbuf = new StringBuffer();
        date.toStringDate(sbuf);
        sbuf.append('T');
        time.toStringTime(sbuf);
        if (date.isZoneUnspecified()) {
            hasZone1 = false;
        } else {
            hasZone1 = true;
        }
        boolean hasZone2;
        if (time.isZoneUnspecified()) {
            hasZone2 = false;
        } else {
            hasZone2 = true;
        }
        if (hasZone1 || hasZone2) {
            int zone1 = date.getZoneMinutes();
            int zone2 = time.getZoneMinutes();
            if (hasZone1 && hasZone2 && zone1 != zone2) {
                throw new Error("dateTime: incompatible timezone in arguments");
            }
            if (!hasZone1) {
                zone1 = zone2;
            }
            DateTime.toStringZone(zone1, sbuf);
        }
        return (DateTime) XTimeType.dateTimeType.valueOf(sbuf.toString());
    }

    public static DateTime currentDateTime() {
        DateTime current = (DateTime) currentDateTimeLocal.get();
        if (current != null) {
            return current;
        }
        current = now();
        currentDateTimeLocal.set(current);
        return current;
    }

    public static DateTime currentDate() {
        return currentDateTime().cast(14);
    }

    public static DateTime currentTime() {
        return currentDateTime().cast(DateTime.TIME_MASK);
    }

    public static Object implicitTimezone() {
        return timeZoneFromXTime(currentDateTime());
    }
}
