package ru.practicum.explore.event.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.explore.category.model.Category;
import ru.practicum.explore.category.service.ICategoryService;
import ru.practicum.explore.event.client.EventClient;
import ru.practicum.explore.event.dto.*;
import ru.practicum.explore.event.enums.State;
import ru.practicum.explore.event.mapper.EventMapper;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.model.Mark;
import ru.practicum.explore.event.model.MarkKey;
import ru.practicum.explore.event.repository.IEventRepository;
import ru.practicum.explore.event.repository.IMarkRepository;
import ru.practicum.explore.exceptionhandler.BadRequestException;
import ru.practicum.explore.exceptionhandler.ForbiddenException;
import ru.practicum.explore.exceptionhandler.NotFoundException;
import ru.practicum.explore.exceptionhandler.WrongStateException;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.LocationInfoDto;
import ru.practicum.explore.location.model.Location;
import ru.practicum.explore.location.service.ILocationService;
import ru.practicum.explore.user.model.User;
import ru.practicum.explore.user.repository.IUserRepository;
import ru.practicum.explore.user.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventService implements IEventService {

    private final IEventRepository iEventRepository;
    private final IUserService iUserService;
    private final IUserRepository iUserRepository;
    private final ILocationService iLocationService;
    private final ICategoryService iCategoryService;
    private final IMarkRepository iMarkRepository;

    private final EventClient eventClient;

    public EventService(IEventRepository iEventRepository, IUserService iUserService, IUserRepository iUserRepository, ILocationService iLocationService, ICategoryService iCategoryService, IMarkRepository iMarkRepository, EventClient eventClient) {
        this.iEventRepository = iEventRepository;
        this.iUserService = iUserService;
        this.iUserRepository = iUserRepository;
        this.iLocationService = iLocationService;
        this.iCategoryService = iCategoryService;
        this.iMarkRepository = iMarkRepository;
        this.eventClient = eventClient;
    }

    @Override
    public List<EventFullDto> getAllAdmin(List<Integer> userIds, List<State> states, List<Integer> categories,
                                          String rangeFrom, String rangeEnd,
                                          int from, int size) {

        Pageable pageable = PageRequest.of(from / size, size);

        if (!states.isEmpty()) {
            if (states.stream().filter(f -> Arrays.stream(State.class.getEnumConstants()).anyMatch(e -> e.name().equals(f.toString())))
                    .count() != states.size()) {
                log.error("WrongStateException: {}", "getAllAdmin - Unknown state: UNSUPPORTED_STATUS");
                throw new WrongStateException("Unknown state: UNSUPPORTED_STATUS");
            }
        }

        List<Event> events = Collections.emptyList();

        //  List<State> stateEnums = states.stream().map(f -> State.valueOf(f)).collect(Collectors.toList());

        //нет параметров
        if (userIds.isEmpty() && states.isEmpty() && categories.isEmpty() &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAll(pageable).getContent();
        }

        //все мероприятия не позже даты
        if (userIds.isEmpty() && states.isEmpty() && categories.isEmpty() &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByEventDateBefore(EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия начиная с даты
        if (userIds.isEmpty() && states.isEmpty() && categories.isEmpty() &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByEventDateAfter(EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия в промежутке
        if (userIds.isEmpty() && states.isEmpty() && categories.isEmpty() &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByEventDateBetween(EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия с категориями
        if (userIds.isEmpty() && states.isEmpty() && !categories.isEmpty() &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByCategoryIdIn(categories, pageable).getContent();
        }

        //все мероприятия с категориями не позже даты
        if (userIds.isEmpty() && states.isEmpty() && !categories.isEmpty() &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByCategoryIdInAndEventDateBefore(categories, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия с категориями начиная с даты
        if (userIds.isEmpty() && states.isEmpty() && !categories.isEmpty() &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByCategoryIdInAndEventDateAfter(categories, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия с категориями в промежутке
        if (userIds.isEmpty() && states.isEmpty() && !categories.isEmpty() &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByCategoryIdInAndEventDateBetween(categories, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия со статусами
        if (userIds.isEmpty() && !states.isEmpty() && categories.isEmpty() &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIn(states, pageable).getContent();
        }

        //все мероприятия со статусами и с категориями
        if (userIds.isEmpty() && !states.isEmpty() && (!categories.isEmpty()) &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateInAndCategoryIdIn(states, categories, pageable).getContent();
        }

        //все мероприятия со статусами не позже даты
        if (userIds.isEmpty() && !states.isEmpty() && categories.isEmpty() &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateInAndEventDateBefore(states, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия со статусами начиная с даты
        if (userIds.isEmpty() && !states.isEmpty() && categories.isEmpty() &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateInAndEventDateAfter(states, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия со статусами в промежутке
        if (userIds.isEmpty() && !states.isEmpty() && categories.isEmpty() &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateInAndEventDateBetween(states, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия со статусами и с категориями не позже даты
        if (userIds.isEmpty() && !states.isEmpty() && (!categories.isEmpty()) &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateInAndCategoryIdInAndEventDateBefore(states, categories, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия со статусами и с категориями начиная с даты
        if (userIds.isEmpty() && !states.isEmpty() && (!categories.isEmpty()) &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateInAndCategoryIdInAndEventDateAfter(states, categories, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия со статусами и с категориями в промежутке
        if (userIds.isEmpty() && !states.isEmpty() && (!categories.isEmpty()) &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateInAndCategoryIdInAndEventDateBetween(states, categories, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия поиск по пользователю
        if (!userIds.isEmpty() && states.isEmpty() && categories.isEmpty() &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByUserIdIn(userIds, pageable).getContent();
        }

        //все мероприятия поиск по пользователю не позже даты
        if (!userIds.isEmpty() && states.isEmpty() && categories.isEmpty() &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByUserIdInAndEventDateBefore(userIds, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия поиск по пользователю начиная с даты
        if (!userIds.isEmpty() && !states.isEmpty() && categories.isEmpty() &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByUserIdInAndEventDateAfter(userIds, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия поиск по пользователю в промежутке
        if (!userIds.isEmpty() && !states.isEmpty() && categories.isEmpty() &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByUserIdInAndEventDateBetween(userIds, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия поиск по пользователю и статусу
        if (!userIds.isEmpty() && !states.isEmpty() && categories.isEmpty() &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByUserIdInAndStateIn(userIds, states, pageable).getContent();
        }

        //все мероприятия поиск по пользователю и статусу не позже даты
        if (!userIds.isEmpty() && !states.isEmpty() && categories.isEmpty() &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByUserIdInAndStateInAndEventDateBefore(userIds, states, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия поиск по пользователю и статусу начиная с даты
        if (!userIds.isEmpty() && !states.isEmpty() && categories.isEmpty() &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByUserIdInAndStateInAndEventDateAfter(userIds, states, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия поиск по пользователю и статусу в промежутке
        if (!userIds.isEmpty() && !states.isEmpty() && categories.isEmpty() &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByUserIdInAndStateInAndEventDateBetween(userIds, states, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия поиск по пользователю и с категориями
        if (!userIds.isEmpty() && states.isEmpty() && !categories.isEmpty() &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByUserIdInAndCategoryIdIn(userIds, categories, pageable).getContent();
        }

        //все мероприятия поиск по пользователю и с категориями не позже даты
        if (!userIds.isEmpty() && states.isEmpty() && !categories.isEmpty() &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByUserIdInAndCategoryIdInAndEventDateBefore(userIds, categories, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия поиск по пользователю и с категориями начиная с даты
        if (!userIds.isEmpty() && states.isEmpty() && !categories.isEmpty() &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByUserIdInAndCategoryIdInAndEventDateAfter(userIds, categories, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия поиск по пользователю и с категориями в промежутке
        if (!userIds.isEmpty() && states.isEmpty() && !categories.isEmpty() &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByUserIdInAndCategoryIdInAndEventDateBetween(userIds, categories, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия поиск по пользователю и статусу и с категориями
        if (!userIds.isEmpty() && !states.isEmpty() && !categories.isEmpty() &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByUserIdInAndStateInAndCategoryIdIn(userIds, states, categories, pageable).getContent();
        }

        //все мероприятия поиск по пользователю и статусу и с категориями не позже даты
        if (!userIds.isEmpty() && !states.isEmpty() && !categories.isEmpty() &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByUserIdInAndStateInAndCategoryIdInAndEventDateBefore(userIds, states, categories, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия поиск по пользователю и статусу и с категориями начиная с даты
        if (!userIds.isEmpty() && !states.isEmpty() && !categories.isEmpty() &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByUserIdInAndStateInAndCategoryIdInAndEventDateAfter(userIds, states, categories, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия поиск по пользователю и статусу и с категориями в промежутке
        if (!userIds.isEmpty() && !states.isEmpty() && !categories.isEmpty() &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByUserIdInAndStateInAndCategoryIdInAndEventDateBetween(userIds, states, categories, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        return events.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventAdmin(AdminUpdateEventRequestDto adminUpdateEventRequestDto, int eventId) {

        Optional<Event> eventToUpdate = iEventRepository.findById(eventId);

        if (eventToUpdate.isEmpty()) {
            log.error("NotFoundException: {}", "При обновлении Событие с ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие с ИД " + eventId + " не найдено");
        }

        if (LocalDateTime.now().plusHours(2).isAfter(EventMapper.toDateTime(adminUpdateEventRequestDto.getEventDate()))) {
            log.error("ForbiddenException: {}", "дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента");
            throw new ForbiddenException("Event can't be less than 2 hours from now");
        }

        if (adminUpdateEventRequestDto.getAnnotation() != null) {
            eventToUpdate.get().setAnnotation(adminUpdateEventRequestDto.getAnnotation());
        }
        if (adminUpdateEventRequestDto.getDescription() != null) {
            eventToUpdate.get().setDescription(adminUpdateEventRequestDto.getDescription());
        }
        if (adminUpdateEventRequestDto.getTitle() != null) {
            eventToUpdate.get().setTitle(adminUpdateEventRequestDto.getTitle());
        }
        if (adminUpdateEventRequestDto.getEventDate() != null) {
            eventToUpdate.get().setEventDate(EventMapper.toDateTime(adminUpdateEventRequestDto.getEventDate()));
        }
        if (adminUpdateEventRequestDto.getCategory() != null) {
            eventToUpdate.get().setCategory(iCategoryService.getCategory(adminUpdateEventRequestDto.getCategory()).get());
        }
        if (adminUpdateEventRequestDto.getPaid() != null) {
            eventToUpdate.get().setPaid(adminUpdateEventRequestDto.getPaid());
        }
        if (adminUpdateEventRequestDto.getParticipantLimit() != null) {
            eventToUpdate.get().setParticipantLimit(adminUpdateEventRequestDto.getParticipantLimit());
        }
        if (adminUpdateEventRequestDto.getRequestModeration() != null) {
            eventToUpdate.get().setRequestModeration(adminUpdateEventRequestDto.getRequestModeration());
        }

        if (adminUpdateEventRequestDto.getLocation() != null) {

            iLocationService.updateEventLocation(adminUpdateEventRequestDto.getLocation(),
                    eventToUpdate.get().getLocation().getId(), adminUpdateEventRequestDto.getTitle());

        }

        eventToUpdate.get().setState(State.PENDING);

        return EventMapper.toEventFullDto(iEventRepository.save(eventToUpdate.get()));

    }

    @Override
    public EventFullDto publishEventAdmin(int eventId) {

        Optional<Event> eventToPublish = iEventRepository.findById(eventId);

        if (eventToPublish.isEmpty()) {
            log.error("NotFoundException: {}", "При обновлении Событие с ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие с ИД " + eventId + " не найдено");
        }

        if (!eventToPublish.get().getState().equals(State.PENDING)) {
            log.error("ForbiddenException: {}", "Публиковать можно только в статусе PENDING");
            throw new ForbiddenException("Only pending events can be published");
        }

        if (LocalDateTime.now().plusHours(1).isAfter(eventToPublish.get().getEventDate())) {
            log.error("ForbiddenException: {}", "Event can't be less than 1 hours from now");
            throw new ForbiddenException("Event can't be less than 1 hours from now");
        }

        eventToPublish.get().setState(State.PUBLISHED);
        eventToPublish.get().setPublishedOn(LocalDateTime.now());

        return EventMapper.toEventFullDto(iEventRepository.save(eventToPublish.get()));
    }

    @Override
    public EventFullDto rejectEventAdmin(int eventId) {
        Optional<Event> eventToReject = iEventRepository.findById(eventId);

        if (eventToReject.isEmpty()) {
            log.error("NotFoundException: {}", "При обновлении Событие с ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие с ИД " + eventId + " не найдено");
        }

        if (!eventToReject.get().getState().equals(State.PENDING)) {
            log.error("ForbiddenException: {}", "Публиковать можно только в статусе PENDING");
            throw new ForbiddenException("Only pending events can be published");
        }

        eventToReject.get().setState(State.CANCELED);

        return EventMapper.toEventFullDto(iEventRepository.save(eventToReject.get()));
    }

    @Override
    public List<EventShortDto> getAllPublic(String text, List<Integer> categories, Boolean paid, String rangeFrom, String rangeEnd,
                                            Boolean onlyAvailable, String sort, int from, int size, HttpServletRequest request) {

        LocalDateTime timestamp = LocalDateTime.now();

        Pageable pageable = PageRequest.of(from / size, size);

        List<Event> events = new ArrayList<>();

        //  List<State> stateEnums = states.stream().map(f -> State.valueOf(f)).collect(Collectors.toList());

        //нет параметров
        if ((text == null || text.isBlank()) && onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIs(State.PUBLISHED, onlyAvailable, pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIs(State.PUBLISHED, pageable).getContent();
        }

        //все мероприятия не позже даты
        if ((text == null || text.isBlank()) && onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndEventDateBefore(State.PUBLISHED, onlyAvailable, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndEventDateBefore(State.PUBLISHED, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия начиная с даты
        if ((text == null || text.isBlank()) && onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndEventDateAfter(State.PUBLISHED, onlyAvailable, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndEventDateAfter(State.PUBLISHED, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия в промежутке
        if ((text == null || text.isBlank()) && onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndEventDateBetween(State.PUBLISHED, onlyAvailable, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndEventDateBetween(State.PUBLISHED, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия с категориями
        if ((text == null || text.isBlank()) && onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdIn(State.PUBLISHED, onlyAvailable, categories, pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdIn(State.PUBLISHED, categories, pageable).getContent();
        }

        //все мероприятия с категориями не позже даты
        if ((text == null || text.isBlank()) && onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBefore(State.PUBLISHED, onlyAvailable, categories, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateBefore(State.PUBLISHED, categories, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия с категориями начиная с даты
        if ((text == null || text.isBlank()) && onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateAfter(State.PUBLISHED, onlyAvailable, categories, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateAfter(State.PUBLISHED, categories, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия с категориями в промежутке
        if ((text == null || text.isBlank()) && onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBetween(State.PUBLISHED, onlyAvailable, categories, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateBetween(State.PUBLISHED, categories, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия paid
        if ((text == null || text.isBlank()) && onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIs(State.PUBLISHED, onlyAvailable, paid, pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndPaidIs(State.PUBLISHED, paid, pageable).getContent();
        }

        //все мероприятия paid и с категориями
        if ((text == null || text.isBlank()) && onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdIn(State.PUBLISHED, onlyAvailable, paid, categories, pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndPaidIsAndCategoryIdIn(State.PUBLISHED, paid, categories, pageable).getContent();
        }

        //все мероприятия paid не позже даты
        if ((text == null || text.isBlank()) && onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBefore(State.PUBLISHED, onlyAvailable, paid, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndPaidIsAndEventDateBefore(State.PUBLISHED, paid, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия paid начиная с даты
        if ((text == null || text.isBlank()) && onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateAfter(State.PUBLISHED, onlyAvailable, paid, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndPaidIsAndEventDateAfter(State.PUBLISHED, paid, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия paid в промежутке
        if ((text == null || text.isBlank()) && onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBetween(State.PUBLISHED, onlyAvailable, paid, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndPaidIsAndEventDateBetween(State.PUBLISHED, paid, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия Paid и с категориями не позже даты
        if ((text == null || text.isBlank()) && onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdInAndEventDateBefore(State.PUBLISHED, onlyAvailable, paid, categories, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndPaidIsAndCategoryIdInAndEventDateBefore(State.PUBLISHED, paid, categories, EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия paid и с категориями начиная с даты
        if ((text == null || text.isBlank()) && onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdInAndEventDateAfter(State.PUBLISHED, onlyAvailable, paid, categories, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndPaidIsAndCategoryIdInAndEventDateAfter(State.PUBLISHED, paid, categories, EventMapper.toDateTime(rangeFrom), pageable).getContent();
        }

        //все мероприятия paid и с категориями в промежутке
        if ((text == null || text.isBlank()) && onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdInAndEventDateBetween(State.PUBLISHED, onlyAvailable, paid, categories, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        if ((text == null || text.isBlank()) && !onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndPaidIsAndCategoryIdInAndEventDateBetween(State.PUBLISHED, paid, categories, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), pageable).getContent();
        }

        //все мероприятия поиск по text
        if (text != null && onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, text, text, pageable).getContent();
        }

        //все мероприятия поиск по text не позже даты
        if (text != null && onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        //все мероприятия поиск по text начиная с даты
        if (text != null && onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, EventMapper.toDateTime(rangeFrom), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, EventMapper.toDateTime(rangeFrom), text, text, pageable).getContent();
        }

        //все мероприятия поиск по text в промежутке
        if (text != null && onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        //все мероприятия поиск по text и paid
        if (text != null && onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, paid, text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, paid, text, text, pageable).getContent();
        }

        //все мероприятия поиск по text и paid не позже даты
        if (text != null && onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, paid, EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, paid, EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        //все мероприятия поиск по text и paid начиная с даты
        if (text != null && onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, paid, EventMapper.toDateTime(rangeFrom), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, paid, EventMapper.toDateTime(rangeFrom), text, text, pageable).getContent();
        }

        //все мероприятия поиск по text и paid в промежутке
        if (text != null && onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, paid, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, paid, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        //все мероприятия поиск по text и с категориями
        if (text != null && onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, categories, text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdInAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, categories, text, text, pageable).getContent();
        }

        //все мероприятия поиск по text и с категориями не позже даты
        if (text != null && onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, categories, EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, categories, EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        //все мероприятия поиск по text и с категориями начиная с даты
        if (text != null && onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, categories, EventMapper.toDateTime(rangeFrom), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, categories, EventMapper.toDateTime(rangeFrom), text, text, pageable).getContent();
        }
        //все мероприятия поиск по text и с категориями в промежутке
        if (text != null && onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, categories, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && !categories.isEmpty() && paid == null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, categories, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        //все мероприятия поиск по text и paid и с категориями
        if (text != null && onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, categories, paid, text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdInAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, categories, paid, text, text, pageable).getContent();
        }

        //все мероприятия поиск по text и paid и с категориями не позже даты
        if (text != null && onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, categories, paid, EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom == null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdInAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, categories, paid, EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        //все мероприятия поиск по text и paid и с категориями начиная с даты
        if (text != null && onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, categories, paid, EventMapper.toDateTime(rangeFrom), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd == null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdInAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, categories, paid, EventMapper.toDateTime(rangeFrom), text, text, pageable).getContent();
        }

        //все мероприятия поиск по text и paid и с категориями в промежутке
        if (text != null && onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, onlyAvailable, categories, paid, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        if (text != null && !onlyAvailable && !categories.isEmpty() && paid != null &&
                rangeFrom != null && rangeEnd != null) {
            events = iEventRepository.findAllByStateIsAndCategoryIdInAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State.PUBLISHED, categories, paid, EventMapper.toDateTime(rangeFrom), EventMapper.toDateTime(rangeEnd), text, text, pageable).getContent();
        }

        if (events.isEmpty()) {
            return Collections.emptyList();
        }

        List<Event> sortedList = events.stream()
                .sorted(Comparator.comparing(Event::getPublishedOn))
                .collect(Collectors.toList());

        List<Integer> eventIds = events.stream()
                .map(Event::getId).collect(Collectors.toList());

        List<String> urisList = eventIds.stream().map(u -> request.getRequestURI() + "/" + u).collect(Collectors.toList());

        List<ViewStatDto> statList = eventClient.getStat(sortedList.get(0).getPublishedOn(), timestamp, urisList, true);

        for (int i = 0; i < events.size(); i++) {
            events.get(i).setViews(statList.get(i).getHits());
        }

        if (sort != null && sort.equals("EVENT_DATE") && events.size() > 1) {
            events.sort(Comparator.comparing(Event::getEventDate));
        }

        if (sort != null && sort.equals("VIEWS") && events.size() > 1) {
            events.sort(Comparator.comparing(Event::getViews));
        }

        eventClient.sendStat(EventMapper.toHitDto("events", request.getRequestURI(), request.getRemoteAddr(), timestamp));

        eventIds.stream().forEach(e -> eventClient.sendStat(EventMapper.toHitDto("events", request.getRequestURI() + "/" + e, request.getRemoteAddr(), timestamp)));


        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventByIdPublic(int eventId, HttpServletRequest request) {
        Optional<Event> event = iEventRepository.findById(eventId);
        if (event.isEmpty() || !event.get().getState().equals(State.PUBLISHED)) {
            log.error("NotFoundException: {}", "При чтении из БД, Событие с ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие с ИД " + eventId + " не найдено");
        }

        List<ViewStatDto> statList = eventClient.getStat(event.get().getPublishedOn(), LocalDateTime.now(), Collections.singletonList(request.getRequestURI()), true);

        if (!statList.isEmpty()) {
            event.get().setViews(statList.get(0).getHits());
        } else {
            event.get().setViews(0);
        }

        HitDto hitDto = EventMapper.toHitDto("events", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());

        eventClient.sendStat(hitDto);

        return EventMapper.toEventFullDto(event.get());
    }

    @Override
    public List<EventShortDto> getAllUserEvents(int userId, int from, int size) {

        checkUser(userId);

        return iEventRepository.findAllByUserIdOrderByEventDateDesc(userId).stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getUserEventById(int userId, int eventId) {

        checkUser(userId);

        Optional<Event> userEvent = iEventRepository.findById(eventId);

        if (userEvent.isEmpty()) {
            log.error("NotFoundException: {}", "При поиске события по ИД, Событие с ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие с ИД " + eventId + " не найдено");
        }

        if (userEvent.get().getUser().getId() != userId) {
            log.error("BadRequestException: {}", "При поиске события по ИД, Событие с ИД " + eventId + " не принадлежит пользователю с ИД " + userId);
            throw new BadRequestException("Событие с ИД " + eventId + " не принадлежит пользователю с ИД " + userId);
        }

        return EventMapper.toEventFullDto(userEvent.get());
    }

    @Override
    public EventFullDto updateEventPrivate(UpdateEventDto updateEventDto, int userId) {

        Optional<Event> eventToUpdate = iEventRepository.findById(updateEventDto.getEventId());

        checkUser(userId);

        if (eventToUpdate.isEmpty()) {
            log.error("NotFoundException: {}", "При обновлении Событие с ИД " + updateEventDto.getEventId() + " не найдено");
            throw new NotFoundException("Событие с ИД " + updateEventDto.getEventId() + " не найдено");
        }

        if (eventToUpdate.get().getUser().getId() != userId) {
            log.error("BadRequestException: {}", "Обновлять может только собственник вещи");
            throw new BadRequestException("Обновлять может только собственник вещи");
        }

        if (eventToUpdate.get().getState().equals(State.PUBLISHED)) {
            log.error("ForbiddenException: {}", "Обновлять можно только в статусе PENDING или CANCELED");
            throw new ForbiddenException("Only pending or canceled events can be changed");
        }

        if (LocalDateTime.now().plusHours(2).isAfter(EventMapper.toDateTime(updateEventDto.getEventDate()))) {
            log.error("ForbiddenException: {}", "дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента");
            throw new ForbiddenException("Event can't be less than 2 hours from now");
        }

        if (updateEventDto.getAnnotation() != null) {
            eventToUpdate.get().setAnnotation(updateEventDto.getAnnotation());
        }
        if (updateEventDto.getDescription() != null) {
            eventToUpdate.get().setDescription(updateEventDto.getDescription());
        }
        if (updateEventDto.getTitle() != null) {
            eventToUpdate.get().setTitle(updateEventDto.getTitle());
        }
        if (updateEventDto.getEventDate() != null) {
            eventToUpdate.get().setEventDate(EventMapper.toDateTime(updateEventDto.getEventDate()));
        }
        if (updateEventDto.getCategory() != null) {
            eventToUpdate.get().setCategory(iCategoryService.getCategory(updateEventDto.getCategory()).get());
        }
        if (updateEventDto.getPaid() != null) {
            eventToUpdate.get().setPaid(updateEventDto.getPaid());
        }
        if (updateEventDto.getParticipantLimit() != null) {
            eventToUpdate.get().setParticipantLimit(updateEventDto.getParticipantLimit());
        }

        eventToUpdate.get().setState(State.PENDING);

        return EventMapper.toEventFullDto(iEventRepository.save(eventToUpdate.get()));
    }

    @Override
    public EventFullDto createEventPrivate(NewEventDto newEventDto, int userId) {

        User user = checkUser(userId);

        Optional<Category> category = iCategoryService.getCategory(newEventDto.getCategory());

        if (category.isEmpty()) {
            log.error("NotFoundException: {}", "При создании события Категория с ИД " + newEventDto.getCategory() + " не найдена");
            throw new NotFoundException("Категория с ИД " + newEventDto.getCategory() + " не найдена");
        }

        Location location = iLocationService.createEventLocation(newEventDto.getLocation(), newEventDto.getTitle());

        if (LocalDateTime.now().plusHours(2).isAfter(EventMapper.toDateTime(newEventDto.getEventDate()))) {
            log.error("ForbiddenException: {}", "дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента");
            throw new ForbiddenException("Event can't be less than 2 hours from now");
        }

        Event savedEvent = iEventRepository.save(EventMapper.toEvent(newEventDto, user, category.get(), location));

        location.setEvent(savedEvent);

        return EventMapper.toEventFullDto(savedEvent);

    }

    @Override
    public EventFullDto cancelUserEventById(int userId, int eventId) {

        checkUser(userId);

        Optional<Event> eventToCancel = iEventRepository.findById(eventId);

        if (eventToCancel.isEmpty()) {
            log.error("NotFoundException: {}", "При отмене события по ИД, Событие с ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие с ИД " + eventId + " не найдено");
        }

        if (eventToCancel.get().getUser().getId() != userId) {
            log.error("BadRequestException: {}", "При отмене события по ИД, Событие с ИД " + eventId + " не принадлежит пользователю с ИД " + userId);
            throw new BadRequestException("Событие с ИД " + eventId + " не принадлежит пользователю с ИД " + userId);
        }

        eventToCancel.get().setState(State.CANCELED);

        return EventMapper.toEventFullDto(iEventRepository.save(eventToCancel.get()));

    }

    @Override
    public Optional<Event> getEvent(int eventId) {
        return iEventRepository.findById(eventId);
    }

    @Override
    public List<Event> getEventsByIds(List<Integer> eventIds) {
        return iEventRepository.findAllByIdIn(eventIds);
    }

    @Override
    public List<EventShortDto> findAllEventsOrderedByDistance(LocationDto locationDto, int from, int size) {

        Pageable pageable = PageRequest.of(from / size, size);

        List<LocationInfoDto> locationList = iLocationService.findAllLocationsOrderedByDistance(locationDto);

        List<Integer> locationsIds = locationList.stream().map(LocationInfoDto::getId).collect(Collectors.toList());

        return iEventRepository.findAllByLocationIdIn(locationsIds, pageable).stream()
                .map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    public List<EventShortDto> findAllEventsWithinRadius(LocationDto locationDto, double radius, int from, int size) {

        Pageable pageable = PageRequest.of(from / size, size);

        List<LocationInfoDto> locationList = iLocationService.findAllLocationsWithinRadius(locationDto, radius);

        List<Integer> locationsIds = locationList.stream().map(LocationInfoDto::getId).collect(Collectors.toList());

        return iEventRepository.findAllByLocationIdIn(locationsIds, pageable).stream()
                .map(EventMapper::toEventShortDto).collect(Collectors.toList());

    }

    @Override
    public HttpStatus addMark(int userId, int eventId, int mark) {

        User user = checkUser(userId);

        Optional<Event> eventToMark = iEventRepository.findById(eventId);

        if (eventToMark.isEmpty()) {
            log.error("NotFoundException: {}", "Событие с ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие с ИД " + eventId + " не найдено");
        }

        if (eventToMark.get().getUser().getId() == userId) {
            log.error("BadRequestException: {}", "Событие с ИД " + eventId + " не может быть оценено инициатором с ИД " + userId);
            throw new BadRequestException("Событие с ИД " + eventId + " не может быть оценено инициатором с ИД " + userId);
        }

        if (!eventToMark.get().getState().equals(State.PUBLISHED)) {
            log.error("ForbiddenException: {}", "Оценить можно только в статусе PUBLISHED");
            throw new ForbiddenException("Only published events can be marked");
        }

        Optional<Mark> userMark = iMarkRepository.findMarkByUserIdAndEventId(eventId, userId);

        if (userMark.isPresent()) {
            userMark.get().setMark(mark);
            userMark.get().setLastUpdated(LocalDateTime.now());
            iMarkRepository.save(userMark.get());
        } else {
            Mark eventMark = new Mark(new MarkKey(userId, eventId), mark, null, null);
            iMarkRepository.save(eventMark);
        }

        Double eventRating = iMarkRepository.getEventRating(eventId);
        eventToMark.get().setRating(eventRating);
        Double userRating = iEventRepository.getUserRating(userId);
        user.setRating(userRating);
        iUserRepository.save(user);
        iEventRepository.save(eventToMark.get());

        return HttpStatus.OK;
    }

    @Override
    public List<EventFullDto> getEventsBySubscriptions(int userId, List<Integer> ids, int from, int size) {

        checkUser(userId);

        Pageable pageable = PageRequest.of(from / size, size);

        List<Integer> subscriptionList = iUserService.getUserSubscriptions(userId);

        return iEventRepository.findAllByUserIdInOrderByEventDateAsc(subscriptionList, pageable).stream()
                .map(EventMapper::toEventFullDto).collect(Collectors.toList());

    }

    @Override
    public List<EventShortDto> findAllEventsByAddress(String city, String suburb, String road, String houseNumber, int from, int size) {

        List<Location> locationsList = iLocationService.findLocationsByAddress(city, suburb, road, houseNumber);

        if (locationsList.isEmpty()) {
            return Collections.emptyList();
        }

        Pageable pageable = PageRequest.of(from / size, size);
        List<Integer> locationIds = locationsList.stream().map(Location::getId).collect(Collectors.toList());


        return iEventRepository.findAllByLocationIdIn(locationIds, pageable).stream().map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public Event saveEvent(Event event) {
        return iEventRepository.save(event);
    }

    private User checkUser(int userId) {
        Optional<User> user = iUserService.getUser(userId);

        if (user.isEmpty()) {
            log.error("NotFoundException: {}", "Пользователь с ИД " + userId + " не найден");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }
        return user.get();
    }
}
