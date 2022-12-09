package ru.practicum.explore.user.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.explore.exceptionhandler.BadRequestException;
import ru.practicum.explore.exceptionhandler.ConflictException;
import ru.practicum.explore.exceptionhandler.ForbiddenException;
import ru.practicum.explore.exceptionhandler.NotFoundException;
import ru.practicum.explore.user.dto.UserDto;
import ru.practicum.explore.user.dto.UserShortDto;
import ru.practicum.explore.user.mapper.UserMapper;
import ru.practicum.explore.user.model.Subscription;
import ru.practicum.explore.user.model.SubscriptionKey;
import ru.practicum.explore.user.model.User;
import ru.practicum.explore.user.repository.ISubscriptionRepository;
import ru.practicum.explore.user.repository.IUserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserService implements IUserService {

    IUserRepository iUserRepository;
    ISubscriptionRepository iSubscriptionRepository;


    @Override
    public UserDto create(UserDto userDto) {

        return UserMapper.toUserDto(iUserRepository.save(UserMapper.toUser(userDto)));
    }

    @Override
    public List<UserDto> getAll(List<Integer> ids, int from, int size) {

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        List<User> usersList;

        if (!ids.isEmpty()) {
            usersList = iUserRepository.findByIdIn(ids, pageable).getContent();
        } else {

            usersList = iUserRepository.findAllUsersPageable(pageable);
        }

        if (usersList.isEmpty()) {
            return Collections.emptyList();
        }

        return usersList.stream().map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(UserDto userDto, int userId) {

        Optional<User> userToUpdate = iUserRepository.findById(userId);

        if (userToUpdate.isEmpty()) {
            log.error("NotFoundException: {}", "При обновлении пользователя, Пользователь с ИД " + userId + " не найден");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }

        if (iUserRepository.findByEmail(userDto.getEmail()).isPresent()) {
            log.error("ConflictException: {}", "При обновлении пользователя, Пользователь с с email " + userDto.getEmail() + " уже существует");
            throw new ConflictException("Пользователь с email " + userDto.getEmail() + " уже существует");
        }

        if (userDto.getName() != null) {
            userToUpdate.get().setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            userToUpdate.get().setEmail(userDto.getEmail());
        }

        return UserMapper.toUserDto(iUserRepository.save(userToUpdate.get()));

    }

    @Override
    public UserDto allowSubscription(int userId, boolean subscription) {

        Optional<User> userToUpdate = iUserRepository.findById(userId);

        if (userToUpdate.isEmpty()) {
            log.error("NotFoundException: {}", "При обновлении пользователя, Пользователь с ИД " + userId + " не найден");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }

        if (!subscription) {
            iSubscriptionRepository.deleteAllById_UserId(userId);
        }

        userToUpdate.get().setAllowSubscription(subscription);

        return UserMapper.toUserDto(iUserRepository.save(userToUpdate.get()));
    }

    @Override
    public HttpStatus subscribeToUser(int userId, int subscrId) {

        Optional<User> subscriber = iUserRepository.findById(subscrId);

        if (subscriber.isEmpty()) {
            log.error("NotFoundException: {}", "Пользователь с ИД " + subscrId + " не найден");
            throw new NotFoundException("Пользователь с ИД " + subscrId + " не найден");
        }

        Optional<User> userToSubscribe = iUserRepository.findById(userId);

        if (userToSubscribe.isEmpty()) {
            log.error("NotFoundException: {}", "Пользователь с ИД " + userId + " не найден");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }

        if (!userToSubscribe.get().getAllowSubscription()) {
            log.error("ForbiddenException: {}", "На пользователя с ИД " + userId + " нельзя подписаться");
            throw new ForbiddenException("На пользователя с ИД " + userId + " нельзя подписаться");
        }

        if (iSubscriptionRepository.findSubscriptionById_UserIdAndId_SubscriberId(userId, subscrId).isPresent()) {
            log.error("BadRequestException: {}", "На пользователя с ИД " + subscrId + " подписка уже оформлена");
            throw new BadRequestException("На пользователя с ИД " + subscrId + " подписка уже оформлена");
        }

        Subscription subscription = new Subscription(new SubscriptionKey(userId, subscrId), null);
        iSubscriptionRepository.save(subscription);

        return HttpStatus.OK;
    }

    @Override
    public HttpStatus unsubscribeFromUser(int userId, int subscrId) {
        Optional<User> subscriber = iUserRepository.findById(subscrId);

        if (subscriber.isEmpty()) {
            log.error("NotFoundException: {}", "Пользователь с ИД " + subscrId + " не найден");
            throw new NotFoundException("Пользователь с ИД " + subscrId + " не найден");
        }

        Optional<User> userToUnsubscribe = iUserRepository.findById(userId);

        if (userToUnsubscribe.isEmpty()) {
            log.error("NotFoundException: {}", "Пользователь с ИД " + userId + " не найден");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }

        Optional<Subscription> subscription = iSubscriptionRepository.findSubscriptionById_UserIdAndId_SubscriberId(userId, subscrId);

        if (subscription.isEmpty()) {
            log.error("BadRequestException: {}", "На пользователя с ИД " + subscrId + " подписка не оформлена");
            throw new BadRequestException("На пользователя с ИД " + subscrId + " подписка не оформлена");
        }

        iSubscriptionRepository.delete(subscription.get());

        return HttpStatus.OK;
    }

    @Override
    public List<Integer> getUserSubscriptions(int userId) {
        return iSubscriptionRepository.findAllById_SubscriberIdOrderByCreatedOnDesc(userId).stream()
                .map(s -> s.getId().getUserId()).collect(Collectors.toList());
    }

    @Override
    public List<UserShortDto> getAllUserSubscriptions(int userId, int from, int size) {

        Optional<User> user = iUserRepository.findById(userId);

        if (user.isEmpty()) {
            log.error("NotFoundException: {}", "Пользователь с ИД " + userId + " не найден");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        List<Integer> subscriptionList = iSubscriptionRepository.findAllById_SubscriberIdOrderByCreatedOnDesc(userId).stream()
                .map(s -> s.getId().getUserId()).collect(Collectors.toList());

        if (subscriptionList.isEmpty()) {
            return Collections.emptyList();
        }

        return iUserRepository.findByIdIn(subscriptionList, pageable).stream()
                .map(UserMapper::toUserShortDto).collect(Collectors.toList());
    }

    @Override
    public List<UserShortDto> getAllUserSubscribers(int userId, int from, int size) {
        Optional<User> user = iUserRepository.findById(userId);

        if (user.isEmpty()) {
            log.error("NotFoundException: {}", "Пользователь с ИД " + userId + " не найден");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        List<Integer> subscribersList = iSubscriptionRepository.findAllById_UserIdOrderByCreatedOnDesc(userId).stream()
                .map(s -> s.getId().getSubscriberId()).collect(Collectors.toList());

        if (subscribersList.isEmpty()) {
            return Collections.emptyList();
        }

        return iUserRepository.findByIdIn(subscribersList, pageable).stream()
                .map(UserMapper::toUserShortDto).collect(Collectors.toList());
    }

    @Override
    public HttpStatus delete(int userId) {
        if (iUserRepository.findById(userId).isEmpty()) {
            log.error("NotFoundException: {}", "При удалении пользователя, Пользователь с ИД " + userId + " не найден");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }
        iUserRepository.deleteById(userId);
        return HttpStatus.OK;
    }

    @Override
    public Optional<User> getUser(int userId) {
        return iUserRepository.findById(userId);
    }
}
