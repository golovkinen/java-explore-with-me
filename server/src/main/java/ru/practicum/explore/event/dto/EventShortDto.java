package ru.practicum.explore.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.user.dto.UserShortDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventShortDto {

    private Integer id;
    private String annotation;
    private String title;
    private String eventDate;
    private CategoryDto category;
    private UserShortDto initiator;
    private Boolean paid;
    private Integer views;
    private Integer confirmedRequests;

}
