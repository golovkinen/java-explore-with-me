package ru.practicum.explore;

public class EventServiceTests {
/*

    IEventRepository iEventRepository;
    IUserService iUserService;
    IUserRepository iUserRepository;
    ILocationService iLocationService;
    ICategoryService iCategoryService;
    IMarkRepository iMarkRepository;
    EventService eventService;
    EventClient eventClient;

    @BeforeEach
    void beforeEach() {
        iEventRepository = mock(IEventRepository.class);
        iLocationService = mock(ILocationService.class);
        iUserService = mock(IUserService.class);
        iUserRepository = mock(IUserRepository.class);
        iCategoryService = mock(ICategoryService.class);
        eventClient = mock(EventClient.class);
        iMarkRepository = mock(IMarkRepository.class);

        eventService = new EventService(iEventRepository, iUserService, iUserRepository, iLocationService, iCategoryService, iMarkRepository, eventClient);

    }

    @Test
    void readAll() {

        User user1 = new User(1, "email1@mail.com", "Name1", new ArrayList<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Event event1 = new Event(1, "Item Name", "Item Desc", true, user1, new HashSet<>(), new HashSet<>(), null);

        when(iEventRepository.findAllPaged(any()))
                .thenReturn(Collections.singletonList(event1));

        final List<ItemInfoDto> list = itemService.readAll(0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void readAllUserItems() {

        ru.practicum.explore.item.model.Event event = new ru.practicum.explore.item.model.Event(1, "Item Name", "Item Desc", true, null, new HashSet<>(), new HashSet<>(), null);
        User user1 = new User(1, "email1@mail.com", "Name1", Collections.singletonList(event), new HashSet<>(), new HashSet<>(), new HashSet<>());
        ru.practicum.explore.item.model.Event event1 = new ru.practicum.explore.item.model.Event(1, "Item Name", "Item Desc", true, user1, new HashSet<>(), new HashSet<>(), null);

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.readAllUserItemsByUserIdPaged(anyInt(), any()))
                .thenReturn(Collections.singletonList(event1));

        final List<ItemInfoDto> list = itemService.readAllUserItems(1, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void createItem() {
        NewEventDto newEventDto = new NewEventDto(null, "Item Name", "Item Desc", true, null);
        User user1 = new User(1, "email1@mail.com", "Name1", new ArrayList<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        ru.practicum.explore.item.model.Event event1 = new ru.practicum.explore.item.model.Event(1, "Item Name", "Item Desc", true, user1, new HashSet<>(), new HashSet<>(), null);

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.save(any()))
                .thenReturn(event1);

        when(iUserRepository.save(any()))
                .thenReturn(user1);

        final ItemInfoDto newItem = itemService.create(newEventDto, 1);

        assertNotNull(newItem);
        assertEquals(1, newItem.getId());

    }

    @Test
    void readById() {

        NewEventDto newEventDto = new NewEventDto(null, "Item Name", "Item Desc", true, null);
        User user1 = new User(1, "email1@mail.com", "Name1", new ArrayList<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        ru.practicum.explore.item.model.Event event1 = new ru.practicum.explore.item.model.Event(1, "Item Name", "Item Desc", true, user1, new HashSet<>(), new HashSet<>(), null);

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        when(iEventRepository.getItemsLastBooking(anyInt(), any()))
                .thenReturn(Optional.empty());

        when(iEventRepository.getItemsNextBooking(anyInt(), any()))
                .thenReturn(Optional.empty());

        final ItemInfoDto newItem = itemService.read(1, 1);

        assertNotNull(newItem);
        assertEquals(1, newItem.getId());

    }

    @Test
    void update() {

        NewEventDto newEventDto = new NewEventDto(1, "Item Name", "Item Desc", true, null);
        User user1 = new User(1, "email1@mail.com", "Name1", new ArrayList<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        ru.practicum.explore.item.model.Event event1 = new ru.practicum.explore.item.model.Event(1, "Item Name", "Item Desc", true, user1, new HashSet<>(), new HashSet<>(), null);

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        when(iEventRepository.save(any()))
                .thenReturn(event1);

        final ItemInfoDto list = itemService.update(newEventDto, 1, 1);

        assertNotNull(list);
        assertEquals(1, list.getId());

    }

    @Test
    void searchItems() {

        NewEventDto newEventDto = new NewEventDto(null, "Item Name", "Item Desc", true, null);
        User user1 = new User(1, "email1@mail.com", "Name1", new ArrayList<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        ru.practicum.explore.item.model.Event event1 = new ru.practicum.explore.item.model.Event(1, "Item Name", "Item Desc", true, user1, new HashSet<>(), new HashSet<>(), null);

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.searchItemByWord(anyString(), any()))
                .thenReturn(Collections.singletonList(event1));

        when(iEventRepository.getItemsLastBooking(anyInt(), any()))
                .thenReturn(Optional.empty());

        when(iEventRepository.getItemsNextBooking(anyInt(), any()))
                .thenReturn(Optional.empty());

        final List<ItemInfoDto> newItem = itemService.searchItemByWord("Item", 0, 10);

        assertNotNull(newItem);
        assertEquals(1, newItem.get(0).getId());

    }

    @Test
    void createNewComment() {

        ru.practicum.explore.item.model.Event event = new ru.practicum.explore.item.model.Event(1, "Item Name", "Item Desc", true, null, new HashSet<>(), new HashSet<>(), null);
        CommentInfoDto commentInfoDto = new CommentInfoDto(null, "comment", "Name1", LocalDateTime.now());

        User user2 = new User(2, "email2@mail.com", "Name2", new ArrayList<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Booking lastBooking = new Booking(1, LocalDateTime.of(2022, 10, 1, 10, 00, 00),
                LocalDateTime.of(2022, 10, 2, 10, 00, 00), Status.APPROVED, event, user2);

        User user1 = new User(1, "email1@mail.com", "Name1", new ArrayList<>(), Collections.singleton(lastBooking), new HashSet<>(), new HashSet<>());
        ru.practicum.explore.item.model.Event event1 = new ru.practicum.explore.item.model.Event(1, "Item Name", "Item Desc", true, user1, new HashSet<>(), new HashSet<>(), null);
        Comment comment = new Comment(1, "Comment", LocalDateTime.of(2022, 10, 2, 10, 00, 00),
                event1, user2);

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        when(iEventRepository.checkUserBookedItemBeforeComment(anyInt(), anyInt(), any()))
                .thenReturn(Collections.singletonList(lastBooking));

        when(iEventRepository.getItemsLastBooking(anyInt(), any()))
                .thenReturn(Optional.empty());

        when(iEventRepository.getItemsNextBooking(anyInt(), any()))
                .thenReturn(Optional.empty());

        when(iCommentRepository.save(any()))
                .thenReturn(comment);

        final CommentInfoDto newComment = itemService.createComment(commentInfoDto, 1, 1);

        assertNotNull(newComment);
        assertEquals(1, newComment.getId());

    }

 */
}
