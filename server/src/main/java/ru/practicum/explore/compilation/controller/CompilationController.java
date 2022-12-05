package ru.practicum.explore.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.compilation.dto.CompilationDto;
import ru.practicum.explore.compilation.dto.NewCompilationDto;
import ru.practicum.explore.compilation.service.ICompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Validated
public class CompilationController {
    private final ICompilationService iCompilationService;

    public CompilationController(ICompilationService iCompilationService) {
        this.iCompilationService = iCompilationService;
    }

    @GetMapping(value = "/compilations")
    public List<CompilationDto> getAllCompilations(@RequestParam(name = "pinned", required = false) Optional<Boolean> pinned,
                                                   @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                                   @Positive @RequestParam(name = "size", defaultValue = "10") int size) {
        return iCompilationService.getAllCompilations(pinned, from, size);
    }

    @GetMapping(value = "/compilations/{compId}")
    public CompilationDto getCompilationById(@Positive @PathVariable(name = "compId") int compId) {
        return iCompilationService.getCompilationById(compId);
    }

    @PostMapping(value = "/admin/compilations")
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {

        return iCompilationService.createCompilation(newCompilationDto);
    }

    @PatchMapping(value = "/admin/compilations/{compId}/events/{eventId}")
    public HttpStatus addEventToCompilation(@Positive @PathVariable(name = "compId") int compId,
                                            @Positive @PathVariable(name = "eventId") int eventId) {

        return iCompilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping(value = "/admin/compilations/{compId}")
    public HttpStatus deleteCompilation(@Positive @PathVariable(name = "compId") int compId) {
        return iCompilationService.deleteCompilation(compId);
    }

    @DeleteMapping(value = "/admin/compilations/{compId}/events/{eventId}")
    public HttpStatus deleteEventFromCompilation(@Positive @PathVariable(name = "compId") int compId,
                                                 @Positive @PathVariable(name = "eventId") int eventId) {
        return iCompilationService.deleteEventFromCompilation(compId, eventId);
    }

    @DeleteMapping(value = "/admin/compilations/{compId}/pin")
    public HttpStatus unpinCompilation(@Positive @PathVariable(name = "compId") int compId) {
        return iCompilationService.unpinCompilation(compId);
    }

    @PatchMapping(value = "/admin/compilations/{compId}/pin")
    public HttpStatus pinCompilation(@Positive @PathVariable(name = "compId") int compId) {
        return iCompilationService.pinCompilation(compId);
    }
}
