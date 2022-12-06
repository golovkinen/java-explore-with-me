package ru.practicum.explore.stat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HitDto {

    private String app;

    private String uri;

    private String ip;

    private String timestamp;
}
