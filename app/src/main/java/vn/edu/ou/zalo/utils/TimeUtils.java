package vn.edu.ou.zalo.utils;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    public static String getDetailedTimeAgo(long timestamp) {
        long now = System.currentTimeMillis();
        long difference = now - timestamp;

        if (difference < DateUtils.MINUTE_IN_MILLIS) {
            return "Just now";
        } else if (difference < DateUtils.HOUR_IN_MILLIS) {
            long minutes = difference / DateUtils.MINUTE_IN_MILLIS;
            return (minutes == 1) ? "1 minute ago" : minutes + " minutes ago";
        } else if (difference < DateUtils.DAY_IN_MILLIS) {
            long hours = difference / DateUtils.HOUR_IN_MILLIS;
            return (hours == 1) ? "1 hour ago" : hours + " hours ago";
        }

        Calendar messageCalendar = Calendar.getInstance();
        messageCalendar.setTimeInMillis(timestamp);
        Calendar currentCalendar = Calendar.getInstance();

        if (difference < 2 * DateUtils.DAY_IN_MILLIS) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return "Yesterday at " + timeFormat.format(new Date(timestamp));
        } else if (currentCalendar.get(Calendar.WEEK_OF_YEAR) == messageCalendar.get(Calendar.WEEK_OF_YEAR)
                && currentCalendar.get(Calendar.YEAR) == messageCalendar.get(Calendar.YEAR)) {
            SimpleDateFormat weekdayFormat = new SimpleDateFormat("EEEE 'at' HH:mm", Locale.getDefault());
            return weekdayFormat.format(new Date(timestamp));
        } else if (currentCalendar.get(Calendar.YEAR) == messageCalendar.get(Calendar.YEAR)) {
            SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMM d 'at' HH:mm", Locale.getDefault());
            return monthDayFormat.format(new Date(timestamp));
        } else {
            SimpleDateFormat fullDateFormat = new SimpleDateFormat("MMM d yyyy", Locale.getDefault());
            return fullDateFormat.format(new Date(timestamp));
        }
    }

    public static String getShortTimeAgo(long timestamp) {
        long now = System.currentTimeMillis();

        // Calculate the difference in milliseconds
        long difference = now - timestamp;

        // If the time is less than 1 minute
        if (difference < DateUtils.MINUTE_IN_MILLIS) {
            return "1 minute";
        }

        // If the time is less than 1 hour
        if (difference < DateUtils.HOUR_IN_MILLIS) {
            long minutes = difference / DateUtils.MINUTE_IN_MILLIS;
            return (minutes == 1) ? "1 minute" : (minutes + " minutes");
        }

        // If the time is less than 1 day
        if (difference < DateUtils.DAY_IN_MILLIS) {
            long hours = difference / DateUtils.HOUR_IN_MILLIS;
            return (hours == 1) ? "1 hour" : (hours + " hours");
        }

        // If it's within the current week, use abbreviated weekday name
        Calendar messageCalendar = Calendar.getInstance();
        messageCalendar.setTimeInMillis(timestamp);
        Calendar currentCalendar = Calendar.getInstance();

        if (currentCalendar.get(Calendar.WEEK_OF_YEAR) == messageCalendar.get(Calendar.WEEK_OF_YEAR)
                && currentCalendar.get(Calendar.YEAR) == messageCalendar.get(Calendar.YEAR)) {
            String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
            int dayOfWeek = messageCalendar.get(Calendar.DAY_OF_WEEK) - 1; // Adjusting for 0-based index
            return weekdays[dayOfWeek];
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

        return dateFormat.format(new Date(timestamp));
    }

    public static String getHourMinute(long timestamp) {
        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();
        // Set the time using the provided timestamp
        calendar.setTimeInMillis(timestamp);

        // Create a SimpleDateFormat instance to format the time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

        // Return the formatted time
        return sdf.format(calendar.getTime());
    }
}
