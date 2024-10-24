package vn.edu.ou.zalo.utils;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    public static String getTimeAgo(long timestamp) {
        long now = System.currentTimeMillis();

        // Calculate the difference in milliseconds
        long difference = now - timestamp;

        // If the time is less than 1 minute
        if (difference < DateUtils.MINUTE_IN_MILLIS) {
            String output = "1 minute";
            return output;
        }

        // If the time is less than 1 hour
        if (difference < DateUtils.HOUR_IN_MILLIS) {
            long minutes = difference / DateUtils.MINUTE_IN_MILLIS;
            String output = (minutes == 1) ? "1 minute" : (minutes + " minutes");
            return output;
        }

        // If the time is less than 1 day
        if (difference < DateUtils.DAY_IN_MILLIS) {
            long hours = difference / DateUtils.HOUR_IN_MILLIS;
            String output = (hours == 1) ? "1 hour" : (hours + " hours");
            return output;
        }

        // If it's within the current week, use abbreviated weekday name
        Calendar messageCalendar = Calendar.getInstance();
        messageCalendar.setTimeInMillis(timestamp);
        Calendar currentCalendar = Calendar.getInstance();

        if (currentCalendar.get(Calendar.WEEK_OF_YEAR) == messageCalendar.get(Calendar.WEEK_OF_YEAR)
                && currentCalendar.get(Calendar.YEAR) == messageCalendar.get(Calendar.YEAR)) {
            String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
            int dayOfWeek = messageCalendar.get(Calendar.DAY_OF_WEEK) - 1; // Adjusting for 0-based index
            String output = weekdays[dayOfWeek];
            return output;
        }

        // If it's within this year, show "dd/MM"
        int messageYear = messageCalendar.get(Calendar.YEAR);
        int currentYear = currentCalendar.get(Calendar.YEAR);

        SimpleDateFormat dateFormat;
        if (messageYear == currentYear) {
            dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        } else {
            // If it's from another year, show "dd/MM/yyyy"
            dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        }

        String output = dateFormat.format(new Date(timestamp));
        return output;
    }
}
