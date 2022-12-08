package ru.practicum.explore.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore.location.dto.LocationDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {

    @NotNull
    @NotBlank
    @Size(max = 2000)
    @Size(min = 20)
    String annotation;
    @NotNull
    @NotBlank
    @Size(max = 120)
    @Size(min = 3)
    String title;
    @NotNull
    @NotBlank
    @Size(max = 7000)
    @Size(min = 20)
    String description;
    @NotNull
    @NotBlank
    String eventDate;
    @NotNull
    Integer category;
    @NotNull
    LocationDto location;
    @NotNull
    Boolean paid;
    @NotNull
    Integer participantLimit;
    @NotNull
    Boolean requestModeration;

}

