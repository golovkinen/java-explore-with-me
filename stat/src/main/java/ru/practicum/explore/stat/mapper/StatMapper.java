package ru.practicum.explore.stat.mapper;


import ru.practicum.explore.stat.dto.HitDto;
import ru.practicum.explore.stat.dto.ViewStatDto;
import ru.practicum.explore.stat.model.Stat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatMapper {

 //   static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

// Custom format if needed
 static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Stat toStat(HitDto hitDto) {
        return new Stat(null, hitDto.getApp(), hitDto.getUri(), hitDto.getIp(), LocalDateTime.parse(hitDto.getTimestamp(), formatter));
    }

    public static ViewStatDto toViewStatDto(String app, String uri, Integer hits) {
        return new ViewStatDto(app, uri, hits);
    }

    public static LocalDateTime toDateTime(String time) {
        return LocalDateTime.parse(time, formatter);
    }

    public static String dateTimeToString (LocalDateTime time) {
        return time.format(formatter);
    }
}
