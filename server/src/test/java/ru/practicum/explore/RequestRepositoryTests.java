package ru.practicum.explore;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(scripts = "/test_schema.sql")
public class RequestRepositoryTests {

/*    @Autowired
    ru.practicum.explore.item.repository.IEventRepository iEventRepository;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    ICommentRepository iCommentRepository;

    @Autowired
    IBookingRepository iBookingRepository;
    @Autowired
    IRequestRepository iRequestRepository;

    User user1;
    User user2;
    ru.practicum.explore.item.model.Event event1;
    ru.practicum.explore.item.model.Event event2;
    Comment comment1;
    Booking booking1;
    Booking booking2;
    Booking booking3;

    Request request1;

    Request request2;
    Request request3;

    @BeforeEach
    void beforeEach() {

        user1 = iUserRepository.save(new User(null, "Email1@mail.com", "Name1", Collections.singletonList(event1), new HashSet<>(), new HashSet<>(), new HashSet<>()));
        user2 = iUserRepository.save(new User(null, "Email2@mail.com", "Name2", Collections.singletonList(event2), Collections.singleton(booking1), new HashSet<>(), Collections.singleton(comment1)));
        event1 = iEventRepository.save(new ru.practicum.explore.item.model.Event(null, "Item Name1", "Item1 Desc", true, user1, Collections.singleton(booking1), Collections.singleton(comment1), null));
        comment1 = iCommentRepository.save(new Comment(null, "Comment", LocalDateTime.of(2022, 10, 2, 10, 00, 00),
                event1, user2));
        event2 = iEventRepository.save(new ru.practicum.explore.item.model.Event(null, "Item Name2", "Item2 Desc", true, user2, new HashSet<>(), new HashSet<>(), null));
        booking1 = iBookingRepository.save(new Booking(null, LocalDateTime.of(2022, 10, 1, 10, 00, 00),
                LocalDateTime.of(2022, 10, 2, 10, 00, 00), Status.APPROVED, event1, user2));
        booking2 = iBookingRepository.save(new Booking(null, LocalDateTime.of(2022, 11, 1, 10, 00, 00),
                LocalDateTime.of(2022, 11, 2, 10, 00, 00), Status.WAITING, event1, user2));
        booking3 = iBookingRepository.save(new Booking(null, LocalDateTime.of(2022, 12, 1, 10, 00, 00),
                LocalDateTime.of(2022, 12, 2, 10, 00, 00), Status.REJECTED, event1, user2));
        request1 = iRequestRepository.save(new Request(null, "Request1 Desc", LocalDateTime.of(2022, 10, 2, 10, 00, 00), user1, new HashSet<>()));
        request2 = iRequestRepository.save(new Request(null, "Request2 Desc", LocalDateTime.of(2022, 11, 2, 10, 00, 00), user2, new HashSet<>()));
        request3 = iRequestRepository.save(new Request(null, "Request3 Desc", LocalDateTime.of(2022, 12, 2, 10, 00, 00), user1, new HashSet<>()));
    }

    @Test
    void getAllUserRequests() {
        List<Request> list = iRequestRepository.findAllByUserIdOrderByCreatedOnDesc(1);

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(3, list.get(0).getId());
    }

    @Test
    void getAllRequests() {
        List<Request> list = iRequestRepository.getPagedRequests(2, PageRequest.of(0, 10));

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getId());
    }

    @Test
    void getRequestById() {
        Optional<Request> list = iRequestRepository.findById(2);

        assertNotNull(list);
        assertEquals(2, list.get().getId());
    }

    @Test
    void createNew() {
        Request list = iRequestRepository.save(new Request(null, "Request4 Desc", LocalDateTime.of(2022, 12, 2, 10, 00, 00), user1, new HashSet<>()));

        assertNotNull(list);
        assertEquals(4, list.getId());
    }

 */
}