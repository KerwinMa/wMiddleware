/*
 *  DateUtil.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.utils;

import com.witbooking.middleware.exceptions.DateFormatException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 15-mar-2013
 */
public class DateUtil {

    /**
     * Creates a new instance of
     * <code>DateUtil</code> without params.
     */
    public DateUtil() {
    }

    //getting the dates without the hours of the day
    public static Date toBeginningOfTheDay(Date date) {
        try {
            return stringToCalendarDate(calendarFormat(date));
        } catch (Exception ex) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.clear(Calendar.HOUR);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);
            return cal.getTime();
        }
    }

    public static void incrementDays(Date date, int days) {
        DateTime dtOrg = new DateTime(date);
        dtOrg = dtOrg.plusDays(days);
        date.setTime(dtOrg.toDate().getTime());
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.clear(Calendar.HOUR);
//        cal.clear(Calendar.MINUTE);
//        cal.clear(Calendar.SECOND);
//        cal.clear(Calendar.MILLISECOND);
//        cal.add(Calendar.DATE, days);
//        date.setTime(cal.getTime().getTime());
    }

    public static Date cloneAndIncrementDays(Date date, int days) {
        Date ret = new Date(date.getTime());
        incrementDays(ret, days);
        return ret;
    }

    public static boolean dateBetweenDaysRange(Date targetDate, Date startDate, Date endDate) {
        DateTime target = new DateTime(targetDate);
        return !target.isBefore(new DateTime(startDate)) && !target.isAfter(new DateTime(endDate));
    }

    public static int getDayOfWeek(Date targetDate) {
        return new DateTime(targetDate).getDayOfWeek();
    }

    public static int daysBetweenDates(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return Days.daysBetween(new DateTime(startDate), new DateTime(endDate)).getDays();
    }

    //TODO: this is created because the Notice are in Hours, this should change to Days
    public static int hoursBetweenDates(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return Hours.hoursBetween(new DateTime(startDate.getTime()), new DateTime(endDate.getTime())).getHours();
    }

    public static String calendarFormat(Date date) {
        if (date == null) {
            return null;
        } else {
            return getCalendarFormat().print(date.getTime());
        }
    }

    public static String timeFormat(Date date) {
        if (date == null) {
            return null;
        } else {
            return getTimeFormat().print(date.getTime());
        }
    }

    public static String timestampFormat(Date date) {
        if (date == null) {
            return null;
        } else {
            return getTimestampFormat().print(date.getTime());
        }
    }

    public static String isoDateTimeFormat(Date date) {
        if (date == null) {
            return null;
        } else {
            return ISODateTimeFormat.dateTimeNoMillis().withZoneUTC().print(date.getTime());
//            return ISODateTimeFormat.dateTimeNoMillis().print(new DateTime(date));
        }
    }

    public static Date stringToCalendarDate(String date) throws DateFormatException {
        if (date == null) {
            return null;
        } else {
            try {
                return getCalendarFormat().parseDateTime(date).toDate();
            } catch (Exception e) {
                throw new DateFormatException(e);
            }
        }
    }

    public static Date stringToTimestamp(String date) throws DateFormatException {
        if (date == null) {
            return null;
        } else {
            try {
                return getTimestampFormat().parseDateTime(date).toDate();
            } catch (Exception e) {
                throw new DateFormatException(e);
            }
        }
    }

    public static boolean isCalendarFormat(String date){
        return date.matches("((19|20)\\d\\d)-(0[1-9]|1[012])-([012][0-9]|3[01])");
    }

    public static boolean isTimestampFormat(String date){
        return date.matches("((19|20)\\d\\d)-(0[1-9]|1[012])-([012][0-9]|3[01]) (2[0-4]|[01][0-9]):([0-5][0-9]):([0-5][0-9])");
    }


    /**
     * Return A Date from a String with Date in ISO 8601, using the UTC TimeZone
     * yyyy-MM-ddTHH:mm:ssZ
     */
    public static Date stringToISODateTime(String date) throws ParseException {
        if (date == null) {
            return null;
        } else {
            //TODO: apply the TimeZone UTC to the Timestamps
            return ISODateTimeFormat.dateTimeNoMillis().withZoneUTC().parseDateTime(date).toDate();
//            return ISODateTimeFormat.dateTimeNoMillis().parseDateTime(date).toDate();
        }
    }

    public static Date stringToISODateTimeOrCalendarDate(String date) throws ParseException {
        if (date == null)
            return null;
        if (date.trim().length() == 10)
            date = date + "T00:00:00Z";
        return stringToISODateTime(date);
    }


    public static Date newUTCDate() {
        return new DateTime(DateTimeZone.UTC).toDate();
    }

    /**
     * This format is used for get the dates without the hours of the day
     */
    private static DateTimeFormatter getCalendarFormat() {
//                TODO: apply the TimeZone UTC to the Timestamps
//        return new SimpleDateFormat("yyyy-MM-dd");
        return DateTimeFormat.forPattern("yyyy-MM-dd");
    }

    private static DateTimeFormatter getTimestampFormat() {
//                TODO: apply the TimeZone UTC to the Times
//        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    }

    private static DateTimeFormatter getTimeFormat() {
//                TODO: apply the TimeZone UTC to the Times
        return DateTimeFormat.forPattern("HH:mm:ss");
    }

}