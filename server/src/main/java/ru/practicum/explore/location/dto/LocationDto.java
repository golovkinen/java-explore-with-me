package ru.practicum.explore.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

        @NotNull
        @Min(value = -90)
        @Max(value = 90)
        private Double lat;

        @NotNull
        @Min(value = -180)
        @Max(value = 180)
        private Double lon;
}
