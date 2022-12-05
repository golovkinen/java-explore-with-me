package ru.practicum.explore.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewLocationDto {

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String title;

    @NotNull
    @NotBlank
    @Min(value = -90)
    @Max(value = 90)
    private Double lat;

    @Min(value = -180)
    @Max(value = 180)
    @NotNull
    @NotBlank
    private Double lon;
}
