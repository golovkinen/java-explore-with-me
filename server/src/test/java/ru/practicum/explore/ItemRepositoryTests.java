package ru.practicum.explore;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(scripts = "/test_schema.sql")
public class ItemRepositoryTests {
/*

    @Autowired
    ru.practicum.explore.item.repository.IEventRepository iEventRepository;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    ICommentRepository iCommentRepository;


    User user1;
    User user2;
    ru.practicum.explore.item.model.Event event1;
    ru.practicum.explore.item.model.Event event2;
    Comment comment1;

    @BeforeEach
    void beforeEach() {

        user1 = iUserRepository.save(new User(null, "Email1@mail.com", "Name1", Collections.singletonList(event1), new HashSet<>(), new HashSet<>(), new HashSet<>()));
        user2 = iUserRepository.save(new User(null, "Email2@mail.com", "Name2", Collections.singletonList(event2), new HashSet<>(), new HashSet<>(), Collections.singleton(comment1)));
        event1 = iEventRepository.save(new ru.practicum.explore.item.model.Event(null, "Item Name1", "Item1 Desc", true, user1, new HashSet<>(), Collections.singleton(comment1), null));
        comment1 = iCommentRepository.save(new Comment(null, "Comment", LocalDateTime.of(2022, 10, 2, 10, 00, 00),
                event1, user2));
        event2 = iEventRepository.save(new ru.practicum.explore.item.model.Event(null, "Item Name2", "Item2 Desc", true, user2, new HashSet<>(), new HashSet<>(), null));
    }

    @Test
    void readAll() {
        List<ru.practicum.explore.item.model.Event> list = iEventRepository.findAllPaged(PageRequest.of(0, 10));

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getId());
        assertEquals(2, list.get(1).getId());
    }

    @Test
    void readAllUserItems() {
        List<ru.practicum.explore.item.model.Event> list = iEventRepository.readAllUserItemsByUserIdPaged(1, PageRequest.of(0, 10));

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
    }

    @Test
    void readById() {
        Optional<ru.practicum.explore.item.model.Event> list = iEventRepository.findById(2);

        assertNotNull(list);
        assertEquals(2, list.get().getId());
    }

    @Test
    void createNew() {
        ru.practicum.explore.item.model.Event list = iEventRepository.save(new ru.practicum.explore.item.model.Event(null, "Item Name3", "Item3 Desc", true, user2, new HashSet<>(), new HashSet<>(), null));

        assertNotNull(list);
        assertEquals(3, list.getId());
    }

    @Test
    void deleteByUser() {
        iEventRepository.deleteById(1);
        Optional<ru.practicum.explore.item.model.Event> list = iEventRepository.findById(1);
        assertTrue(list.isEmpty());
    }

    @Test
    void createNewComment() {
        Comment list = iCommentRepository.save(new Comment(null, "Comment", LocalDateTime.of(2022, 10, 2, 10, 00, 00),
                event1, user2));
        assertNotNull(list);
        assertEquals(2, list.getId());
    }

 */
}