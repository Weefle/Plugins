package com.icroque.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rémi on 16/01/2016.
 */
public class DateUtils {

    public static String getHour(Date date) {
        return new SimpleDateFormat("H").format(date);
    }

    public static String getMinute(Date date) {
        return new SimpleDateFormat("mm").format(date);
    }

    public static String getDay(Date date) {
        return new SimpleDateFormat("dd").format(date);
    }

    public static String getMonth(Date date) {
        return new SimpleDateFormat("MM").format(date);
    }

    public static String getYear(Date date) {
        return new SimpleDateFormat("yyyy").format(date);
    }

    public static Calendar getCalendar(long time) {
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        calendar.setTime(new Date(time));
        return calendar;
    }
}
