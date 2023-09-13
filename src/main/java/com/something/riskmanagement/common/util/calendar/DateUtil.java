package com.something.riskmanagement.common.util.calendar;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public class DateUtil {
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SHORT_DATE_FORMAT = "yyMMdd";

    private static final String PERSIAN_LOCALE = "fa_IR@persianCalendar=persian";
    private static final String PERSIAN_LOCALE_NUM_LATIN = "fa_IR@numbers=latn";
    private static final String EN_LOCALE = "en_us";

    public static PersianDate gregorianToPersian(Date date) {
        Calendar persianCalendar = Calendar.getInstance(new ULocale(PERSIAN_LOCALE));
        persianCalendar.setTime(date);
        PersianDate persianDate = new PersianDate();
        persianDate.setYear(persianCalendar.get(Calendar.YEAR));
        persianDate.setMonth(persianCalendar.get(Calendar.MONTH));
        persianDate.setDay(persianCalendar.get(Calendar.DAY_OF_MONTH));
        persianDate.setHour(persianCalendar.get(Calendar.HOUR));
        persianDate.setMinute(persianCalendar.get(Calendar.MINUTE));
        persianDate.setSecond(persianCalendar.get(Calendar.SECOND));
        return persianDate;
    }

    public static Date persianToGregorian(PersianDate date) {
        Calendar persianCalendar = Calendar.getInstance(new ULocale(PERSIAN_LOCALE));
        persianCalendar.set(date.getYear(), date.getMonth(), date.getDay());
        return persianCalendar.getTime();
    }

    public static String gregorianToPersian(Date gregorianDate, String pattern) {
        ULocale persianLocale = new ULocale(PERSIAN_LOCALE);
        DateFormat df = new SimpleDateFormat(pattern, persianLocale);
        return df.format(gregorianDate);
    }

    public static String gregorianToPersianLatinNumber(Date gregorianDate, String pattern) {
        ULocale persianLocale = new ULocale(PERSIAN_LOCALE_NUM_LATIN);
        DateFormat df = new SimpleDateFormat(pattern, persianLocale);
        return df.format(gregorianDate);
    }

    public static boolean isFirstDayOfPersianWeek(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return cal.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.SATURDAY;
    }

    public static boolean isFirstDayOfPersianMonth(Date date) {
        PersianDate persianDate = gregorianToPersian(date);
        return persianDate.getDay() == 1;
    }

    public static boolean isFirstDayOfPersianYear(Date date) {
        PersianDate persianDate = gregorianToPersian(date);
        return persianDate.getDay() == 1 && persianDate.getMonth() == 0;
    }

    public static Date addMonths(Date date, Integer months) {
        Calendar calendar = Calendar.getInstance(new ULocale(EN_LOCALE));
//        calendar.setLenient(false);
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    public static Date addDays(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance(new ULocale(EN_LOCALE));
//        calendar.setLenient(false);
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public static String gregorianToString(Date gregorianDate, String pattern) {
        ULocale enLocale = new ULocale(EN_LOCALE);
        DateFormat df = new SimpleDateFormat(pattern, enLocale);
        return df.format(gregorianDate);
    }

    public static Date stringToGregorian(String str, String pattern) {
        ULocale enLocale = new ULocale(EN_LOCALE);
        DateFormat df = new SimpleDateFormat(pattern, enLocale);
        Date date = null;
        try {
            date = df.parse(str);
        } catch (ParseException ignored) {
        }
        return date;
    }

    public static Date persianStringToGregorianDate(String str) {
        if (str.matches("[0-9]{4}(-|/)[0-9]{2}(-|/)[0-9]{2}")) {
            String[] s = str.split("-|/");

            Calendar persianCalendar = Calendar.getInstance(new ULocale(PERSIAN_LOCALE));
            persianCalendar.set(Integer.valueOf(s[0]), Integer.valueOf(s[1]) - 1, Integer.valueOf(s[2]));
            persianCalendar.set(Calendar.MILLISECONDS_IN_DAY, 0);
            return persianCalendar.getTime();
        }
        return null;
    }

    public static Date persianStringToGregorianTimestamp(String str) {
        if (str.matches("[0-9]{4}(-|/)[0-9]{2}(-|/)[0-9]{2} [0-9]{2}(-|:)[0-9]{2}(-|:)[0-9]{2}")) {
            String[] s = str.split("-|/|:| ");

            Calendar persianCalendar = Calendar.getInstance(new ULocale(PERSIAN_LOCALE));
            persianCalendar.set(Integer.valueOf(s[0]), Integer.valueOf(s[1]) - 1, Integer.valueOf(s[2]), Integer.valueOf(s[3]), Integer.valueOf(s[4]), Integer.valueOf(s[5]));
            return persianCalendar.getTime();
        }
        return null;
    }

    public static Date resetTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECONDS_IN_DAY, 0);
        return cal.getTime();
    }
}
