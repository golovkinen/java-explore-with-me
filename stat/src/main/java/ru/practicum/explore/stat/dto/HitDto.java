package ru.practicum.explore.stat.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HitDto {

    String app;

    String uri;

    String ip;

    String timestamp;
}
