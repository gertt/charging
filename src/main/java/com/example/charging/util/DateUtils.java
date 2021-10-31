package com.example.charging.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.example.charging.util.AppConstants.DATE_FORMAT;

public class DateUtils {

    /**
     * This method calculates the difference between two date
     * in a specific format and returns the time in hours.
     */
    public static float diffDate(String timeStart, String timeStop) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date firstDate = sdf.parse(timeStart);
        Date secondDate = sdf.parse(timeStop);
        long diffInMilliSeconds = Math.abs(secondDate.getTime() - firstDate.getTime());
        return TimeUnit.MINUTES.convert(diffInMilliSeconds, TimeUnit.MILLISECONDS) / 60f;
    }
}
