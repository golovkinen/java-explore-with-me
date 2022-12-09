package ru.practicum.explore.stat.service;

import org.springframework.http.HttpStatus;
import ru.practicum.explore.stat.dto.HitDto;
import ru.practicum.explore.stat.dto.ViewStatDto;

import java.util.List;

public interface IStatService {
    HttpStatus create(HitDto hitDto);

    List<ViewStatDto> getStat(String start, String end, String uris, Boolean unique);
}
