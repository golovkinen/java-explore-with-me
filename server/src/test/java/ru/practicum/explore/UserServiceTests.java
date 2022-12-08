package ru.practicum.explore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import ru.practicum.explore.user.dto.UserDto;
import ru.practicum.explore.user.dto.UserShortDto;
import ru.practicum.explore.user.model.Subscription;
import ru.practicum.explore.user.model.SubscriptionKey;
import ru.practicum.explore.user.model.User;
import ru.practicum.explore.user.repository.ISubscriptionRepository;
import ru.practicum.explore.user.repository.IUserRepository;
import ru.practicum.explore.user.service.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTests {


    IUserRepository iUserRepository;

    ISubscriptionRepository iSubscriptionRepository;

    UserService userService;

    @BeforeEach
    void beforeEach() {
        iUserRepository = mock(IUserRepository.class);
        iSubscriptionRepository = mock(ISubscriptionRepository.class);
        userService = new UserService(iUserRepository, iSubscriptionRepository);

    }

    @Test
    void readAll() {

        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        Page<User> pageUser = new PageImpl<>(Collections.singletonList(user1));


        when(iUserRepository.findByIdIn(anyList(), any()))
                .thenReturn(pageUser);

        final List<UserDto> list = userService.getAll(Collections.singletonList(1), 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void readAllNoParams() {

        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        Page<User> pageUser = new PageImpl<>(Collections.singletonList(user1));


        when(iUserRepository.findAllUsersPageable(any()))
                .thenReturn(Collections.singletonList(user1));

        final List<UserDto> list = userService.getAll(Collections.emptyList(), 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void createUser() {
        UserDto userDto = new UserDto(null, "Email1@mail.com", "Name1", true);
        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());


        when(iUserRepository.save(any()))
                .thenReturn(user1);

        final UserDto newUser = userService.create(userDto);

        assertNotNull(newUser);
        assertEquals(1, newUser.getId());

    }


    @Test
    void update() {

        UserDto userDto = new UserDto(1, "UpdatedEmail1@mail.com", "UpdatedName1", true);
        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());
        User user2 = new User(1, "UpdatedEmail1@mail.com", "UpdatedName1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());


        when(iUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iUserRepository.save(any()))
                .thenReturn(user2);

        final UserDto updateUser = userService.update(userDto, 1);

        assertNotNull(updateUser);
        assertEquals(1, updateUser.getId());

    }

    @Test
    void allowUserSubscription() {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        when(iUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iUserRepository.save(any()))
                .thenReturn(user1);


        final UserDto updateUser = userService.allowSubscription(1, true);

        assertNotNull(updateUser);
        assertEquals(1, updateUser.getId());

    }

    @Test
    void subscribeToUser() {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());


        when(iUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iSubscriptionRepository.findSubscriptionById_UserIdAndId_SubscriberId(anyInt(), anyInt()))
                .thenReturn(Optional.empty());

        Subscription subscription = new Subscription(new SubscriptionKey(1, 2), null);
        when(iSubscriptionRepository.save(any()))
                .thenReturn(subscription);


        final HttpStatus httpStatus = userService.subscribeToUser(1, 2);

        assertEquals("200 OK", HttpStatus.OK.toString());

    }

    @Test
    void unsubscribeFromUser() {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        Subscription subscription = new Subscription(new SubscriptionKey(1, 2), null);


        when(iUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iSubscriptionRepository.findSubscriptionById_UserIdAndId_SubscriberId(anyInt(), anyInt()))
                .thenReturn(Optional.of(subscription));

        when(iSubscriptionRepository.save(any()))
                .thenReturn(subscription);


        final HttpStatus httpStatus = userService.unsubscribeFromUser(1, 2);

        assertEquals("200 OK", HttpStatus.OK.toString());

    }

    @Test
    void getUserSubscriptions() {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        Subscription subscription = new Subscription(new SubscriptionKey(1, 2), null);


        when(iSubscriptionRepository.findAllById_SubscriberIdOrderByCreatedOnDesc(anyInt()))
                .thenReturn(Collections.singletonList(subscription));


        final List<Integer> ids = userService.getUserSubscriptions(1);

        assertNotNull(ids);
        assertEquals(1, ids.get(0));

    }

    @Test
    void getAllUserSubscriptions() {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        Subscription subscription = new Subscription(new SubscriptionKey(1, 2), null);

        Page<User> pageUser = new PageImpl<>(Collections.singletonList(user1));

        when(iUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iSubscriptionRepository.findAllById_SubscriberIdOrderByCreatedOnDesc(anyInt()))
                .thenReturn(Collections.singletonList(subscription));

        when(iUserRepository.findByIdIn(anyList(), any()))
                .thenReturn(pageUser);

        final List<UserShortDto> userShortDtos = userService.getAllUserSubscriptions(1, 0, 10);

        assertNotNull(userShortDtos);
        assertEquals(1, userShortDtos.get(0).getId());

    }

    @Test
    void getAllUserSubscribers() throws Exception {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        Subscription subscription = new Subscription(new SubscriptionKey(1, 2), null);

        Page<User> page = new PageImpl<>(Collections.singletonList(user1));

        when(iUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iSubscriptionRepository.findAllById_SubscriberIdOrderByCreatedOnDesc(anyInt()))
                .thenReturn(Collections.singletonList(subscription));

        when(iUserRepository.findByIdIn(anyList(), any()))
                .thenReturn(page);

        final List<UserShortDto> userShortDtos = userService.getAllUserSubscriptions(1, 0, 10);

        assertNotNull(userShortDtos);
        assertEquals(1, userShortDtos.get(0).getId());

    }


}
