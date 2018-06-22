package gnu.kawa.xml;

import android.support.v4.media.TransportMediator;
import gnu.bytecode.ClassType;
import gnu.math.DateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class XTimeType extends XDataType {
    public static final XTimeType dateTimeType = new XTimeType("dateTime", 20);
    public static final XTimeType dateType = new XTimeType("date", 21);
    private static TimeZone fixedTimeZone;
    public static final XTimeType gDayType = new XTimeType("gDay", 26);
    public static final XTimeType gMonthDayType = new XTimeType("gMonthDay", 25);
    public static final XTimeType gMonthType = new XTimeType("gMonth", 27);
    public static final XTimeType gYearMonthType = new XTimeType("gYearMonth", 23);
    public static final XTimeType gYearType = new XTimeType("gYear", 24);
    public static final XTimeType timeType = new XTimeType("time", 22);
    static ClassType typeDateTime = ClassType.make("gnu.math.DateTime");

    XTimeType(String name, int code) {
        super(name, typeDateTime, code);
    }

    static int components(int typeCode) {
        switch (typeCode) {
            case 20:
            case 28:
                return TransportMediator.KEYCODE_MEDIA_PLAY;
            case 21:
                return 14;
            case 22:
                return DateTime.TIME_MASK;
            case 23:
                return 6;
            case 24:
                return 2;
            case 25:
                return 12;
            case 26:
                return 8;
            case 27:
                return 4;
            case 29:
                return 6;
            case 30:
                return 120;
            default:
                return 0;
        }
    }

    public DateTime now() {
        return new DateTime(components(this.typeCode) | 128, (GregorianCalendar) Calendar.getInstance(fixedTimeZone()));
    }

    private static synchronized TimeZone fixedTimeZone() {
        TimeZone timeZone;
        synchronized (XTimeType.class) {
            if (fixedTimeZone == null) {
                fixedTimeZone = DateTime.minutesToTimeZone(TimeZone.getDefault().getRawOffset() / 60000);
            }
            timeZone = fixedTimeZone;
        }
        return timeZone;
    }

    public static DateTime parseDateTime(String value, int mask) {
        DateTime time = DateTime.parse(value, mask);
        if (time.isZoneUnspecified()) {
            time.setTimeZone(fixedTimeZone());
        }
        return time;
    }

    public Object valueOf(String value) {
        return parseDateTime(value, components(this.typeCode));
    }

    public boolean isInstance(Object obj) {
        if ((obj instanceof DateTime) && components(this.typeCode) == ((DateTime) obj).components()) {
            return true;
        }
        return false;
    }
}
