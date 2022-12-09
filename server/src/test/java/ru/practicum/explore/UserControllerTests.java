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
import ru.practicum.explore.user.controller.UserController;
import ru.practicum.explore.user.dto.UserDto;
import ru.practicum.explore.user.service.UserService;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTests {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllUsers() throws Exception {
        when(userService.getAll(anyList(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    void create() throws Exception {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        when(userService.create(any()))
                .thenReturn(userDto);

        mockMvc.perform(post("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("Email1@mail.com")))
                .andExpect(jsonPath("$.name", is("Name1")));

    }

    @Test
    void allowUserSubscription() throws Exception {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", false);

        when(userService.allowSubscription(anyInt(), anyBoolean()))
                .thenReturn(userDto);

        mockMvc.perform(patch("/user/{userId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("subscription", "true"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("Email1@mail.com")))
                .andExpect(jsonPath("$.name", is("Name1")));

    }

    @Test
    void subscribeToUser() throws Exception {

        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        when(userService.subscribeToUser(anyInt(), anyInt()))
                .thenReturn(HttpStatus.OK);

        mockMvc.perform(post("/user/{subscriberId}/subscription/{userId}", 1, 2)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());

    }

    @Test
    void unsubscribeFromUser() throws Exception {

        when(userService.unsubscribeFromUser(anyInt(), anyInt()))
                .thenReturn(HttpStatus.OK);

        mockMvc.perform(delete("/user/{subscriberId}/subscription/{userId}", 1, 2)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());

    }

    @Test
    void getAllUserSubscriptions() throws Exception {
        when(userService.getAllUserSubscriptions(anyInt(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/user/{userId}/subscription", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    void getAllUserSubscribers() throws Exception {
        when(userService.getAllUserSubscribers(anyInt(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/user/{userId}/subscriber", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    void deleteById() throws Exception {

        when(userService.delete(anyInt()))
                .thenReturn(HttpStatus.OK);

        mockMvc.perform(delete("/admin/users/{id}", 1))
                .andExpect(status().isOk());

    }
}
