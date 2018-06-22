package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@SimpleObject
public final class Dates {
    public static final int DATE_APRIL = 3;
    public static final int DATE_AUGUST = 7;
    public static final int DATE_DAY = 5;
    public static final int DATE_DECEMBER = 11;
    public static final int DATE_FEBRUARY = 1;
    public static final int DATE_FRIDAY = 6;
    public static final int DATE_HOUR = 11;
    public static final int DATE_JANUARY = 0;
    public static final int DATE_JULY = 6;
    public static final int DATE_JUNE = 5;
    public static final int DATE_MARCH = 2;
    public static final int DATE_MAY = 4;
    public static final int DATE_MILLISECOND = 14;
    public static final int DATE_MINUTE = 12;
    public static final int DATE_MONDAY = 2;
    public static final int DATE_MONTH = 2;
    public static final int DATE_NOVEMBER = 10;
    public static final int DATE_OCTOBER = 9;
    public static final int DATE_SATURDAY = 7;
    public static final int DATE_SECOND = 13;
    public static final int DATE_SEPTEMBER = 8;
    public static final int DATE_SUNDAY = 1;
    public static final int DATE_THURSDAY = 5;
    public static final int DATE_TUESDAY = 3;
    public static final int DATE_WEDNESDAY = 4;
    public static final int DATE_WEEK = 3;
    public static final int DATE_YEAR = 1;

    private Dates() {
    }

    @SimpleFunction
    public static void DateAdd(Calendar date, int intervalKind, int interval) {
        switch (intervalKind) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 11:
            case 12:
            case 13:
                date.add(intervalKind, interval);
                return;
            default:
                throw new IllegalArgumentException("illegal date/time interval kind in function DateAdd()");
        }
    }

    @SimpleFunction
    public static void DateAddInMillis(Calendar date, long millis) {
        date.setTimeInMillis(date.getTimeInMillis() + millis);
    }

    @SimpleFunction
    public static Calendar DateValue(String value) {
        Calendar date = new GregorianCalendar();
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            dateTimeFormat.setLenient(true);
            date.setTime(dateTimeFormat.parse(value));
        } catch (ParseException e) {
            DateFormat dateFormat;
            try {
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                dateFormat.setLenient(true);
                date.setTime(dateFormat.parse(value));
            } catch (ParseException e2) {
                try {
                    dateFormat = new SimpleDateFormat("HH:mm");
                    dateFormat.setLenient(true);
                    date.setTime(dateFormat.parse(value));
                } catch (ParseException e3) {
                    throw new IllegalArgumentException("illegal date/time format in function DateValue()");
                }
            }
        }
        return date;
    }

    @SimpleFunction
    public static int Day(Calendar date) {
        return date.get(5);
    }

    @SimpleFunction
    public static long ConvertDuration(long duration, int intervalKind) {
        switch (intervalKind) {
            case 3:
                return ((((duration / 1000) / 60) / 60) / 24) / 7;
            case 5:
                return (((duration / 1000) / 60) / 60) / 24;
            case 11:
                return ((duration / 1000) / 60) / 60;
            case 12:
                return (duration / 1000) / 60;
            case 13:
                return duration / 1000;
            default:
                throw new IllegalArgumentException("illegal date/time interval kind in function Duration()");
        }
    }

    @SimpleFunction
    public static String FormatDateTime(Calendar date, String pattern) {
        SimpleDateFormat formatdate = new SimpleDateFormat();
        if (pattern.length() == 0) {
            formatdate.applyPattern("MMM d, yyyy HH:mm:ss a");
        } else {
            formatdate.applyPattern(pattern);
        }
        return formatdate.format(date.getTime());
    }

    @SimpleFunction
    public static String FormatDate(Calendar date, String pattern) {
        SimpleDateFormat formatdate = new SimpleDateFormat();
        if (pattern.length() == 0) {
            formatdate.applyPattern("MMM d, yyyy");
        } else {
            formatdate.applyPattern(pattern);
        }
        return formatdate.format(date.getTime());
    }

    @SimpleFunction
    public static String FormatTime(Calendar date) {
        return DateFormat.getTimeInstance(2).format(date.getTime());
    }

    @SimpleFunction
    public static Calendar DateInstant(int year, int month, int day) {
        String year_str = String.valueOf(year);
        String month_str = String.valueOf(month);
        String day_str = String.valueOf(day);
        if (month < 10) {
            month_str = "0" + month_str;
        }
        if (day < 10) {
            day_str = "0" + day_str;
        }
        return DateValue(month_str + "/" + day_str + "/" + year_str);
    }

    @SimpleFunction
    public static Calendar TimeInstant(int hour, int minute) {
        String hour_str = String.valueOf(hour);
        String minute_str = String.valueOf(minute);
        if (hour < 10) {
            hour_str = "0" + hour_str;
        }
        if (minute < 10) {
            minute_str = "0" + minute_str;
        }
        return DateValue(hour_str + ":" + minute_str);
    }

    @SimpleFunction
    public static int Hour(Calendar date) {
        return date.get(11);
    }

    @SimpleFunction
    public static int Minute(Calendar date) {
        return date.get(12);
    }

    @SimpleFunction
    public static int Month(Calendar date) {
        return date.get(2);
    }

    @SimpleFunction
    public static String MonthName(Calendar date) {
        return String.format("%1$tB", new Object[]{date});
    }

    @SimpleFunction
    public static Calendar Now() {
        return new GregorianCalendar();
    }

    @SimpleFunction
    public static int Second(Calendar date) {
        return date.get(13);
    }

    @SimpleFunction
    public static long Timer() {
        return System.currentTimeMillis();
    }

    @SimpleFunction
    public static int Weekday(Calendar date) {
        return date.get(7);
    }

    @SimpleFunction
    public static String WeekdayName(Calendar date) {
        return String.format("%1$tA", new Object[]{date});
    }

    @SimpleFunction
    public static int Year(Calendar date) {
        return date.get(1);
    }
}
