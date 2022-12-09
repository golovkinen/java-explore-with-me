package ru.practicum.explore.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ViewStatDto {

    String app;

    String uri;

    Integer hits;
}
