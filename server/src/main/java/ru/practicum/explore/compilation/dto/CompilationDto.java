package ru.practicum.explore.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore.event.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDto {

    Integer id;
    List<EventShortDto> events;
    Boolean pinned;
    String title;
}
