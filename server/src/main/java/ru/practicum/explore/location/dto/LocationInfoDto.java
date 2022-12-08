package ru.practicum.explore.location.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationInfoDto {

    Integer id;
    String title;
    Double lat;
    Double lon;
    String address;
}
