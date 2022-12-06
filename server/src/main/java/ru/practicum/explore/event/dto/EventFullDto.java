package ru.practicum.explore.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.user.dto.UserShortDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventFullDto {

    private Integer id;
    private String annotation;
    private String title;
    private String description;
    private String eventDate;
    private CategoryDto category;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private String createdOn;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private String state;
    private Integer views;
    private Integer confirmedRequests;

}
