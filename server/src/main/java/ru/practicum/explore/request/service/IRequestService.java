package ru.practicum.explore.request.service;

import ru.practicum.explore.request.dto.RequestInfoDto;

import java.util.List;

public interface IRequestService {
    RequestInfoDto createRequest(int userId, int eventId);

    List<RequestInfoDto> getAllUserRequests(int userId);

    List<RequestInfoDto> getRequestsBySubscriptions(int userId, List<Integer> ids, int from, int size);

    RequestInfoDto userCancelOwnRequest(int userId, int requestId);

    List<RequestInfoDto> getAllRequestsForUserEvent(int userId, int eventId);

    RequestInfoDto userConfirmRequest(int userId, int eventId, int requestId);

    RequestInfoDto userRejectRequest(int userId, int eventId, int requestId);
}
