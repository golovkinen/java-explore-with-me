package ru.practicum.explore;

import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
public class RequestDtoTests {
/*    @Autowired
    private JacksonTester<RequestDto> json;

    @Test
    void testSerialize() throws Exception {

        LocalDateTime start = LocalDateTime.of(2022, 10, 1, 10, 00, 00);

        RequestDto requestDto = new RequestDto(1, "Desc", start, 2,
                Collections.singletonList(new RequestDto.ItemsForRequestDto(1, "Item", "Desc Item", true, 1)));

        JsonContent<RequestDto> result = json.write(requestDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(requestDto.getId());
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo("2022-10-01T10:00:00");

    }

 */
}
