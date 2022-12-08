package ru.practicum.explore.location.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewLocationDto {

    @NotNull
    @NotBlank
    @Size(max = 200)
    String title;

    @NotNull
    @NotBlank
    @Min(value = -90)
    @Max(value = 90)
    Double lat;

    @Min(value = -180)
    @Max(value = 180)
    @NotNull
    @NotBlank
    Double lon;
}
