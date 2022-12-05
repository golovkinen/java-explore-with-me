package ru.practicum.explore.event.service;

import org.springframework.http.HttpStatus;
import ru.practicum.explore.event.dto.*;
import ru.practicum.explore.event.enums.State;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.location.dto.LocationDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface IEventService {

    List<EventFullDto> getAllAdmin(List<Integer> ids, List<State> states, List<Integer> categories,
                                   String rangeFrom, String rangeEnd, int from, int size);

    EventFullDto updateEventAdmin(AdminUpdateEventRequestDto adminUpdateEventRequestDto, int eventId);

    List<EventShortDto> getAllPublic(String text, List<Integer> categories, Boolean paid, String rangeFrom, String rangeEnd,
                                     Boolean onlyAvailable, String sort, int from, int size, HttpServletRequest request);

    EventFullDto getEventByIdPublic(int eventId, HttpServletRequest request);

    List<EventShortDto> getAllUserEvents(int userId, int from, int size);

    EventFullDto getUserEventById(int userId, int eventId);

    EventFullDto updateEventPrivate(UpdateEventDto updateEventDto, int userId);

    EventFullDto createEventPrivate(NewEventDto newEventDto, int userId);

    EventFullDto cancelUserEventById(int userId, int eventId);

    Optional<Event> getEvent(int eventId);

    EventFullDto publishEventAdmin(int eventId);

    EventFullDto rejectEventAdmin(int eventId);

    List<Event> getEventsByIds(List<Integer> eventIds);

    List<EventShortDto> findAllEventsOrderedByDistance(LocationDto locationDto, int from, int size);

    List<EventShortDto> findAllEventsWithinRadius(LocationDto locationDto, double radius, int from, int size);

    HttpStatus addMark(int userId, int eventId, int mark);

    List<EventFullDto> getEventsBySubscriptions(int userId, List<Integer> ids, int from, int size);

    List<EventShortDto> findAllEventsByAddress(String city, String suburb, String road, String houseNumber, int from, int size);

    Event saveEvent(Event event);
}
