package ru.practicum.explore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.event.dto.*;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.user.dto.UserShortDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class NewEventDtoTests {
    @Autowired
    private JacksonTester<AdminUpdateEventRequestDto> adminUpdateEventRequestDtoJacksonTester;

    @Autowired
    private JacksonTester<EventFullDto> eventFullDtoJacksonTester;

    @Autowired
    private JacksonTester<EventInfoDto> eventInfoDtoJacksonTester;

    @Autowired
    private JacksonTester<EventShortDto> eventShortDtoJacksonTester;

    @Autowired
    private JacksonTester<HitDto> hitDtoJacksonTester;

    @Autowired
    private JacksonTester<NewEventDto> newEventDtoJacksonTester;

    @Autowired
    private JacksonTester<UpdateEventDto> updateEventDtoJacksonTester;

    @Autowired
    private JacksonTester<ViewStatDto> viewStatDtoJacksonTester;


    @Test
    void testSerializeAdminUpdateDto() throws Exception {
        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        AdminUpdateEventRequestDto adminUpdateEventRequestDto = new AdminUpdateEventRequestDto("Annotation1mustBe20Chars", "Title1mustBe20CharsMin", "Description1mustBe20Chars", "2023-11-30 01:47:40", 1, locationDto, false, 128, true);

        JsonContent<AdminUpdateEventRequestDto> result = adminUpdateEventRequestDtoJacksonTester.write(adminUpdateEventRequestDto);

        assertThat(result).hasJsonPath("$.annotation");
        assertThat(result).extractingJsonPathStringValue("$.annotation").isEqualTo(adminUpdateEventRequestDto.getAnnotation());
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo(adminUpdateEventRequestDto.getTitle());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(adminUpdateEventRequestDto.getDescription());
    }

    @Test
    void testSerializeEventFullDto() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", 1, locationDto, true, 120, false);

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        AdminUpdateEventRequestDto adminUpdateEventRequestDto = new AdminUpdateEventRequestDto("Annotation1mustBe20Chars", "Title1mustBe20CharsMin", "Description1mustBe20Chars", "2023-11-30 01:47:40", 1, locationDto, false, 128, true);

        JsonContent<EventFullDto> result = eventFullDtoJacksonTester.write(eventFullDto);

        assertThat(result).hasJsonPath("$.annotation");
        assertThat(result).extractingJsonPathStringValue("$.annotation").isEqualTo(eventFullDto.getAnnotation());
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo(eventFullDto.getTitle());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(eventFullDto.getDescription());
    }

    @Test
    void testSerializeEventInfoDto() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", 1, locationDto, true, 120, false);

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        EventInfoDto eventInfoDto = new EventInfoDto(1, "title1");

        AdminUpdateEventRequestDto adminUpdateEventRequestDto = new AdminUpdateEventRequestDto("Annotation1mustBe20Chars", "Title1mustBe20CharsMin", "Description1mustBe20Chars", "2023-11-30 01:47:40", 1, locationDto, false, 128, true);

        JsonContent<EventInfoDto> result = eventInfoDtoJacksonTester.write(eventInfoDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(eventInfoDto.getId());
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo(eventInfoDto.getTitle());
    }

    @Test
    void testSerializeEventShortDto() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", 1, locationDto, true, 120, false);

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1mustbe20min", "title1", "2023-11-30 01:47:40", categoryDto, userShortDto, false, 0, 0);

        EventInfoDto eventInfoDto = new EventInfoDto(1, "title1");

        AdminUpdateEventRequestDto adminUpdateEventRequestDto = new AdminUpdateEventRequestDto("Annotation1mustBe20Chars", "Title1mustBe20CharsMin", "Description1mustBe20Chars", "2023-11-30 01:47:40", 1, locationDto, false, 128, true);

        JsonContent<EventShortDto> result = eventShortDtoJacksonTester.write(eventShortDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(eventShortDto.getId());
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo(eventShortDto.getTitle());
    }

    @Test
    void testSerializeNewEventDto() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", 1, locationDto, true, 120, false);

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1mustbe20min", "title1", "2023-11-30 01:47:40", categoryDto, userShortDto, false, 0, 0);

        EventInfoDto eventInfoDto = new EventInfoDto(1, "title1");

        AdminUpdateEventRequestDto adminUpdateEventRequestDto = new AdminUpdateEventRequestDto("Annotation1mustBe20Chars", "Title1mustBe20CharsMin", "Description1mustBe20Chars", "2023-11-30 01:47:40", 1, locationDto, false, 128, true);

        JsonContent<NewEventDto> result = newEventDtoJacksonTester.write(newEventDto);

        assertThat(result).hasJsonPath("$.title");
        assertThat(result).extractingJsonPathStringValue("$.annotation").isEqualTo(newEventDto.getAnnotation());
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo(eventShortDto.getTitle());
    }

    @Test
    void testSerializeUpdateEventDto() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", 1, locationDto, true, 120, false);

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1mustbe20min", "title1", "2023-11-30 01:47:40", categoryDto, userShortDto, false, 0, 0);

        EventInfoDto eventInfoDto = new EventInfoDto(1, "title1");

        AdminUpdateEventRequestDto adminUpdateEventRequestDto = new AdminUpdateEventRequestDto("Annotation1mustBe20Chars", "Title1mustBe20CharsMin", "Description1mustBe20Chars", "2023-11-30 01:47:40", 1, locationDto, false, 128, true);

        UpdateEventDto updateEventDto = new UpdateEventDto("annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", 1, true, 120, 1);


        JsonContent<UpdateEventDto> result = updateEventDtoJacksonTester.write(updateEventDto);

        assertThat(result).hasJsonPath("$.eventId");
        assertThat(result).extractingJsonPathStringValue("$.annotation").isEqualTo(updateEventDto.getAnnotation());
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo(updateEventDto.getTitle());
    }

    @Test
    void testSerializeHitDto() throws Exception {

        LocationDto locationDto = new LocationDto(55.710697, 37.607448);

        NewEventDto newEventDto = new NewEventDto("annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", 1, locationDto, true, 120, false);

        CategoryDto categoryDto = new CategoryDto(1, "category1");

        UserShortDto userShortDto = new UserShortDto(1, "name1");

        EventFullDto eventFullDto = new EventFullDto(1, "annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", categoryDto, userShortDto, locationDto, true, "2022-11-29 21:47:40", 120, null, false, "PENDING", null, null);

        EventShortDto eventShortDto = new EventShortDto(1, "annotation1mustbe20min", "title1", "2023-11-30 01:47:40", categoryDto, userShortDto, false, 0, 0);

        EventInfoDto eventInfoDto = new EventInfoDto(1, "title1");

        AdminUpdateEventRequestDto adminUpdateEventRequestDto = new AdminUpdateEventRequestDto("Annotation1mustBe20Chars", "Title1mustBe20CharsMin", "Description1mustBe20Chars", "2023-11-30 01:47:40", 1, locationDto, false, 128, true);

        UpdateEventDto updateEventDto = new UpdateEventDto("annotation1mustbe20min", "title1", "description1mustbe20min", "2023-11-30 01:47:40", 1, true, 120, 1);

        HitDto hitDto = new HitDto("/events", "/events/2", "0.0.0.1", "2023-11-30 01:47:40");

        JsonContent<HitDto> result = hitDtoJacksonTester.write(hitDto);

        assertThat(result).hasJsonPath("$.ip");
        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo(hitDto.getApp());
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo(hitDto.getUri());
    }
}
