package com.cxj.springbootcxj.cxjUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author : ChuXianJie
 * @date : 2019-12-30 14:36
 */
public class UtilDateTime {
    private static final String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static final String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private static final String[][] timevals = new String[][]{{"1000", "millisecond"}, {"60", "second"}, {"60", "minute"}, {"24", "hour"}, {"168", "week"}};
    private static final DecimalFormat df = new DecimalFormat("0.00;-0.00");
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String TIME_FORMAT = "HH:mm:ss";

    public UtilDateTime() {
    }

    public static double getInterval(Date from, Date thru) {
        return thru != null ? (double)(thru.getTime() - from.getTime()) : 0.0D;
    }

    public static int getIntervalInDays(Timestamp from, Timestamp thru) {
        return thru != null ? (int)((thru.getTime() - from.getTime()) / 86400000L) : 0;
    }

    public static Timestamp addDaysToTimestamp(Timestamp start, int days) {
        return new Timestamp(start.getTime() + 86400000L * (long)days);
    }

    public static Timestamp addDaysToTimestamp(Timestamp start, Double days) {
        return new Timestamp(start.getTime() + (long)((int)(8.64E7D * days)));
    }

    public static double getInterval(Timestamp from, Timestamp thru) {
        return thru != null ? (double)(thru.getTime() - from.getTime() + (long)((thru.getNanos() - from.getNanos()) / 1000000)) : 0.0D;
    }

    public static Timestamp nowTimestamp() {
        return getTimestamp(System.currentTimeMillis());
    }

    public static Timestamp getTimestamp(long time) {
        return new Timestamp(time);
    }

    public static Timestamp getTimestamp(String milliSecs) throws NumberFormatException {
        return new Timestamp(Long.parseLong(milliSecs));
    }

    public static String nowAsString() {
        return Long.toString(System.currentTimeMillis());
    }

    public static String nowDateString() {
        return nowDateString("yyyyMMddHHmmss");
    }

    public static String nowDateString(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static Date nowDate() {
        return new Date();
    }

    public static Timestamp getDayStart(Timestamp stamp) {
        return getDayStart(stamp, 0);
    }

    public static Timestamp getDayStart(Timestamp stamp, int daysLater) {
        return getDayStart(stamp, daysLater, TimeZone.getDefault(), Locale.getDefault());
    }

    public static Timestamp getNextDayStart(Timestamp stamp) {
        return getDayStart(stamp, 1);
    }

    public static Timestamp getDayEnd(Timestamp stamp) {
        return getDayEnd(stamp, 0L);
    }

    public static Timestamp getDayEnd(Timestamp stamp, Long daysLater) {
        return getDayEnd(stamp, daysLater, TimeZone.getDefault(), Locale.getDefault());
    }

    public static Timestamp getYearStart(Timestamp stamp) {
        return getYearStart(stamp, 0, 0, 0);
    }

    public static Timestamp getYearStart(Timestamp stamp, int daysLater) {
        return getYearStart(stamp, daysLater, 0, 0);
    }

    public static Timestamp getYearStart(Timestamp stamp, int daysLater, int yearsLater) {
        return getYearStart(stamp, daysLater, 0, yearsLater);
    }

    public static Timestamp getYearStart(Timestamp stamp, int daysLater, int monthsLater, int yearsLater) {
        return getYearStart(stamp, daysLater, monthsLater, yearsLater, TimeZone.getDefault(), Locale.getDefault());
    }

    public static Timestamp getYearStart(Timestamp stamp, Number daysLater, Number monthsLater, Number yearsLater) {
        return getYearStart(stamp, daysLater == null ? 0 : daysLater.intValue(), monthsLater == null ? 0 : monthsLater.intValue(), yearsLater == null ? 0 : yearsLater.intValue());
    }

    public static Timestamp getMonthStart(Timestamp stamp) {
        return getMonthStart(stamp, 0, 0);
    }

    public static Timestamp getMonthStart(Timestamp stamp, int daysLater) {
        return getMonthStart(stamp, daysLater, 0);
    }

    public static Timestamp getMonthStart(Timestamp stamp, int daysLater, int monthsLater) {
        return getMonthStart(stamp, daysLater, monthsLater, TimeZone.getDefault(), Locale.getDefault());
    }

    public static Timestamp getWeekStart(Timestamp stamp) {
        return getWeekStart(stamp, 0, 0);
    }

    public static Timestamp getWeekStart(Timestamp stamp, int daysLater) {
        return getWeekStart(stamp, daysLater, 0);
    }

    public static Timestamp getWeekStart(Timestamp stamp, int daysLater, int weeksLater) {
        return getWeekStart(stamp, daysLater, weeksLater, TimeZone.getDefault(), Locale.getDefault());
    }

    public static Timestamp getWeekEnd(Timestamp stamp) {
        return getWeekEnd(stamp, TimeZone.getDefault(), Locale.getDefault());
    }

    public static Calendar toCalendar(Timestamp stamp) {
        Calendar cal = Calendar.getInstance();
        if (stamp != null) {
            cal.setTimeInMillis(stamp.getTime());
        }

        return cal;
    }

    public static java.sql.Date toSqlDate(String date) {
        Date newDate = toDate(date, "00:00:00");
        return newDate != null ? new java.sql.Date(newDate.getTime()) : null;
    }

    public static java.sql.Date toSqlDate(String monthStr, String dayStr, String yearStr) {
        Date newDate = toDate(monthStr, dayStr, yearStr, "0", "0", "0");
        return newDate != null ? new java.sql.Date(newDate.getTime()) : null;
    }

    public static java.sql.Date toSqlDate(int month, int day, int year) {
        Date newDate = toDate(month, day, year, 0, 0, 0);
        return newDate != null ? new java.sql.Date(newDate.getTime()) : null;
    }

    public static Time toSqlTime(String time) {
        Date newDate = toDate("1/1/1970", time);
        return newDate != null ? new Time(newDate.getTime()) : null;
    }

    public static Time toSqlTime(String hourStr, String minuteStr, String secondStr) {
        Date newDate = toDate("0", "0", "0", hourStr, minuteStr, secondStr);
        return newDate != null ? new Time(newDate.getTime()) : null;
    }

    public static Time toSqlTime(int hour, int minute, int second) {
        Date newDate = toDate(0, 0, 0, hour, minute, second);
        return newDate != null ? new Time(newDate.getTime()) : null;
    }

    public static Timestamp toTimestamp(String dateTime) {
        Date newDate = toDate(dateTime);
        return newDate != null ? new Timestamp(newDate.getTime()) : null;
    }

    public static Timestamp toTimestampNew(String dateTime) {
        String date = dateTime.substring(0, dateTime.indexOf(" "));
        String time = dateTime.substring(dateTime.indexOf(" ") + 1);
        Date newDate = toDateNew(date, time);
        return newDate != null ? new Timestamp(newDate.getTime()) : null;
    }

    public static Timestamp toTimestamp(String date, String time) {
        Date newDate = toDate(date, time);
        return newDate != null ? new Timestamp(newDate.getTime()) : null;
    }

    public static Timestamp toTimestamp(String monthStr, String dayStr, String yearStr, String hourStr, String minuteStr, String secondStr) {
        Date newDate = toDate(monthStr, dayStr, yearStr, hourStr, minuteStr, secondStr);
        return newDate != null ? new Timestamp(newDate.getTime()) : null;
    }

    public static Timestamp toTimestamp(int month, int day, int year, int hour, int minute, int second) {
        Date newDate = toDate(month, day, year, hour, minute, second);
        return newDate != null ? new Timestamp(newDate.getTime()) : null;
    }

    public static Timestamp toTimestamp(Date date) {
        return date == null ? null : new Timestamp(date.getTime());
    }

    public static Date toDate(String dateTime) {
        if (dateTime == null) {
            return null;
        } else {
            String date = dateTime.substring(0, dateTime.indexOf(" "));
            String time = dateTime.substring(dateTime.indexOf(" ") + 1);
            return toDate(date, time);
        }
    }

    public static Date toDate(String date, String time) {
        if (date != null && time != null) {
            int dateSlash1 = date.indexOf("/");
            int dateSlash2 = date.lastIndexOf("/");
            if (dateSlash1 > 0 && dateSlash1 != dateSlash2) {
                int timeColon1 = time.indexOf(":");
                int timeColon2 = time.lastIndexOf(":");
                if (timeColon1 <= 0) {
                    return null;
                } else {
                    String month = date.substring(0, dateSlash1);
                    String day = date.substring(dateSlash1 + 1, dateSlash2);
                    String year = date.substring(dateSlash2 + 1);
                    String hour = time.substring(0, timeColon1);
                    String minute;
                    String second;
                    if (timeColon1 == timeColon2) {
                        minute = time.substring(timeColon1 + 1);
                        second = "0";
                    } else {
                        minute = time.substring(timeColon1 + 1, timeColon2);
                        second = time.substring(timeColon2 + 1);
                    }

                    return toDate(month, day, year, hour, minute, second);
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Date toDateForNoSpace(String date, String time) {
        if (date != null && time != null) {
            if (date.length() == 8 && time.length() == 9) {
                String year = date.substring(0, 4);
                String month = date.substring(4, 6);
                String day = date.substring(6, 8);
                String hour = time.substring(0, 2);
                String minute = time.substring(2, 4);
                String second = time.substring(4, 6);
                String minsecond = time.substring(6, 9);

                int intmonth;
                int intday;
                int intyear;
                int inthour;
                int intminute;
                int intsecond;
                int intminsecond;
                try {
                    intmonth = Integer.parseInt(month);
                    intday = Integer.parseInt(day);
                    intyear = Integer.parseInt(year);
                    inthour = Integer.parseInt(hour);
                    intminute = Integer.parseInt(minute);
                    intsecond = Integer.parseInt(second);
                    intminsecond = Integer.parseInt(minsecond);
                } catch (Exception var19) {
                    var19.printStackTrace();
                    return null;
                }

                Calendar calendar = Calendar.getInstance();

                try {
                    calendar.set(intyear, intmonth - 1, intday, inthour, intminute, intsecond);
                    calendar.set(14, intminsecond);
                } catch (Exception var18) {
                    return null;
                }

                return new Date(calendar.getTime().getTime());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Date toDateNew(String date, String time) {
        if (date != null && time != null) {
            int dateSlash1 = date.indexOf("-");
            int dateSlash2 = date.lastIndexOf("-");
            if (dateSlash1 > 0 && dateSlash1 != dateSlash2) {
                int timeColon1 = time.indexOf(":");
                int timeColon2 = time.lastIndexOf(":");
                if (timeColon1 <= 0) {
                    return null;
                } else {
                    String year = date.substring(0, dateSlash1);
                    String month = date.substring(dateSlash1 + 1, dateSlash2);
                    String day = date.substring(dateSlash2 + 1);
                    String hour = time.substring(0, timeColon1);
                    String minute;
                    String second;
                    if (timeColon1 == timeColon2) {
                        minute = time.substring(timeColon1 + 1);
                        second = "0";
                    } else {
                        minute = time.substring(timeColon1 + 1, timeColon2);
                        second = time.substring(timeColon2 + 1);
                        if (second.indexOf(".") > -1) {
                            second = second.substring(0, second.indexOf("."));
                        }
                    }

                    return toDate(month, day, year, hour, minute, second);
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Date toDate(String monthStr, String dayStr, String yearStr, String hourStr, String minuteStr, String secondStr) {
        int month;
        int day;
        int year;
        int hour;
        int minute;
        int second;
        try {
            month = Integer.parseInt(monthStr);
            day = Integer.parseInt(dayStr);
            year = Integer.parseInt(yearStr);
            hour = Integer.parseInt(hourStr);
            minute = Integer.parseInt(minuteStr);
            second = Integer.parseInt(secondStr);
        } catch (Exception var13) {
            var13.printStackTrace();
            return null;
        }

        return toDate(month, day, year, hour, minute, second);
    }

    public static Date toDate(int month, int day, int year, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.set(year, month - 1, day, hour, minute, second);
            calendar.set(14, 0);
        } catch (Exception var8) {
            return null;
        }

        return new Date(calendar.getTime().getTime());
    }

    public static String toDateString(Date date, String format) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat dateFormat = null;
            if (format != null) {
                dateFormat = new SimpleDateFormat(format);
            } else {
                dateFormat = new SimpleDateFormat();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return dateFormat.format(date);
        }
    }

    public static String toDateString(Date date) {
        return toDateString(date, "MM/dd/yyyy");
    }

    public static String toTimeString(Date date) {
        if (date == null) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return toTimeString(calendar.get(11), calendar.get(12), calendar.get(13));
        }
    }

    public static String toTimeString(int hour, int minute, int second) {
        String hourStr;
        if (hour < 10) {
            hourStr = "0" + hour;
        } else {
            hourStr = "" + hour;
        }

        String minuteStr;
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = "" + minute;
        }

        String secondStr;
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = "" + second;
        }

        return second == 0 ? hourStr + ":" + minuteStr : hourStr + ":" + minuteStr + ":" + secondStr;
    }

    public static String toDateTimeString(Date date) {
        if (date == null) {
            return "";
        } else {
            String dateString = toDateString(date);
            String timeString = toTimeString(date);
            return dateString != null && timeString != null ? dateString + " " + timeString : "";
        }
    }

    public static String toGmtTimestampString(Timestamp timestamp) {
        DateFormat df = DateFormat.getDateTimeInstance();
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(timestamp);
    }

    public static Timestamp monthBegin() {
        Calendar mth = Calendar.getInstance();
        mth.set(5, 1);
        mth.set(11, 0);
        mth.set(12, 0);
        mth.set(13, 0);
        mth.set(14, 0);
        mth.set(9, 0);
        return new Timestamp(mth.getTime().getTime());
    }

    public static int weekNumber(Timestamp input) {
        return weekNumber(input, TimeZone.getDefault(), Locale.getDefault());
    }

    public static int dayNumber(Timestamp stamp) {
        Calendar tempCal = toCalendar(stamp, TimeZone.getDefault(), Locale.getDefault());
        return tempCal.get(7);
    }

    public static int weekNumber(Timestamp input, int startOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(startOfWeek);
        if (startOfWeek == 2) {
            calendar.setMinimalDaysInFirstWeek(4);
        } else if (startOfWeek == 1) {
            calendar.setMinimalDaysInFirstWeek(3);
        }

        calendar.setTime(new Date(input.getTime()));
        return calendar.get(3);
    }

    public static Calendar getCalendarInstance(TimeZone timeZone, Locale locale) {
        return Calendar.getInstance(TimeZone.getTimeZone(timeZone.getID()), locale);
    }

    public static Calendar toCalendar(Date date, TimeZone timeZone, Locale locale) {
        Calendar cal = getCalendarInstance(timeZone, locale);
        if (date != null) {
            cal.setTime(date);
        }

        return cal;
    }

    public static Timestamp adjustTimestamp(Timestamp stamp, int adjType, int adjQuantity, TimeZone timeZone, Locale locale) {
        Calendar tempCal = toCalendar(stamp, timeZone, locale);
        tempCal.add(adjType, adjQuantity);
        return new Timestamp(tempCal.getTimeInMillis());
    }

    public static Timestamp adjustTimestamp(Timestamp stamp, Integer adjType, Integer adjQuantity) {
        Calendar tempCal = toCalendar(stamp);
        tempCal.add(adjType, adjQuantity);
        return new Timestamp(tempCal.getTimeInMillis());
    }

    public static Timestamp getDayStart(Timestamp stamp, TimeZone timeZone, Locale locale) {
        return getDayStart(stamp, 0, timeZone, locale);
    }

    public static Timestamp getDayStart(Timestamp stamp, int daysLater, TimeZone timeZone, Locale locale) {
        Calendar tempCal = toCalendar(stamp, timeZone, locale);
        tempCal.set(tempCal.get(1), tempCal.get(2), tempCal.get(5), 0, 0, 0);
        tempCal.add(5, daysLater);
        Timestamp retStamp = new Timestamp(tempCal.getTimeInMillis());
        retStamp.setNanos(0);
        return retStamp;
    }

    public static Timestamp getDayEnd(Timestamp stamp, TimeZone timeZone, Locale locale) {
        return getDayEnd(stamp, 0L, timeZone, locale);
    }

    public static Timestamp getDayEnd(Timestamp stamp, Long daysLater, TimeZone timeZone, Locale locale) {
        Calendar tempCal = toCalendar(stamp, timeZone, locale);
        tempCal.set(tempCal.get(1), tempCal.get(2), tempCal.get(5), 23, 59, 59);
        tempCal.add(5, daysLater.intValue());
        Timestamp retStamp = new Timestamp(tempCal.getTimeInMillis());
        retStamp.setNanos(0);
        return retStamp;
    }

    public static Timestamp getWeekStart(Timestamp stamp, TimeZone timeZone, Locale locale) {
        return getWeekStart(stamp, 0, 0, timeZone, locale);
    }

    public static Timestamp getWeekStart(Timestamp stamp, int daysLater, TimeZone timeZone, Locale locale) {
        return getWeekStart(stamp, daysLater, 0, timeZone, locale);
    }

    public static Timestamp getWeekStart(Timestamp stamp, int daysLater, int weeksLater, TimeZone timeZone, Locale locale) {
        Calendar tempCal = toCalendar(stamp, timeZone, locale);
        tempCal.set(tempCal.get(1), tempCal.get(2), tempCal.get(5), 0, 0, 0);
        tempCal.add(5, daysLater);
        tempCal.set(7, tempCal.getFirstDayOfWeek());
        tempCal.add(4, weeksLater);
        Timestamp retStamp = new Timestamp(tempCal.getTimeInMillis());
        retStamp.setNanos(0);
        return retStamp;
    }

    public static Timestamp getWeekEnd(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Timestamp weekStart = getWeekStart(stamp, timeZone, locale);
        Calendar tempCal = toCalendar(weekStart, timeZone, locale);
        tempCal.add(5, 6);
        return getDayEnd(new Timestamp(tempCal.getTimeInMillis()), timeZone, locale);
    }

    public static Timestamp getMonthStart(Timestamp stamp, TimeZone timeZone, Locale locale) {
        return getMonthStart(stamp, 0, 0, timeZone, locale);
    }

    public static Timestamp getMonthStart(Timestamp stamp, int daysLater, TimeZone timeZone, Locale locale) {
        return getMonthStart(stamp, daysLater, 0, timeZone, locale);
    }

    public static Timestamp getMonthStart(Timestamp stamp, int daysLater, int monthsLater, TimeZone timeZone, Locale locale) {
        Calendar tempCal = toCalendar(stamp, timeZone, locale);
        tempCal.set(tempCal.get(1), tempCal.get(2), 1, 0, 0, 0);
        tempCal.add(2, monthsLater);
        tempCal.add(5, daysLater);
        Timestamp retStamp = new Timestamp(tempCal.getTimeInMillis());
        retStamp.setNanos(0);
        return retStamp;
    }

    public static Timestamp getMonthEnd(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar tempCal = toCalendar(stamp, timeZone, locale);
        tempCal.set(tempCal.get(1), tempCal.get(2), tempCal.getActualMaximum(5), 0, 0, 0);
        return getDayEnd(new Timestamp(tempCal.getTimeInMillis()), timeZone, locale);
    }

    public static Timestamp getYearStart(Timestamp stamp, TimeZone timeZone, Locale locale) {
        return getYearStart(stamp, 0, 0, 0, timeZone, locale);
    }

    public static Timestamp getYearStart(Timestamp stamp, int daysLater, TimeZone timeZone, Locale locale) {
        return getYearStart(stamp, daysLater, 0, 0, timeZone, locale);
    }

    public static Timestamp getYearStart(Timestamp stamp, int daysLater, int yearsLater, TimeZone timeZone, Locale locale) {
        return getYearStart(stamp, daysLater, 0, yearsLater, timeZone, locale);
    }

    public static Timestamp getYearStart(Timestamp stamp, Number daysLater, Number monthsLater, Number yearsLater, TimeZone timeZone, Locale locale) {
        return getYearStart(stamp, daysLater == null ? 0 : daysLater.intValue(), monthsLater == null ? 0 : monthsLater.intValue(), yearsLater == null ? 0 : yearsLater.intValue(), timeZone, locale);
    }

    public static Timestamp getYearStart(Timestamp stamp, int daysLater, int monthsLater, int yearsLater, TimeZone timeZone, Locale locale) {
        Calendar tempCal = toCalendar(stamp, timeZone, locale);
        tempCal.set(tempCal.get(1), 0, 1, 0, 0, 0);
        tempCal.add(1, yearsLater);
        tempCal.add(2, monthsLater);
        tempCal.add(5, daysLater);
        Timestamp retStamp = new Timestamp(tempCal.getTimeInMillis());
        retStamp.setNanos(0);
        return retStamp;
    }

    public static Timestamp getYearEnd(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar tempCal = toCalendar(stamp, timeZone, locale);
        tempCal.set(tempCal.get(1), tempCal.getActualMaximum(2) + 1, 0, 0, 0, 0);
        return getMonthEnd(new Timestamp(tempCal.getTimeInMillis()), timeZone, locale);
    }

    public static int weekNumber(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar tempCal = toCalendar(stamp, timeZone, locale);
        return tempCal.get(3);
    }

    public static List<String> getDayNames(Locale locale) {
        Calendar tempCal = Calendar.getInstance(locale);
        tempCal.set(7, tempCal.getFirstDayOfWeek());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", locale);
        List<String> resultList = new ArrayList();

        for(int i = 0; i < 7; ++i) {
            resultList.add(dateFormat.format(tempCal.getTime()));
            tempCal.roll(7, 1);
        }

        return resultList;
    }

    public static List<String> getMonthNames(Locale locale) {
        Calendar tempCal = Calendar.getInstance(locale);
        tempCal.set(2, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", locale);
        List<String> resultList = new ArrayList();

        for(int i = 0; i <= tempCal.getActualMaximum(2); ++i) {
            resultList.add(dateFormat.format(tempCal.getTime()));
            tempCal.roll(2, 1);
        }

        return resultList;
    }

    public static DateFormat toDateFormat(String dateFormat, TimeZone tz, Locale locale) {
        DateFormat df = null;
        if (UtilValidate.isEmpty(dateFormat)) {
            df = DateFormat.getDateInstance(3, locale);
        } else {
            df = new SimpleDateFormat(dateFormat, locale == null ? Locale.getDefault() : locale);
        }

        ((DateFormat)df).setTimeZone(tz);
        return (DateFormat)df;
    }

    public static DateFormat toDateTimeFormat(String dateTimeFormat, TimeZone tz, Locale locale) {
        DateFormat df = null;
        if (UtilValidate.isEmpty(dateTimeFormat)) {
            df = DateFormat.getDateTimeInstance(3, 2, locale);
        } else {
            df = new SimpleDateFormat(dateTimeFormat, locale == null ? Locale.getDefault() : locale);
        }

        ((DateFormat)df).setTimeZone(tz);
        return (DateFormat)df;
    }

    public static DateFormat toTimeFormat(String timeFormat, TimeZone tz, Locale locale) {
        DateFormat df = null;
        if (UtilValidate.isEmpty(timeFormat)) {
            df = DateFormat.getTimeInstance(2, locale);
        } else {
            df = new SimpleDateFormat(timeFormat, locale == null ? Locale.getDefault() : locale);
        }

        ((DateFormat)df).setTimeZone(tz);
        return (DateFormat)df;
    }

    public static Timestamp stringToTimeStamp(String dateTimeString, TimeZone tz, Locale locale) throws ParseException {
        return stringToTimeStamp(dateTimeString, (String)null, tz, locale);
    }

    public static Timestamp stringToTimeStamp(String dateTimeString, String dateTimeFormat, TimeZone tz, Locale locale) throws ParseException {
        DateFormat dateFormat = toDateTimeFormat(dateTimeFormat, tz, locale);
        Date parsedDate = dateFormat.parse(dateTimeString);
        return new Timestamp(parsedDate.getTime());
    }

    public static String timeStampToString(Timestamp stamp, TimeZone tz, Locale locale) {
        return timeStampToString(stamp, (String)null, tz, locale);
    }

    public static String timeStampToString(Timestamp stamp, String dateTimeFormat, TimeZone tz, Locale locale) {
        DateFormat dateFormat = toDateTimeFormat(dateTimeFormat, tz, locale);
        return dateFormat.format(stamp);
    }

    public static TimeZone toTimeZone(String tzId) {
        return UtilValidate.isEmpty(tzId) ? TimeZone.getDefault() : TimeZone.getTimeZone(tzId);
    }

    public static TimeZone toTimeZone(int gmtOffset) {
        if (gmtOffset <= 12 && gmtOffset >= -14) {
            String tzId = gmtOffset > 0 ? "Etc/GMT+" : "Etc/GMT";
            return TimeZone.getTimeZone(tzId + gmtOffset);
        } else {
            throw new IllegalArgumentException("Invalid GMT offset");
        }
    }

    public static int getSecond(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar cal = toCalendar(stamp, timeZone, locale);
        return cal.get(13);
    }

    public static int getMinute(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar cal = toCalendar(stamp, timeZone, locale);
        return cal.get(12);
    }

    public static int getHour(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar cal = toCalendar(stamp, timeZone, locale);
        return cal.get(11);
    }

    public static int getDayOfWeek(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar cal = toCalendar(stamp, timeZone, locale);
        return cal.get(7);
    }

    public static int getDayOfMonth(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar cal = toCalendar(stamp, timeZone, locale);
        return cal.get(5);
    }

    public static int getDayOfYear(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar cal = toCalendar(stamp, timeZone, locale);
        return cal.get(6);
    }

    public static int getWeek(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar cal = toCalendar(stamp, timeZone, locale);
        return cal.get(3);
    }

    public static int getMonth(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar cal = toCalendar(stamp, timeZone, locale);
        return cal.get(2);
    }

    public static int getYear(Timestamp stamp, TimeZone timeZone, Locale locale) {
        Calendar cal = toCalendar(stamp, timeZone, locale);
        return cal.get(1);
    }

    public static Date getEarliestDate() {
        Calendar cal = getCalendarInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        cal.set(1, cal.getActualMinimum(1));
        cal.set(2, cal.getActualMinimum(2));
        cal.set(5, 1);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getLatestDate() {
        Calendar cal = getCalendarInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        cal.set(1, cal.getActualMaximum(1));
        cal.set(2, cal.getActualMaximum(2));
        cal.set(5, cal.getActualMaximum(5));
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 999);
        return cal.getTime();
    }

    public static String getDateFormat(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }

        byte dateStyle;
        if ("yyyy-MM-dd" != null && !"DEFAULT".equals("yyyy-MM-dd") && !"SHORT".equals("yyyy-MM-dd")) {
            if ("MEDIUM".equals("yyyy-MM-dd")) {
                dateStyle = 2;
            } else {
                if (!"LONG".equals("yyyy-MM-dd")) {
                    return "yyyy-MM-dd";
                }

                dateStyle = 1;
            }
        } else {
            dateStyle = 3;
        }

        SimpleDateFormat df = (SimpleDateFormat)SimpleDateFormat.getDateInstance(dateStyle, locale);
        return df.toPattern();
    }

    public static String getDateTimeFormat(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }

        int dateStyle = -1;
        if ("yyyy-MM-dd" != null && !"DEFAULT".equals("yyyy-MM-dd") && !"SHORT".equals("yyyy-MM-dd")) {
            if ("MEDIUM".equals("yyyy-MM-dd")) {
                dateStyle = 2;
            } else if ("LONG".equals("yyyy-MM-dd")) {
                dateStyle = 1;
            }
        } else {
            dateStyle = 3;
        }

        int timeStyle = -1;
        if ("HH:mm:ss" != null && !"DEFAULT".equals("HH:mm:ss") && !"SHORT".equals("HH:mm:ss")) {
            if ("MEDIUM".equals("HH:mm:ss")) {
                timeStyle = 2;
            } else if ("LONG".equals("HH:mm:ss")) {
                timeStyle = 1;
            }
        } else {
            timeStyle = 3;
        }

        SimpleDateFormat df;
        if (dateStyle >= 0 && timeStyle >= 0) {
            df = (SimpleDateFormat)SimpleDateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
            return df.toPattern();
        } else if (dateStyle >= 0 && timeStyle == -1) {
            df = (SimpleDateFormat)SimpleDateFormat.getDateInstance(dateStyle, locale);
            return df.toPattern() + " " + "HH:mm:ss";
        } else if (dateStyle == -1 && timeStyle == -1) {
            return "yyyy-MM-dd HH:mm:ss";
        } else {
            df = (SimpleDateFormat)SimpleDateFormat.getTimeInstance(timeStyle, locale);
            return "yyyy-MM-dd " + df.toPattern();
        }
    }

    public static Date unmodifiableDate(Date date) {
        return (Date)(date instanceof UtilDateTime.ImmutableDate ? date : new UtilDateTime.ImmutableDate(date.getTime()));
    }

    public static String getDateFormat() {
        return "yyyy-MM-dd";
    }

    public static String getDateTimeFormat() {
        return "yyyy-MM-dd HH:mm:ss.SSS";
    }

    public static String getTimeFormat() {
        return "HH:mm:ss";
    }

    private static class ImmutableDate extends Date {
        private ImmutableDate(long date) {
            super(date);
        }

        public Object clone() {
            return this;
        }

        public void setTime(long time) {
            throw new UnsupportedOperationException();
        }
    }

}
