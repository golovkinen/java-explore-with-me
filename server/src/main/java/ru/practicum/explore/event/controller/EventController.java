package ru.practicum.explore.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.dto.*;
import ru.practicum.explore.event.enums.State;
import ru.practicum.explore.event.service.IEventService;
import ru.practicum.explore.location.dto.LocationDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Validated
public class EventController {
    private final IEventService iEventService;

    public EventController(IEventService iEventService) {
        this.iEventService = iEventService;
    }

    @GetMapping(value = "/events")
    public List<EventShortDto> getAllPublic (@RequestParam(name = "text", required = false) String text,
                                             @RequestParam(name = "categories", required = false) Optional<List<Integer>> categories,
                                             @RequestParam(name = "paid", required = false) Boolean paid,
                                             @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                             @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                             @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                             @RequestParam(name = "sort", required = false) String sort,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") int size,
                                             HttpServletRequest request) {
        return iEventService.getAllPublic(text, categories.orElse(Collections.emptyList()), paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping(value = "/events/{eventId}")
    public EventFullDto getEventByIdPublic(@Positive @PathVariable(name = "eventId") int eventId,
                                           HttpServletRequest request) {
        return iEventService.getEventByIdPublic(eventId, request);
    }

    @GetMapping(value = "/users/{userId}/events")
    public List<EventShortDto> getAllUserEvents (@Positive @PathVariable(name = "userId") int userId,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") int size) {
        return iEventService.getAllUserEvents(userId, from, size);
    }

    @GetMapping(value = "/users/{userId}/events/{eventId}")
    public EventFullDto getUserEventById (@Positive @PathVariable(name = "userId") int userId,
                                          @Positive @PathVariable(name = "eventId") int eventId) {
        return iEventService.getUserEventById(userId, eventId);
    }

    @PatchMapping(value = "/users/{userId}/events")
    public EventFullDto updateEventPrivate(@Positive @PathVariable(name = "userId") int userId,
                                           @RequestBody UpdateEventDto updateEventDto) {
        return iEventService.updateEventPrivate(updateEventDto, userId);
    }

    @PostMapping(value = "/users/{userId}/events")
    public EventFullDto createEventPrivate(@Validated @RequestBody NewEventDto newEventDto,
                                           @Positive @PathVariable(name = "userId") int userId
                                           ) {
        return iEventService.createEventPrivate(newEventDto, userId);
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}")
    public EventFullDto cancelUserEventById (@Positive @PathVariable(name = "userId") int userId,
                                             @Positive @PathVariable(name = "eventId") int eventId) {
        return iEventService.cancelUserEventById(userId, eventId);
    }

    @GetMapping(value = "/admin/events")
    public List<EventFullDto> getAllAdmin(@RequestParam(name = "users", required = false) Optional<List<Integer>> ids,
                                           @RequestParam(name = "states", required = false) Optional<List<State>> states,
                                           @RequestParam(name = "categories", required = false) Optional<List<Integer>> categories,
                                           @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                           @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                           @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") int size) {
        return iEventService.getAllAdmin(ids.orElse(Collections.emptyList()), states.orElse(Collections.emptyList()),
                categories.orElse(Collections.emptyList()), rangeStart, rangeEnd, from, size);
    }

    @PutMapping(value = "/admin/events/{eventId}")
    public EventFullDto updateEventAdmin(
            @RequestBody AdminUpdateEventRequestDto adminUpdateEventRequestDto,
            @Positive @PathVariable int eventId) {

        return iEventService.updateEventAdmin(adminUpdateEventRequestDto, eventId);
    }

    @PatchMapping(value = "/admin/events/{eventId}/publish")
    public EventFullDto publishEventAdmin(@Positive @PathVariable int eventId) {

        return iEventService.publishEventAdmin(eventId);
    }

    @PatchMapping(value = "/admin/events/{eventId}/reject")
    public EventFullDto rejectEventAdmin(@Positive @PathVariable int eventId) {

        return iEventService.rejectEventAdmin(eventId);
    }

    @PostMapping(value = "/users/{userId}/events/{eventId}")
    public HttpStatus addMark(@Positive @PathVariable(name = "userId") int userId,
                                            @Positive @PathVariable(name = "eventId") int eventId,
                                            @Min(value = 0) @Max(value = 10) @RequestParam(name = "mark") int mark) {
        return iEventService.addMark(userId, eventId, mark);
    }

    @GetMapping(value = "/events/location/distance")
    public List<EventShortDto> findAllEventsOrderedByDistance(@RequestBody LocationDto locationDto,
                                                              @RequestParam(name = "from", defaultValue = "0") int from,
                                                              @RequestParam(name = "size", defaultValue = "10") int size) {
        return iEventService.findAllEventsOrderedByDistance(locationDto, from, size);
    }

    @GetMapping(value = "/events/location")
    public List<EventShortDto> findAllEventsWithinRadius(@RequestBody LocationDto locationDto,
                                                         @Positive @RequestParam(name = "radius", defaultValue = "500") double radius,
                                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                                         @Positive @RequestParam(name = "size", defaultValue = "10") int size) {
        return iEventService.findAllEventsWithinRadius(locationDto, radius, from, size);
    }

    @GetMapping(value = "/events/location/address")
    public List<EventShortDto> findAllEventsByAddress(@RequestParam(name = "city") String city,
                                                      @RequestParam(name = "suburb", required = false) String suburb,
                                                      @RequestParam(name = "road", required = false) String road,
                                                      @RequestParam(name = "houseNumber", required = false) String houseNumber,
                                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                                      @Positive @RequestParam(name = "size", defaultValue = "10") int size) {
        return iEventService.findAllEventsByAddress(city, suburb, road, houseNumber, from, size);
    }

    @GetMapping(value = "/user/{userId}/events/subscription")
    public List<EventFullDto> getEventsBySubscriptions (@Positive @PathVariable(name = "userId") int userId,
                                                        @Positive @RequestParam(name = "ids", required = false) Optional<List<Integer>> ids,
                                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                                        @Positive @RequestParam(name = "size", defaultValue = "10") int size) {

        return iEventService.getEventsBySubscriptions(userId, ids.orElse(Collections.emptyList()), from, size);
    }

}
