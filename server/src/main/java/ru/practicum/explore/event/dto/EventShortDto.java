package ru.practicum.explore.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.user.dto.UserShortDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {

    Integer id;
    String annotation;
    String title;
    String eventDate;
    CategoryDto category;
    UserShortDto initiator;
    Boolean paid;
    Integer views;
    Integer confirmedRequests;

}
