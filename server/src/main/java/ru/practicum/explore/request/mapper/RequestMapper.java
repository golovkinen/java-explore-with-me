package ru.practicum.explore.request.mapper;

import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.request.dto.RequestInfoDto;
import ru.practicum.explore.request.enums.Status;
import ru.practicum.explore.request.model.Request;
import ru.practicum.explore.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestMapper {
    public static Request createRequest(User requester, Event event) {
        return new Request(null, null, Status.PENDING,
                event, requester);
    }

    public static RequestInfoDto toRequestInfoDto(Request request) {

        return new RequestInfoDto(request.getId(), dateTimeToString(request.getCreatedOn()), request.getStatus().toString(),
                request.getEvent().getId(), request.getUser().getId());
    }

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime toDateTime(String time) {
        return LocalDateTime.parse(time, formatter);
    }

    public static String dateTimeToString (LocalDateTime time) {
        if(time == null) {
            return null;
        }

        return time.format(formatter);
    }
}
