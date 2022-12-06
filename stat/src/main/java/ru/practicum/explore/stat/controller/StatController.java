package ru.practicum.explore.stat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.stat.dto.HitDto;
import ru.practicum.explore.stat.dto.ViewStatDto;
import ru.practicum.explore.stat.service.IStatService;

import java.util.List;

@RestController
@Slf4j
public class StatController {
    private final IStatService iStatService;

    public StatController(IStatService iStatService) {
        this.iStatService = iStatService;
    }

    @GetMapping(value = "/stats")
    public List<ViewStatDto> getStat(@RequestParam(name = "start") String start,
                                     @RequestParam(name = "end") String end,
                                     @RequestParam(name = "uris", required = false) String uris,
                                     @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        return iStatService.getStat(start, end, uris, unique);
    }


    @PostMapping(value = "/hit")
    public HttpStatus create(@RequestBody HitDto hitDto) {

        return iStatService.create(hitDto);
    }

}
