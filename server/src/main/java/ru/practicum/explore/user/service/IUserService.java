package ru.practicum.explore.user.service;

import org.springframework.http.HttpStatus;
import ru.practicum.explore.user.dto.UserDto;
import ru.practicum.explore.user.dto.UserShortDto;
import ru.practicum.explore.user.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserDto create(UserDto userDto);

    List<UserDto> getAll(List<Integer> ids, int from, int size);

    UserDto update(UserDto userDto, int userId);

    UserDto allowSubscription(int userId, boolean subscription);

    HttpStatus subscribeToUser(int userId, int subscrId);

    HttpStatus unsubscribeFromUser(int userId, int subscrId);

    List<Integer> getUserSubscriptions(int userId);

    List<UserShortDto> getAllUserSubscriptions(int userId, int from, int size);

    List<UserShortDto> getAllUserSubscribers(int userId, int from, int size);

    HttpStatus delete(int id);

    Optional<User> getUser(int userId);
}
