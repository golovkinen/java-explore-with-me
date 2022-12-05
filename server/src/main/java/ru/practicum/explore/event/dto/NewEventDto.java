package ru.practicum.explore.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explore.location.dto.LocationDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewEventDto {

    @NotNull
    @NotBlank
    @Size(max = 2000)
    @Size(min = 20)
    private String annotation;
    @NotNull
    @NotBlank
    @Size(max = 120)
    @Size(min = 3)
    private String title;
    @NotNull
    @NotBlank
    @Size(max = 7000)
    @Size(min = 20)
    private String description;
    @NotNull
    @NotBlank
    private String eventDate;
    @NotNull
    private Integer category;
    @NotNull
    private LocationDto location;
    @NotNull
    private Boolean paid;
    @NotNull
    private Integer participantLimit;
    @NotNull
    private Boolean requestModeration;

}

