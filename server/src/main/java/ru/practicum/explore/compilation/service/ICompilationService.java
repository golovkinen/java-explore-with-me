package ru.practicum.explore.compilation.service;

import org.springframework.http.HttpStatus;
import ru.practicum.explore.compilation.dto.CompilationDto;
import ru.practicum.explore.compilation.dto.NewCompilationDto;

import java.util.List;
import java.util.Optional;

public interface ICompilationService {
    CompilationDto createCompilation(NewCompilationDto newCompilationDto);
    List<CompilationDto> getAllCompilations ( Optional<Boolean> pinned, int from, int size);
    CompilationDto getCompilationById ( int compId);
    HttpStatus addEventToCompilation(int compId, int eventId);
    HttpStatus deleteCompilation( int compId);
    HttpStatus deleteEventFromCompilation( int compId, int eventId);
    HttpStatus unpinCompilation( int compId);
    HttpStatus pinCompilation( int compId);
}
