package ru.practicum.explore.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventDto {

    String annotation;
    String title;
    String description;
    String eventDate;
    Integer category;
    Boolean paid;
    Integer participantLimit;
    Integer eventId;

}

