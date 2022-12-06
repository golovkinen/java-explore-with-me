package ru.practicum.explore.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {

    @NotNull
    private List<Integer> events;
    @NotNull
    private Boolean pinned;
    @NotNull
    @NotBlank
    private String title;
}
