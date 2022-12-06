package ru.practicum.explore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.explore.user.dto.UserDto;
import ru.practicum.explore.user.dto.UserShortDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserDtoTests {
    @Autowired
    private JacksonTester<UserDto> json;

    @Autowired
    private JacksonTester<UserShortDto> jsonShort;

    @Test
    void testSerialize() throws Exception {
        UserDto userDto = new UserDto(1, "Email1@mail.com", "Name1", true);

        JsonContent<UserDto> result = json.write(userDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(userDto.getId());
    }

    @Test
    void testSerializeShort() throws Exception {
        UserShortDto userDto = new UserShortDto(1, "Name1");

        JsonContent<UserShortDto> result = jsonShort.write(userDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(userDto.getId());
    }
}
