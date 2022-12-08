package ru.practicum.explore.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.user.dto.UserShortDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullDto {

    Integer id;
    String annotation;
    String title;
    String description;
    String eventDate;
    CategoryDto category;
    UserShortDto initiator;
    LocationDto location;
    Boolean paid;
    String createdOn;
    Integer participantLimit;
    String publishedOn;
    Boolean requestModeration;
    String state;
    Integer views;
    Integer confirmedRequests;

}
