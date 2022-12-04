package ru.practicum.explore.request.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.request.dto.RequestInfoDto;
import ru.practicum.explore.request.service.IRequestService;

import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Validated
public class RequestController {

    private IRequestService iRequestService;

    public RequestController(IRequestService iRequestService) {
        this.iRequestService = iRequestService;
    }

    @PostMapping(value = "/users/{userId}/requests")
    public RequestInfoDto createRequest(@Positive @PathVariable(name = "userId") int userId,
                                        @Positive @RequestParam(name = "eventId") int eventId) {

        return iRequestService.createRequest(userId, eventId);
    }

    @GetMapping(value = "/users/{userId}/requests")
    public List<RequestInfoDto> getAllUserRequests(@Positive @PathVariable(name = "userId") int userId) {
        return iRequestService.getAllUserRequests(userId);
    }

    @PatchMapping(value = "/users/{userId}/requests/{requestId}/cancel")
    public RequestInfoDto userCancelOwnRequest( @Positive @PathVariable(name = "userId") int userId,
                                                @Positive @PathVariable(name = "requestId") int requestId) {

        return iRequestService.userCancelOwnRequest(userId, requestId);
    }

    @GetMapping(value = "/users/{userId}/events/{eventId}/requests")
    public List<RequestInfoDto> getAllRequestsForUserEvent(@Positive @PathVariable(name = "userId") int userId,
                                                           @Positive @PathVariable(name = "eventId") int eventId) {
        return iRequestService.getAllRequestsForUserEvent(userId, eventId);
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests/{requestId}/confirm")
    public RequestInfoDto userConfirmRequest( @Positive @PathVariable(name = "userId") int userId,
                                                @Positive @PathVariable(name = "eventId") int eventId,
                                                @Positive @PathVariable(name = "requestId") int requestId) {

        return iRequestService.userConfirmRequest(userId, eventId, requestId);
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests/{requestId}/reject")
    public RequestInfoDto userRejectRequest( @Positive @PathVariable(name = "userId") int userId,
                                              @Positive @PathVariable(name = "eventId") int eventId,
                                              @Positive @PathVariable(name = "requestId") int requestId) {

        return iRequestService.userRejectRequest(userId, eventId, requestId);
    }

    @GetMapping(value = "/users/{userId}/requests/subscription")
    public List<RequestInfoDto> getRequestsBySubscriptions (@Positive @PathVariable(name = "userId") int userId,
                                                          @Positive @RequestParam(name = "ids", required = false) Optional<List<Integer>> ids,
                                                          @RequestParam(name = "from", defaultValue = "0") int from,
                                                          @RequestParam(name = "size", defaultValue = "10") int size) {

        return iRequestService.getRequestsBySubscriptions(userId, ids.orElse(Collections.emptyList()), from, size);
    }
}
