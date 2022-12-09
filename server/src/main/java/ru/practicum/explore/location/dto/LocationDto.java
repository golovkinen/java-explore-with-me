package ru.practicum.explore.location.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {

    @NotNull
    @Min(value = -90)
    @Max(value = 90)
    Double lat;

    @NotNull
    @Min(value = -180)
    @Max(value = 180)
    Double lon;
}
