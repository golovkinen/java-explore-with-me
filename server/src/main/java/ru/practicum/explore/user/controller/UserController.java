package ru.practicum.explore.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.user.dto.UserDto;
import ru.practicum.explore.user.dto.UserShortDto;
import ru.practicum.explore.user.service.IUserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Validated
public class UserController {
    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping(value = "/admin/users")
    public List<UserDto> getAll(@RequestParam(name = "ids", required = false) Optional<List<Integer>> ids,
    @RequestParam(name = "from", defaultValue = "0") int from,
    @RequestParam(name = "size", defaultValue = "10") int size) {
        return iUserService.getAll(ids.orElse(Collections.emptyList()), from, size);
    }


    @PostMapping(value = "/admin/users")
    public UserDto create(@Valid @RequestBody UserDto userDto) {

        return iUserService.create(userDto);
    }

    @PatchMapping(value = "/user/{userId}")
    public UserDto allowSubscription (@Positive @PathVariable(name = "userId") int userId,
                                      @NotNull @RequestParam(name = "subscription") Boolean subscription) {

        return iUserService.allowSubscription(userId, subscription);
    }

    @PostMapping(value = "/user/{subscriberId}/subscription/{userId}")
    public HttpStatus subscribeToUser (@Positive @PathVariable(name = "userId") int userId,
                                      @Positive @PathVariable(name = "subscriberId") int subscrId) {

        return iUserService.subscribeToUser(userId, subscrId);
    }

    @DeleteMapping(value = "/user/{subscriberId}/subscription/{userId}")
    public HttpStatus unsubscribeFromUser (@Positive @PathVariable(name = "userId") int userId,
                                       @Positive @PathVariable(name = "subscriberId") int subscrId) {

        return iUserService.unsubscribeFromUser(userId, subscrId);
    }

    @GetMapping(value = "/user/{userId}/subscription")
    public List<UserShortDto> getAllUserSubscriptions (@Positive @PathVariable(name = "userId") int userId,
                                                       @RequestParam(name = "from", defaultValue = "0") int from,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {

        return iUserService.getAllUserSubscriptions(userId, from, size);
    }

    @GetMapping(value = "/user/{userId}/subscriber")
    public List<UserShortDto> getAllUserSubscribers (@Positive @PathVariable(name = "userId") int userId,
                                                       @RequestParam(name = "from", defaultValue = "0") int from,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {

        return iUserService.getAllUserSubscribers(userId, from, size);
    }

    @DeleteMapping(value = "/admin/users/{id}")
    public HttpStatus delete(@Positive @PathVariable(name = "id") int id) {
        return iUserService.delete(id);
    }

}
