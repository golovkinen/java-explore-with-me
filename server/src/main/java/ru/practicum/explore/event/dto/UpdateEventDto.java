package ru.practicum.explore.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateEventDto {

    private String annotation;
    private String title;
    private String description;
    private String eventDate;
    private Integer category;
    private Boolean paid;
    private Integer participantLimit;
    private Integer eventId;

}

