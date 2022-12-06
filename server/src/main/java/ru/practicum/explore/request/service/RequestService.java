package ru.practicum.explore.request.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.event.enums.State;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.service.IEventService;
import ru.practicum.explore.exceptionhandler.BadRequestException;
import ru.practicum.explore.exceptionhandler.NotFoundException;
import ru.practicum.explore.request.dto.RequestInfoDto;
import ru.practicum.explore.request.enums.Status;
import ru.practicum.explore.request.mapper.RequestMapper;
import ru.practicum.explore.request.model.Request;
import ru.practicum.explore.request.repository.IRequestRepository;
import ru.practicum.explore.user.model.User;
import ru.practicum.explore.user.service.IUserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestService implements IRequestService {

    private final IUserService iUserService;
    private final IEventService iEventService;
    private final IRequestRepository iRequestRepository;

    public RequestService(IUserService iUserService, IEventService iEventService, IRequestRepository iRequestRepository) {
        this.iUserService = iUserService;
        this.iEventService = iEventService;
        this.iRequestRepository = iRequestRepository;
    }

    @Override
    public RequestInfoDto createRequest(int userId, int eventId) {

        User requester = checkUser(userId);

        Optional<Event> event = iEventService.getEvent(eventId);

        if (event.isEmpty()) {
            log.error("NotFoundException: {}", "Событие c ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие c ИД " + eventId + " не найдено");
        }

        if (event.get().getUser().getId() == userId) {
            log.error("BadRequestException: {}", "инициатор события не может добавить запрос на участие в своём событии");
            throw new BadRequestException("инициатор события не может добавить запрос на участие в своём событии");
        }

        if (!event.get().getState().equals(State.PUBLISHED)) {
            log.error("BadRequestException: {}", "нельзя участвовать в неопубликованном событии");
            throw new BadRequestException("нельзя участвовать в неопубликованном событии");
        }

        if (event.get().getParticipantLimit() == event.get().getRequests().stream().filter(f -> f.getStatus().equals(Status.CONFIRMED)).count()) {
            log.error("BadRequestException: {}", "у события достигнут лимит запросов на участие");
            throw new BadRequestException("у события достигнут лимит запросов на участие");
        }

        Optional<Request> doubleRequestCheck = iRequestRepository.findRequestsByEventIdIsAndUserIdIs(eventId, userId);

        if (doubleRequestCheck.isPresent()) {
            log.error("BadRequestException: {}", "Пользователь c ИД " + eventId + " уже отправлял запрос");
            throw new BadRequestException("Пользователь c ИД " + eventId + " уже отправлял запрос");
        }

        if (event.get().getRequestModeration()) {
            Request newRequest = RequestMapper.createRequest(requester, event.get());
            event.get().getRequests().add(newRequest);
            return RequestMapper.toRequestInfoDto(iRequestRepository.save(newRequest));
        }

        Request newRequest = RequestMapper.createRequest(requester, event.get());
        newRequest.setStatus(Status.CONFIRMED);
        event.get().getRequests().add(newRequest);
        iEventService.saveEvent(event.get());

        return RequestMapper.toRequestInfoDto(iRequestRepository.save(newRequest));
    }

    @Override
    public List<RequestInfoDto> getAllUserRequests(int userId) {

        checkUser(userId);


        return iRequestRepository.findAllByUserIdOrderByCreatedOnDesc(userId).stream()
                .map(RequestMapper::toRequestInfoDto).collect(Collectors.toList());
    }

    @Override
    public List<RequestInfoDto> getRequestsBySubscriptions(int userId, List<Integer> ids, int from, int size) {

        checkUser(userId);

        Pageable pageable = PageRequest.of(from / size, size);

        List<Integer> subscriptionList = iUserService.getUserSubscriptions(userId);

        return iRequestRepository.findAllByUserIdInOrderByUserIdAsc(subscriptionList, pageable).stream()
                .map(RequestMapper::toRequestInfoDto).collect(Collectors.toList());
    }

    @Override
    public RequestInfoDto userCancelOwnRequest(int userId, int requestId) {

        checkUser(userId);

        Optional<Request> userRequest = iRequestRepository.findById(requestId);

        if (userRequest.isEmpty()) {
            log.error("NotFoundException: {}", "Запрос на участие c ИД " + requestId + " не найден");
            throw new NotFoundException("Запрос на участие c ИД " + requestId + " не найден");
        }

        if (userRequest.get().getUser().getId() != userId) {
            log.error("BadRequestException: {}", "Только собственник запроса может его отменить");
            throw new BadRequestException("Только собственник запроса может его отменить");
        }

        userRequest.get().setStatus(Status.CANCELED);
        return RequestMapper.toRequestInfoDto(iRequestRepository.save(userRequest.get()));
    }

    @Override
    public List<RequestInfoDto> getAllRequestsForUserEvent(int userId, int eventId) {

        checkUser(userId);

        Optional<Event> event = iEventService.getEvent(eventId);

        if (event.isEmpty()) {
            log.error("NotFoundException: {}", "Событие c ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие c ИД " + eventId + " не найдено");
        }

        if (event.get().getUser().getId() != userId) {
            log.error("BadRequestException: {}", "Только инициатор события может посмотреть заявки на событие");
            throw new BadRequestException("Только инициатор события может посмотреть заявки на событие");
        }

        return event.get().getRequests().stream().map(RequestMapper::toRequestInfoDto).collect(Collectors.toList());
    }

    @Override
    public RequestInfoDto userConfirmRequest(int userId, int eventId, int requestId) {

        Request requestToApprove = checkUserAndEventAndRequest(userId, eventId, requestId);

        Optional<Event> event = iEventService.getEvent(eventId);
        requestToApprove.setStatus(Status.CONFIRMED);

        Request approvedRequest = iRequestRepository.save(requestToApprove);

        if (event.get().getParticipantLimit() == event.get().getRequests().stream()
                .filter(f -> f.getStatus().equals(Status.CONFIRMED)).count()) {

            List<Request> requestsToReject = event.get().getRequests().stream()
                    .filter(f -> f.getStatus().equals(Status.PENDING)).collect(Collectors.toList());

            if (!requestsToReject.isEmpty()) {
                requestsToReject.stream().forEach(e -> e.setStatus(Status.REJECTED));
                iRequestRepository.saveAll(requestsToReject);
            }
        }

        return RequestMapper.toRequestInfoDto(approvedRequest);

    }

    @Override
    public RequestInfoDto userRejectRequest(int userId, int eventId, int requestId) {

        Request requestToReject = checkUserAndEventAndRequest(userId, eventId, requestId);
        requestToReject.setStatus(Status.REJECTED);
        return RequestMapper.toRequestInfoDto(iRequestRepository.save(requestToReject));
    }

    private Request checkUserAndEventAndRequest(int userId, int eventId, int requestId) {

        checkUser(userId);

        Optional<Event> event = iEventService.getEvent(eventId);

        if (event.isEmpty()) {
            log.error("NotFoundException: {}", "Событие c ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие c ИД " + eventId + " не найдено");
        }

        Optional<Request> request = iRequestRepository.findById(requestId);

        if (request.isEmpty()) {
            log.error("NotFoundException: {}", "Запрос на участие c ИД " + requestId + " не найден");
            throw new NotFoundException("Запрос на участие c ИД " + requestId + " не найден");
        }

        if (request.get().getEvent().getId() != eventId) {
            log.error("BadRequestException: {}", "Запрос на участие c ИД " + requestId + " не относится к Событию с ИД " + eventId);
            throw new BadRequestException("Запрос на участие c ИД " + requestId + " не относится к Событию с ИД " + eventId);
        }

        if (!request.get().getStatus().equals(Status.PENDING)) {
            log.error("BadRequestException: {}", "Только заявкам в статусе ожидания можно изменить статус. Текущий статус : " + request.get().getStatus());
            throw new BadRequestException("Только заявки в статусе ожидания можно изменить");
        }

        if (event.get().getUser().getId() != userId) {
            log.error("BadRequestException: {}", "Только инициатор события может одобрить заявки на событие");
            throw new BadRequestException("Только инициатор события может одобрить заявки на событие");
        }

        return request.get();
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
