package ru.practicum.explore.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore.location.dto.LocationDto;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminUpdateEventRequestDto {

    String annotation;
    String title;
    String description;
    String eventDate;
    Integer category;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;

}

