package com.iamrajendra.analogclock.utlis;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Date {
    public static final String TIME = "h:mm a";
    public static final String FULL = "EEEE,MMMM d,yyyy";
    public static final String LONG = "MMM dd,yyyy";
    public static final String MEDIUM = "MMMM dd,yyyy";
    public static final String SHORT = "dd/M/yyyy";
    public static final String DATE_MONTH = "dd MMMM";
    public static final String LONG_MONTH_DATE = "dd MMM,yyyy";
    public static final String SHORT_DATE = "dd MMM";
    public static final String INPUT_DATEFORMAT = "yyyy-MM-dd";
    public static final String TIME_STAMP = "MMM dd, yyy h:mm a";
    public static final String TIME_STAMP_NEW = "h:mm a";
    public static final String SERVER_TIME_STAMP = "yyyy-MM-dd h:mm:ss";
    public static final String SNOOZE_TIME_STAMP = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_MONTH_TIME = "dd MMM yyyy";
    public static final String DATE_MONTH_TIME_YR = "dd MMM yy";
    public static final String CHAT_FORMAT = "MMMM dd yyyy";
    public static final String CHAT_FORMAT_DEFAULT = "EEEE, MMMM d";
    public static final String LABLE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZ";
    private static String TAG = Date.class.getSimpleName();

    public static String getTime(String format, java.util.Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(date);
    }

    public static String getTime(String format, long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(calendar.getTime());
    }

    public static boolean compare(long startDate, long endDate) {
        /*Calendar calendarA = Calendar.getInstance();
        Calendar calendarB = Calendar.getInstance();
        calendarA.setTimeInMillis(startDate);
        calendarB.setTimeInMillis(endDate);

        int hourDiff = calendarB.get(Calendar.HOUR_OF_DAY) - calendarA.get(Calendar.HOUR_OF_DAY);
        int minDiff = calendarB.get(Calendar.MINUTE) - calendarA.get(Calendar.MINUTE);

        if (calendarB.get(Calendar.HOUR_OF_DAY)>calendarA.get(Calendar.HOUR_OF_DAY)) {
            return false;
        }

        if (hourDiff <= 0 && minDiff < 30 || minDiff <= 0) {
            return false;
        }
*/
        return true;
    }

    public static int today() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());


        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int dayOfWeek(long time) {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(time);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int weekOfMonth() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());


        return calendar.get(Calendar.WEEK_OF_MONTH);
    }


}
