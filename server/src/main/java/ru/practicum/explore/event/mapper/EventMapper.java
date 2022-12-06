package ru.practicum.explore.event.mapper;

import ru.practicum.explore.category.mapper.CategoryMapper;
import ru.practicum.explore.category.model.Category;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventShortDto;
import ru.practicum.explore.event.dto.HitDto;
import ru.practicum.explore.event.dto.NewEventDto;
import ru.practicum.explore.event.enums.State;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.location.mapper.LocationMapper;
import ru.practicum.explore.location.model.Location;
import ru.practicum.explore.user.mapper.UserMapper;
import ru.practicum.explore.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class EventMapper {


    public static Event toEvent(NewEventDto newEventDto, User user, Category category, Location location) {
        return new Event(null, newEventDto.getAnnotation(), newEventDto.getDescription(), newEventDto.getTitle(),
                toDateTime(newEventDto.getEventDate()), newEventDto.getPaid(), true, null, null,
                newEventDto.getParticipantLimit(), newEventDto.getRequestModeration(), State.PENDING,
                0, new HashSet<>(), new HashSet<>(), user, category, null, location, null);
    }

    public static EventFullDto toEventFullDto(Event event) {

        return new EventFullDto(event.getId(), event.getAnnotation(), event.getTitle(), event.getDescription(), dateTimeToString(event.getEventDate()),
                CategoryMapper.toCategoryDto(event.getCategory()), UserMapper.toUserShortDto(event.getUser()),
                LocationMapper.toLocationDto(event.getLocation()), event.getPaid(), dateTimeToString(event.getCreatedOn()),
                event.getParticipantLimit(), dateTimeToString(event.getPublishedOn()), event.getRequestModeration(),
                event.getState().toString(), event.getViews(), event.getRequests().size());
    }

    public static EventShortDto toEventShortDto(Event event) {

        return new EventShortDto(event.getId(), event.getAnnotation(), event.getTitle(), dateTimeToString(event.getEventDate()),
                CategoryMapper.toCategoryDto(event.getCategory()), UserMapper.toUserShortDto(event.getUser()),
                event.getPaid(), event.getViews(), event.getRequests().size());
    }

    public static HitDto toHitDto(String api, String uri, String ip, LocalDateTime localDateTime) {

        return new HitDto(api, uri, ip, dateTimeToString(localDateTime));
    }

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime toDateTime(String time) {
        return LocalDateTime.parse(time, formatter);
    }

    public static String dateTimeToString(LocalDateTime time) {
        if (time == null) {
            return null;
        }

        return time.format(formatter);
    }

}
