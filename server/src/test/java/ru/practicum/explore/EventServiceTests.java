package ru.practicum.explore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import ru.practicum.explore.category.model.Category;
import ru.practicum.explore.category.service.ICategoryService;
import ru.practicum.explore.event.client.EventClient;
import ru.practicum.explore.event.dto.*;
import ru.practicum.explore.event.enums.State;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.model.Mark;
import ru.practicum.explore.event.model.MarkKey;
import ru.practicum.explore.event.repository.IEventRepository;
import ru.practicum.explore.event.repository.IMarkRepository;
import ru.practicum.explore.event.service.EventService;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.LocationInfoDto;
import ru.practicum.explore.location.model.Location;
import ru.practicum.explore.location.service.ILocationService;
import ru.practicum.explore.user.model.User;
import ru.practicum.explore.user.repository.IUserRepository;
import ru.practicum.explore.user.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventServiceTests {


    IEventRepository iEventRepository;
    IUserService iUserService;
    IUserRepository iUserRepository;
    ILocationService iLocationService;
    ICategoryService iCategoryService;
    IMarkRepository iMarkRepository;
    EventService eventService;
    EventClient eventClient;

    User user1;

    User user2;
    Event event1;
    Event event2;
    Event event3;

    Event event4;
    Location location1;
    Category category1;
    LocalDateTime dateTime;

    HttpServletRequest request;


    @BeforeEach
    void beforeEach() {
        iEventRepository = mock(IEventRepository.class);
        iLocationService = mock(ILocationService.class);
        iUserService = mock(IUserService.class);
        iUserRepository = mock(IUserRepository.class);
        iCategoryService = mock(ICategoryService.class);
        eventClient = mock(EventClient.class);
        iMarkRepository = mock(IMarkRepository.class);
        request = mock(HttpServletRequest.class);

        eventService = new EventService(iEventRepository, iUserService, iUserRepository, iLocationService, iCategoryService, iMarkRepository, eventClient);

        location1 = new Location(1, "title", null, 55.710697, 37.607448, null, null, null, null, null, null, event1);
        category1 = new Category(1, "category1", new HashSet<>());
        user1 = new User(1, "email1@mail.com", "Name1", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());
        user2 = new User(2, "email2@mail.com", "Name2", true, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        event1 = new Event(1, "annotation1mustbe20min", "description1mustbe20min", "title1", LocalDateTime.now().plusDays(5), true, true, LocalDateTime.now(), null, 120, false, State.PENDING, 0, new HashSet<>(), new HashSet<>(), user1, category1, null, location1, 0.0);
        event2 = new Event(2, "annotation2mustbe20min", "description2mustbe20min", "title2", LocalDateTime.now().plusDays(10), true, true, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), 120, false, State.PUBLISHED, 30, new HashSet<>(), new HashSet<>(), user1, category1, null, location1, 0.0);
        event3 = new Event(3, "annotation3mustbe20min", "description3mustbe20min", "title3", LocalDateTime.now().plusDays(15), true, true, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20), 120, false, State.PUBLISHED, 10, new HashSet<>(), new HashSet<>(), user1, category1, null, location1, 0.0);
        event4 = new Event(4, "annotation4mustbe20min", "description4mustbe20min", "title4", LocalDateTime.now().plusDays(5), true, true, LocalDateTime.now(), null, 120, false, State.PENDING, 0, new HashSet<>(), new HashSet<>(), user2, category1, null, location1, 0.0);

        dateTime = LocalDateTime.now().plusDays(5);


    }

    @Test
    void getAllAdminNoParametrs() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllPaged(any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null, null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByEventDateBefore(any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null, "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByEventDateAfter(any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "2022-11-30 01:47:40", null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllAdminWithEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByEventDateBetween(any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "2022-11-30 01:47:40", "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminwithCategories() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByCategoryIdIn(anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.emptyList(), Collections.singletonList(1), null, null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithCatAndEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByCategoryIdInAndEventDateBefore(anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.emptyList(), Collections.singletonList(1), null, "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithCatAndEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByCategoryIdInAndEventDateAfter(anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.emptyList(), Collections.singletonList(1), "2022-11-30 01:47:40", null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllAdminWithCatAndEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByCategoryIdInAndEventDateBetween(anyList(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.emptyList(), Collections.singletonList(1), "2022-11-30 01:47:40", "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithState() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByStateIn(anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.singletonList(State.PUBLISHED), Collections.emptyList(), null, null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithStatAndEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByStateInAndEventDateBefore(anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.singletonList(State.PUBLISHED), Collections.emptyList(), null, "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithStatAndEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByStateInAndEventDateAfter(anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.singletonList(State.PUBLISHED), Collections.emptyList(), "2022-11-30 01:47:40", null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllAdminWithStatAndEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByStateInAndEventDateBetween(anyList(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.singletonList(State.PUBLISHED), Collections.emptyList(), "2022-11-30 01:47:40", "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminwithStateAndCat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByStateInAndCategoryIdIn(anyList(), anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.singletonList(State.PUBLISHED), Collections.singletonList(1), null, null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithStatAndCatAndEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByStateInAndCategoryIdInAndEventDateBefore(anyList(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.singletonList(State.PUBLISHED), Collections.singletonList(1), null, "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithStatAndCatAndEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByStateInAndCategoryIdInAndEventDateAfter(anyList(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.singletonList(State.PUBLISHED), Collections.singletonList(1), "2022-11-30 01:47:40", null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllAdminWithStatAndCatAndEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByStateInAndCategoryIdInAndEventDateBetween(anyList(), anyList(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.emptyList(), Collections.singletonList(State.PUBLISHED), Collections.singletonList(1), "2022-11-30 01:47:40", "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllAdminWithUser() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdIn(anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.emptyList(), Collections.emptyList(), null, null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithUserAndEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndEventDateBefore(anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.emptyList(), Collections.emptyList(), null, "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithUserAndEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndEventDateAfter(anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.emptyList(), Collections.emptyList(), "2022-11-30 01:47:40", null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllAdminWithUserAndEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndEventDateBetween(anyList(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.emptyList(), Collections.emptyList(), "2022-11-30 01:47:40", "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithUserAndStat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndStateIn(anyList(), anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.singletonList(State.PUBLISHED), Collections.emptyList(), null, null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithUserAndStatAndEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndStateInAndEventDateBefore(anyList(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.singletonList(State.PUBLISHED), Collections.emptyList(), null, "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithUserAndStatAndEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndStateInAndEventDateAfter(anyList(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.singletonList(State.PUBLISHED), Collections.emptyList(), "2022-11-30 01:47:40", null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllAdminWithUserAndStatAndEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndStateInAndEventDateBetween(anyList(), anyList(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.singletonList(State.PUBLISHED), Collections.emptyList(), "2022-11-30 01:47:40", "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithUserAndCat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndCategoryIdIn(anyList(), anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.emptyList(), Collections.singletonList(1), null, null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithUserAndCatAndEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndCategoryIdInAndEventDateBefore(anyList(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.emptyList(), Collections.singletonList(1), null, "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithUserAndCatAndEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndCategoryIdInAndEventDateAfter(anyList(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.emptyList(), Collections.singletonList(1), "2022-11-30 01:47:40", null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllAdminWithUserAndCatAndEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndCategoryIdInAndEventDateBetween(anyList(), anyList(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.emptyList(), Collections.singletonList(1), "2022-11-30 01:47:40", "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithUserAndStatAndCat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndStateInAndCategoryIdIn(anyList(), anyList(), anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.singletonList(State.PUBLISHED), Collections.singletonList(1), null, null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithUserAndStatAndCatAndEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndStateInAndCategoryIdInAndEventDateBefore(anyList(), anyList(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.singletonList(State.PUBLISHED), Collections.singletonList(1), null, "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllAdminWithUserAndStatAndCatAndEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndStateInAndCategoryIdInAndEventDateAfter(anyList(), anyList(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.singletonList(State.PUBLISHED), Collections.singletonList(1), "2022-11-30 01:47:40", null, 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllAdminWithUserAndStatAndCatAndEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));


        when(iEventRepository.findAllByUserIdInAndStateInAndCategoryIdInAndEventDateBetween(anyList(), anyList(), anyList(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> list = eventService.getAllAdmin(Collections.singletonList(1), Collections.singletonList(State.PUBLISHED), Collections.singletonList(1), "2022-11-30 01:47:40", "2022-11-30 01:47:40", 0, 10);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void updateEventAdmin() {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        AdminUpdateEventRequestDto adminUpdateEventRequestDto = new AdminUpdateEventRequestDto("Annotation1mustBe20Chars", "Title1mustBe20CharsMin", "Description1mustBe20Chars", "2023-11-30 01:47:40", 1, locationDto, false, 128, true);


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        when(iEventRepository.save(any()))
                .thenReturn(event1);

        final EventFullDto update = eventService.updateEventAdmin(adminUpdateEventRequestDto, 1);

        assertNotNull(update);
        assertEquals(1, update.getId());

    }

    @Test
    void publishEventAdmin() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        when(iEventRepository.save(any()))
                .thenReturn(event1);

        final EventFullDto publishEventAdmin = eventService.publishEventAdmin(1);

        assertNotNull(publishEventAdmin);
        assertEquals(1, publishEventAdmin.getId());

    }

    @Test
    void rejectEventAdmin() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        when(iEventRepository.save(any()))
                .thenReturn(event1);

        final EventFullDto publishEventAdmin = eventService.publishEventAdmin(1);

        assertNotNull(publishEventAdmin);
        assertEquals(1, publishEventAdmin.getId());

    }

    @Test
    void getAllPublicNoParameters() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIs(any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), null, null, null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicSortedByEventDate() {

        Page<Event> pageEvent = new PageImpl<>(List.of(event2, event3));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/2", 10);
        ViewStatDto viewStatDto2 = new ViewStatDto("/events", "/events/3", 20);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(List.of(viewStatDto, viewStatDto2));

        when(iEventRepository.findAllByStateIs(any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), null, null, null, false, "EVENT_DATE", 0, 10, request);

        assertNotNull(list);
        assertEquals(2, list.get(0).getId());
        assertEquals(2, list.size());

    }

    @Test
    void getAllPublicSortedByViewsDesc() {

        Page<Event> pageEvent = new PageImpl<>(List.of(event2, event3));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/2", 30);
        ViewStatDto viewStatDto2 = new ViewStatDto("/events", "/events/3", 10);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(List.of(viewStatDto, viewStatDto2));

        when(iEventRepository.findAllByStateIs(any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), null, null, null, false, "VIEWS", 0, 10, request);

        assertNotNull(list);
        assertEquals(2, list.get(0).getId());
        assertEquals(2, list.size());

    }

    @Test
    void getAllPublicWithEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndEventDateBefore(any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), null, null, "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicWithEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndEventDateAfter(any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), null, "2022-11-30 01:47:40", null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicWithEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndEventDateBetween(any(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), null, "2022-11-30 01:47:40", "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailable() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIs(any(), anyBoolean(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), null, null, null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableWithEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndEventDateBefore(any(), anyBoolean(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), null, null, "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableWithEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndEventDateAfter(any(), anyBoolean(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), null, "2022-11-30 01:47:40", null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicAvailableWithEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndEventDateBetween(any(), anyBoolean(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), null, "2022-11-30 01:47:40", "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicWithCat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdIn(any(), anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), null, null, null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicWithCAtAndEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateBefore(any(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), null, null, "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicWithCatAndEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateAfter(any(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), null, "2022-11-30 01:47:40", null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicWithCatAndEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateBetween(any(), anyList(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), null, "2022-11-30 01:47:40", "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableWithCat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdIn(any(), anyBoolean(), anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), null, null, null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableWithCatAndEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBefore(any(), anyBoolean(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), null, null, "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableWithCatAndEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateAfter(any(), anyBoolean(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), null, "2022-11-30 01:47:40", null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicAvailableWithCatAndEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBetween(any(), anyBoolean(), anyList(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), null, "2022-11-30 01:47:40", "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaid() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIs(any(), anyBoolean(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), true, null, null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidWithEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIsAndEventDateBefore(any(), anyBoolean(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), true, null, "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidWithEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIsAndEventDateAfter(any(), anyBoolean(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), true, "2022-11-30 01:47:40", null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicPaidWithEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIsAndEventDateBetween(any(), anyBoolean(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), true, "2022-11-30 01:47:40", "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailablePaid() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIs(any(), anyBoolean(), anyBoolean(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), true, null, null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailablePaidWithEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBefore(any(), anyBoolean(), anyBoolean(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), true, null, "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailablePaidWithEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateAfter(any(), anyBoolean(), anyBoolean(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), true, "2022-11-30 01:47:40", null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicAvailablePaidWithEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBetween(any(), anyBoolean(), anyBoolean(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.emptyList(), true, "2022-11-30 01:47:40", "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidWithCat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIsAndCategoryIdIn(any(), anyBoolean(), anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), true, null, null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidWithCAtAndEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIsAndCategoryIdInAndEventDateBefore(any(), anyBoolean(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), true, null, "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidWithCatAndEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIsAndCategoryIdInAndEventDateAfter(any(), anyBoolean(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), true, "2022-11-30 01:47:40", null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicPaidWithCatAndEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIsAndCategoryIdInAndEventDateBetween(any(), anyBoolean(), anyList(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), true, "2022-11-30 01:47:40", "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidAvailableWithCat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdIn(any(), anyBoolean(), anyBoolean(), anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), true, null, null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailablePaidWithCatAndEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdInAndEventDateBefore(any(), anyBoolean(), anyBoolean(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), true, null, "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailablePaidWithCatAndEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdInAndEventDateAfter(any(), anyBoolean(), anyBoolean(), anyList(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), true, "2022-11-30 01:47:40", null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicAvailablePaidWithCatAndEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdInAndEventDateBetween(any(), anyBoolean(), anyBoolean(), anyList(), any(), any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic(null, Collections.singletonList(1), true, "2022-11-30 01:47:40", "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicText() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), null, null, null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicWithTextEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), null, null, "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicWithTextEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), null, "2022-11-30 01:47:40", null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicWithTextEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), any(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), null, "2022-11-30 01:47:40", "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableText() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), null, null, null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableWithTextEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), null, null, "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableWithTextEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), null, "2022-11-30 01:47:40", null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicAvailableWithTextEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), any(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), null, "2022-11-30 01:47:40", "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicWithTextCat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdInAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyList(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), null, null, null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicWithCAtAndTextEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyList(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), null, null, "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicWithCatAndTextEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyList(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), null, "2022-11-30 01:47:40", null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicWithCatAndTextEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdInAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyList(), any(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), null, "2022-11-30 01:47:40", "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableWithTextCat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyList(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), null, null, null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableWithCatAndTextEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyList(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), null, null, "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableWithCatAndTextEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyList(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), null, "2022-11-30 01:47:40", null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicAvailableWithCatAndTextEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyList(), any(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), null, "2022-11-30 01:47:40", "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidText() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), true, null, null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidWithTextEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), true, null, "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidWithTextEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), true, "2022-11-30 01:47:40", null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicPaidWithTextEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), any(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), true, "2022-11-30 01:47:40", "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailableTextPaid() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyBoolean(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), true, null, null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailablePaidWithTextEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyBoolean(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), true, null, "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailablePaidWithTextEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyBoolean(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), true, "2022-11-30 01:47:40", null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicAvailablePaidWithTextEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyBoolean(), any(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.emptyList(), true, "2022-11-30 01:47:40", "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidWithTextCat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdInAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyList(), anyBoolean(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), true, null, null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidWithCAtAndTextEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdInAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyList(), anyBoolean(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), true, null, "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidWithCatAndTextEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdInAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyList(), anyBoolean(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), true, "2022-11-30 01:47:40", null, false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicPaidWithCatAndTextEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndCategoryIdInAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyList(), anyBoolean(), any(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), true, "2022-11-30 01:47:40", "2023-11-30 01:47:40", false, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicPaidAvailableWithTextCat() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyList(), anyBoolean(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), true, null, null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailablePaidWithCatAndTextEventDateBefore() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyList(), anyBoolean(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), true, null, "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getAllPublicAvailablePaidWithCatAndTextEventDateAfter() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyList(), anyBoolean(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), true, "2022-11-30 01:47:40", null, true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }


    @Test
    void getAllPublicAvailablePaidWithCatAndTextEventDateBetween() {

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(any(), anyBoolean(), anyList(), anyBoolean(), any(), any(), anyString(), anyString(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> list = eventService.getAllPublic("text", Collections.singletonList(1), true, "2022-11-30 01:47:40", "2023-11-30 01:47:40", true, null, 0, 10, request);

        assertNotNull(list);
        assertEquals(1, list.get(0).getId());
        assertEquals(1, list.size());

    }

    @Test
    void getEventByIdPublic() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event2));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/2", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.save(any()))
                .thenReturn(event2);

        final EventFullDto getEventById = eventService.getEventByIdPublic(2, request);

        assertNotNull(getEventById);
        assertEquals(2, getEventById.getId());

    }

    @Test
    void getAllUserEvents() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.findAllByUserIdOrderByEventDateDesc(anyInt()))
                .thenReturn(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.save(any()))
                .thenReturn(event1);

        final List<EventShortDto> events = eventService.getAllUserEvents(1, 0, 10);

        assertNotNull(events);
        assertEquals(1, events.get(0).getId());

    }

    @Test
    void getUserEventById() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.save(any()))
                .thenReturn(event1);

        final EventFullDto events = eventService.getUserEventById(1, 1);

        assertNotNull(events);
        assertEquals(1, events.getId());

    }

    @Test
    void updateEventPrivate() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        UpdateEventDto updateEventDto = new UpdateEventDto("annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", 1, true, 120, 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iEventRepository.save(any()))
                .thenReturn(event1);

        final EventFullDto events = eventService.updateEventPrivate(updateEventDto, 1);

        assertNotNull(events);
        assertEquals(1, events.getId());

    }

    @Test
    void createEventPrivate() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", 1, locationDto, true, 120, false);

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iLocationService.createEventLocation(any(), anyString()))
                .thenReturn(location1);

        when(iEventRepository.save(any()))
                .thenReturn(event1);

        final EventFullDto events = eventService.createEventPrivate(newEventDto, 1);

        assertNotNull(events);
        assertEquals(1, events.getId());

    }

    @Test
    void cancelUserEventById() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iLocationService.createEventLocation(any(), anyString()))
                .thenReturn(location1);

        when(iEventRepository.save(any()))
                .thenReturn(event1);

        final EventFullDto events = eventService.cancelUserEventById(1, 1);

        assertNotNull(events);
        assertEquals(1, events.getId());

    }

    @Test
    void getEvent() {


        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));


        final Optional<Event> events = eventService.getEvent(1);

        assertNotNull(events);
        assertEquals(1, events.get().getId());

    }

    @Test
    void getEventsByIds() {


        when(iEventRepository.findAllByIdIn(anyList()))
                .thenReturn(Collections.singletonList(event1));


        final List<Event> events = eventService.getEventsByIds(Collections.singletonList(1));

        assertNotNull(events);
        assertEquals(1, events.get(0).getId());

    }

    @Test
    void findAllEventsOrderedByDistance() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        LocationInfoDto locationInfoDto = new LocationInfoDto(1, "LocationTitle", 55.710697, 37.607448, "LocationAddress");

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iLocationService.findAllLocationsOrderedByDistance(any()))
                .thenReturn(Collections.singletonList(locationInfoDto));

        when(iEventRepository.findAllByLocationIdIn(any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> events = eventService.findAllEventsOrderedByDistance(locationDto, 0, 10);

        assertNotNull(events);
        assertEquals(1, events.get(0).getId());

    }

    @Test
    void findAllEventsWithinRadius() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        LocationInfoDto locationInfoDto = new LocationInfoDto(1, "LocationTitle", 55.710697, 37.607448, "LocationAddress");

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iLocationService.findAllLocationsWithinRadius(any(), anyDouble()))
                .thenReturn(Collections.singletonList(locationInfoDto));

        when(iEventRepository.findAllByLocationIdIn(any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> events = eventService.findAllEventsWithinRadius(locationDto, 1000, 0, 10);

        assertNotNull(events);
        assertEquals(1, events.get(0).getId());

    }

    @Test
    void addMark() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user2));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event2));

        when(iMarkRepository.findMarkByUserIdAndEventId(anyInt(), anyInt()))
                .thenReturn(Optional.empty());

        Mark eventMark = new Mark(new MarkKey(1, 1), 5, LocalDateTime.now(), null);

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        LocationInfoDto locationInfoDto = new LocationInfoDto(1, "LocationTitle", 55.710697, 37.607448, "LocationAddress");

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iLocationService.findAllLocationsWithinRadius(any(), anyDouble()))
                .thenReturn(Collections.singletonList(locationInfoDto));

        when(iEventRepository.findAllByLocationIdIn(any(), any()))
                .thenReturn(pageEvent);

        when(iMarkRepository.save(any()))
                .thenReturn(eventMark);

        when(iMarkRepository.getEventRating(anyInt()))
                .thenReturn(5.0);

        when(iEventRepository.getUserRating(anyInt()))
                .thenReturn(5.0);

        when(iUserRepository.save(any()))
                .thenReturn(user1);

        when(iEventRepository.save(any()))
                .thenReturn(event1);

        final HttpStatus events = eventService.addMark(2, 2, 5);

        assertTrue(events.is2xxSuccessful());

    }

    @Test
    void getEventsBySubscriptionsWithIds() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iUserService.getUserSubscriptions(anyInt()))
                .thenReturn(Collections.singletonList(2));

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        LocationInfoDto locationInfoDto = new LocationInfoDto(1, "LocationTitle", 55.710697, 37.607448, "LocationAddress");

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iLocationService.findAllLocationsWithinRadius(any(), anyDouble()))
                .thenReturn(Collections.singletonList(locationInfoDto));

        when(iEventRepository.findAllByUserIdInOrderByEventDateAsc(anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> events = eventService.getEventsBySubscriptions(1, Collections.singletonList(1), 0, 10);

        assertNotNull(events);
        assertEquals(1, events.get(0).getId());

    }

    @Test
    void getEventsBySubscriptionsWithoutIds() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iUserService.getUserSubscriptions(anyInt()))
                .thenReturn(Collections.singletonList(2));

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        LocationInfoDto locationInfoDto = new LocationInfoDto(1, "LocationTitle", 55.710697, 37.607448, "LocationAddress");

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iLocationService.findAllLocationsWithinRadius(any(), anyDouble()))
                .thenReturn(Collections.singletonList(locationInfoDto));

        when(iEventRepository.findAllByUserIdInOrderByEventDateAsc(anyList(), any()))
                .thenReturn(pageEvent);

        final List<EventFullDto> events = eventService.getEventsBySubscriptions(1, Collections.emptyList(), 0, 10);

        assertNotNull(events);
        assertEquals(1, events.get(0).getId());

    }

    @Test
    void findAllEventsByAddress() {


        when(iCategoryService.getCategory(anyInt()))
                .thenReturn(Optional.of(category1));

        when(iUserService.getUser(anyInt()))
                .thenReturn(Optional.of(user1));

        when(iEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(event1));

        Page<Event> pageEvent = new PageImpl<>(Collections.singletonList(event1));

        ViewStatDto viewStatDto = new ViewStatDto("/events", "/events/1", 1);

        when(request.getRequestURI()).thenReturn("/events");

        when(eventClient.getStat(any(), any(), anyList(), anyBoolean()))
                .thenReturn(Collections.singletonList(viewStatDto));

        when(iLocationService.findLocationsByAddress(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Collections.singletonList(location1));

        when(iEventRepository.findAllByLocationIdIn(any(), any()))
                .thenReturn(pageEvent);

        final List<EventShortDto> events = eventService.findAllEventsByAddress("city", "suburb", "road", "houseNumber", 0, 10);

        assertNotNull(events);
        assertEquals(1, events.get(0).getId());

    }
}
