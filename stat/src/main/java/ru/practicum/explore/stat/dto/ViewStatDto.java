package ru.practicum.explore.stat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViewStatDto {

    private String app;

    private String uri;

    private Integer hits;
}
