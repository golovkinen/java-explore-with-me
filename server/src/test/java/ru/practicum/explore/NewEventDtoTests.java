package ru.practicum.explore;

import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
public class NewEventDtoTests {
/*    @Autowired
    private JacksonTester<ItemInfoDto> json;

    @Test
    void testSerialize() throws Exception {
        ItemInfoDto itemDto = new ItemInfoDto(1, "Item1", "Desc1", true, new ItemInfoDto.BookingInfoForItemDto(1, 2), new ItemInfoDto.BookingInfoForItemDto(3, 4), new ArrayList<>(), null);

        JsonContent<ItemInfoDto> result = json.write(itemDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(itemDto.getId());
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(itemDto.getName());
        assertThat(result).extractingJsonPathBooleanValue("$.available").isEqualTo(itemDto.getAvailable());
    }

 */
}
