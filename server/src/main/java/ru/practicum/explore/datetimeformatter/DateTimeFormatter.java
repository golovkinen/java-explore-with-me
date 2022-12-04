package ru.practicum.explore.datetimeformatter;

import java.time.LocalDateTime;

public class DateTimeFormatter {

    static java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime toDateTime(String time) {
        return LocalDateTime.parse(time, formatter);
    }

    public static String dateTimeToString (LocalDateTime time) {
        return time.format(formatter);
    }
}
