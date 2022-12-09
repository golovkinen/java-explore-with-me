package ru.practicum.explore;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.event.controller.EventController;
import ru.practicum.explore.event.dto.*;
import ru.practicum.explore.event.service.EventService;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.user.dto.UserShortDto;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
@AutoConfigureMockMvc
public class EventControllerTests {

    @MockBean
    EventService eventService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllPublic() throws Exception {
        when(eventService.getAllPublic(anyString(), anyList(), anyBoolean(), anyString(), anyString(), anyBoolean(), anyString(), anyInt(), anyInt(), any()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/events")
                        .param("text", "Отвертка")
                        .param("categories", "1,2")
                        .param("paid", "true")
                        .param("rangeFrom", "2022-11-29 21:47:40")
                        .param("rangeEnd", "2022-11-30 21:47:40")
                        .param("onlyAvailable", "false")
                        .param("sort", "VIEWS")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    void getAllAdmin() throws Exception {
        when(eventService.getAllAdmin(anyList(), anyList(), anyList(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/events")
                        .param("users", "1,2,3")
                        .param("states", "PENDING, PUBLISHED")
                        .param("categories", "1,2")
                        .param("rangeFrom", "2022-11-29 21:47:40")
                        .param("rangeEnd", "2022-11-30 21:47:40")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    void create() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", 1, locationDto, true, 120, false);

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        when(eventService.createEventPrivate(any(), anyInt()))
                .thenReturn(eventFullDto);

        mockMvc.perform(post("/users/{id}/events", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEventDto)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.annotation", is("annotation1mustbe20min")))
                .andExpect(jsonPath("$.title", is("title1")))
                .andExpect(jsonPath("$.description", is("description1mustbe20min")));

    }

    @Test
    void getEventByIdPublic() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1", "title1", "description1", "2022-11-30 01:47:40", 1, locationDto, true, 120, false);

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1", "title1", "2022-11-30 01:47:40", categoryDto, userShortDto, true, null, null);

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1", "title1", "description1", "2022-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        when(eventService.getEventByIdPublic(anyInt(), any()))
                .thenReturn(eventFullDto);

        mockMvc.perform(get("/events/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.annotation", is("annotation1")))
                .andExpect(jsonPath("$.title", is("title1")))
                .andExpect(jsonPath("$.description", is("description1")));

    }

    @Test
    void updateEventPrivate() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        UpdateEventDto updateEventDto = new UpdateEventDto("Updatedannotation1", "Updatedtitle1", "Updateddescription1", "2022-11-30 01:47:40", 1, true, 120, 1);

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1", "title1", "2022-11-30 01:47:40", categoryDto, userShortDto, true, null, null);

        EventFullDto eventFullDto = new EventFullDto(1, "Updatedannotation1", "Updatedtitle1", "Updateddescription1", "2022-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        when(eventService.updateEventPrivate(any(), anyInt()))
                .thenReturn(eventFullDto);

        mockMvc.perform(patch("/users/{id}/events", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEventDto)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.annotation", is("Updatedannotation1")))
                .andExpect(jsonPath("$.title", is("Updatedtitle1")))
                .andExpect(jsonPath("$.description", is("Updateddescription1")));

    }

    @Test
    void updateEventAdmin() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        AdminUpdateEventRequestDto adminUpdateEventRequestDto = new AdminUpdateEventRequestDto("Updatedannotation1", "Updatedtitle1", "Updateddescription1", "2022-11-30 01:47:40", 1, locationDto, true, 120, true);

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1", "title1", "2022-11-30 01:47:40", categoryDto, userShortDto, true, null, null);

        EventFullDto eventFullDto = new EventFullDto(1, "Updatedannotation1", "Updatedtitle1", "Updateddescription1", "2022-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        when(eventService.updateEventAdmin(any(), anyInt()))
                .thenReturn(eventFullDto);

        mockMvc.perform(put("/admin/events/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminUpdateEventRequestDto)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.annotation", is("Updatedannotation1")))
                .andExpect(jsonPath("$.title", is("Updatedtitle1")))
                .andExpect(jsonPath("$.description", is("Updateddescription1")));

    }

    @Test
    void getAllUserEvents() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1", "title1", "description1", "2022-11-30 01:47:40", 1, locationDto, true, 120, false);

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1", "title1", "2022-11-30 01:47:40", categoryDto, userShortDto, true, null, null);

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1", "title1", "description1", "2022-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        when(eventService.getAllUserEvents(anyInt(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(eventShortDto));

        mockMvc.perform(get("/users/{id}/events", 1)
                        .contentType(MediaType.APPLICATION_JSON))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].annotation", is("annotation1")))
                .andExpect(jsonPath("$[0].title", is("title1")));

    }

    @Test
    void getUserEventById() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1", "title1", "description1", "2022-11-30 01:47:40", 1, locationDto, true, 120, false);

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1", "title1", "2022-11-30 01:47:40", categoryDto, userShortDto, true, null, null);

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1", "title1", "description1", "2022-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        when(eventService.getUserEventById(anyInt(), anyInt()))
                .thenReturn(eventFullDto);

        mockMvc.perform(get("/users/{userId}/events/{eventId}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.annotation", is("annotation1")))
                .andExpect(jsonPath("$.title", is("title1")))
                .andExpect(jsonPath("$.description", is("description1")));

    }

    @Test
    void cancelUserEventById() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1", "title1", "description1", "2022-11-30 01:47:40", 1, locationDto, true, 120, false);

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1", "title1", "2022-11-30 01:47:40", categoryDto, userShortDto, true, null, null);

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1", "title1", "description1", "2022-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "CANCELED", null, null);

        when(eventService.cancelUserEventById(anyInt(), anyInt()))
                .thenReturn(eventFullDto);

        mockMvc.perform(patch("/users/{userId}/events/{eventId}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.annotation", is("annotation1")))
                .andExpect(jsonPath("$.title", is("title1")))
                .andExpect(jsonPath("$.description", is("description1")))
                .andExpect(jsonPath("$.state", is("CANCELED")));

    }

    @Test
    void rejectEventAdmin() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1", "title1", "description1", "2022-11-30 01:47:40", 1, locationDto, true, 120, false);

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1", "title1", "2022-11-30 01:47:40", categoryDto, userShortDto, true, null, null);

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1", "title1", "description1", "2022-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "REJECTED", null, null);

        when(eventService.rejectEventAdmin(anyInt()))
                .thenReturn(eventFullDto);

        mockMvc.perform(patch("/admin/events/{eventId}/reject", 1)
                        .contentType(MediaType.APPLICATION_JSON))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.annotation", is("annotation1")))
                .andExpect(jsonPath("$.title", is("title1")))
                .andExpect(jsonPath("$.description", is("description1")))
                .andExpect(jsonPath("$.state", is("REJECTED")));

    }

    @Test
    void addMark() throws Exception {
        when(eventService.addMark(anyInt(), anyInt(), anyInt()))
                .thenReturn(HttpStatus.OK);

        mockMvc.perform(post("/users/{userId}/events/{eventId}", 1, 1)
                        .param("mark", "8"))
                .andExpect(status().isOk());

    }

    @Test
    void findAllEventsOrderedByDistance() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1", "title1", "description1", "2022-11-30 01:47:40", 1, locationDto, true, 120, false);

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1", "title1", "2022-11-30 01:47:40", categoryDto, userShortDto, true, null, null);

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1", "title1", "description1", "2022-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PUBLISHED", null, null);

        when(eventService.findAllEventsOrderedByDistance(any(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(eventShortDto));

        mockMvc.perform(get("/events/location/distance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDto))
                        .param("from", "0")
                        .param("size", "10"))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].annotation", is("annotation1")))
                .andExpect(jsonPath("$[0].title", is("title1")));

    }

    @Test
    void findAllEventsWithinRadius() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1", "title1", "description1", "2022-11-30 01:47:40", 1, locationDto, true, 120, false);

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1", "title1", "2022-11-30 01:47:40", categoryDto, userShortDto, true, null, null);

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1", "title1", "description1", "2022-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PUBLISHED", null, null);

        when(eventService.findAllEventsWithinRadius(any(), anyDouble(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(eventShortDto));

        mockMvc.perform(get("/events/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDto))
                        .param("radius", "1000")
                        .param("from", "0")
                        .param("size", "10"))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].annotation", is("annotation1")))
                .andExpect(jsonPath("$[0].title", is("title1")));

    }

    @Test
    void findAllEventsByAddress() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1", "title1", "description1", "2022-11-30 01:47:40", 1, locationDto, true, 120, false);

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1", "title1", "2022-11-30 01:47:40", categoryDto, userShortDto, true, null, null);

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1", "title1", "description1", "2022-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PUBLISHED", null, null);

        when(eventService.findAllEventsByAddress(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(eventShortDto));

        mockMvc.perform(get("/events/location/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("city", "Москва")
                        .param("suburb", "Центральный")
                        .param("road", "Якиманка")
                        .param("houseNumber", "10")
                        .param("from", "0")
                        .param("size", "10"))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].annotation", is("annotation1")))
                .andExpect(jsonPath("$[0].title", is("title1")));

    }

    @Test
    void getEventsBySubscriptions() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1", "title1", "description1", "2022-11-30 01:47:40", 1, locationDto, true, 120, false);

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1", "title1", "2022-11-30 01:47:40", categoryDto, userShortDto, true, null, null);

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1", "title1", "description1", "2022-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PUBLISHED", null, null);

        when(eventService.getEventsBySubscriptions(anyInt(), anyList(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(eventFullDto));

        mockMvc.perform(get("/user/{userId}/events/subscription", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("ids", "1,2,3")
                        .param("from", "0")
                        .param("size", "10"))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].annotation", is("annotation1")))
                .andExpect(jsonPath("$[0].title", is("title1")));

    }

}
