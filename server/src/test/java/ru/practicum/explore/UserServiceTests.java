package ru.practicum.explore;

public class UserServiceTests {
/*

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

        when(iUserRepository.findAll())
                .thenReturn(Collections.singletonList(user1));

        final List<UserDto> list = userService.getAll(Collections.singletonList(1), 0, 10);

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


        final UserDto updateUser = userService.allowSubscription( 1, true);

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


        final HttpStatus httpStatus = userService.subscribeToUser( 1, 2);

        assertEquals("OK", HttpStatus.OK);

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


        final HttpStatus httpStatus = userService.unsubscribeFromUser( 1, 2);

        assertEquals("OK", HttpStatus.OK);

    }

    @Test
    void getUserSubscriptions() {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        Subscription subscription = new Subscription(new SubscriptionKey(1, 2), null);


        when(iSubscriptionRepository.findAllById_SubscriberIdOrderByCreatedOnDesc(anyInt()))
                .thenReturn(Collections.singletonList(subscription));


        final List<Integer> ids = userService.getUserSubscriptions( 1);

        assertNotNull(ids);
        assertEquals(1, ids.get(0));

    }

    @Test
    void getAllUserSubscriptions() {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        Subscription subscription = new Subscription(new SubscriptionKey(1, 2), null);

        Page pageUser = new PageImpl<User>(Collections.singletonList(user1), PageRequest.of(1, 10), 1);

        when(iUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iSubscriptionRepository.findAllById_SubscriberIdOrderByCreatedOnDesc(anyInt()))
                .thenReturn(Collections.singletonList(subscription));

        when(iUserRepository.findByIdIn(anyList(), any()))
                .thenReturn(pageUser);

        final List<UserShortDto> userShortDtos = userService.getAllUserSubscriptions( 1, 0, 10);

        assertNotNull(userShortDtos);
        assertEquals(1, userShortDtos.get(0));

    }

    @Test
    void getAllUserSubscribers() throws Exception {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        User user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        Subscription subscription = new Subscription(new SubscriptionKey(1, 2), null);

        when(iUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iSubscriptionRepository.findAllById_SubscriberIdOrderByCreatedOnDesc(anyInt()))
                .thenReturn(Collections.singletonList(subscription));

        when(iUserRepository.findByIdIn(anyList(), any()))
                .thenReturn((Page<User>) Collections.singletonList(user1));

        final List<UserShortDto> userShortDtos = userService.getAllUserSubscriptions( 1, 0, 10);

        assertNotNull(userShortDtos);
        assertEquals(1, userShortDtos.get(0));

    }

 */


}
