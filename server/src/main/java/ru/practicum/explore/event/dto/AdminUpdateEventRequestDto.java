package ru.practicum.explore.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explore.location.dto.LocationDto;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminUpdateEventRequestDto {

    private String annotation;
    private String title;
    private String description;
    private String eventDate;
    private Integer category;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;

}

