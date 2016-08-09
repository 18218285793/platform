
package cn.com.signheart.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_PATTERN = "HH:mm:ss";

    public DateUtil() {
    }

    public static String format(long millis, String pattern) {
        return format(new Date(millis), pattern);
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String formartDate(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    public static String formartTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    public static String formartDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatCurrent(String pattern) {
        return format(new Date(), pattern);
    }

    public static String formartCurrentDate() {
        return format(new Date(), "yyyy-MM-dd");
    }

    public static String formartCurrentTime() {
        return format(new Date(), "HH:mm:ss");
    }

    public static String formartCurrentDateTime() {
        return format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getTheDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static int compareDate(Date start, Date end) {
        if(start == null && end == null) {
            return 0;
        } else if(end == null) {
            return 1;
        } else {
            if(start == null) {
                start = new Date();
            }

            start = getTheDate(start);
            end = getTheDate(end);
            return start.compareTo(end);
        }
    }

    public static Date parse(String dateString, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        try {
            return formatter.parse(dateString);
        } catch (ParseException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static Date addYears(Date date, int amount) {
        return add(date, 1, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, 2, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return add(date, 3, amount);
    }

    public static Date addDays(Date date, int amount) {
        return add(date, 5, amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, 11, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, 12, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return add(date, 13, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return add(date, 14, amount);
    }

    private static Date add(Date date, int calendarField, int amount) {
        if(date == null) {
            throw new IllegalArgumentException("日期对象不允许为null!");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }
}
