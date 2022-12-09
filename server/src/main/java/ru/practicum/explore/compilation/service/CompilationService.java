package ru.practicum.explore.compilation.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.explore.compilation.dto.CompilationDto;
import ru.practicum.explore.compilation.dto.NewCompilationDto;
import ru.practicum.explore.compilation.mapper.CompilationMapper;
import ru.practicum.explore.compilation.model.Compilation;
import ru.practicum.explore.compilation.repository.ICompilationRepository;
import ru.practicum.explore.event.enums.State;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.service.IEventService;
import ru.practicum.explore.exceptionhandler.BadRequestException;
import ru.practicum.explore.exceptionhandler.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CompilationService implements ICompilationService {

    ICompilationRepository iCompilationRepository;
    IEventService iEventService;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {

        Compilation newCompilation = CompilationMapper.toCompilation(newCompilationDto);

        List<Event> events = iEventService.getEventsByIds(newCompilationDto.getEvents());

        if (events.size() < newCompilationDto.getEvents().size()) {
            List<Integer> difference = newCompilationDto.getEvents().stream().filter(f -> !events.contains(f))
                    .collect(Collectors.toList());

            log.error("NotFoundException: {}", "При создании компиляции, События с ИД " + difference + " не найдены");
            throw new NotFoundException("События с ИД " + difference + " не найдены");
        }

        newCompilation.getEvents().addAll(events);

        Compilation savedCompilation = iCompilationRepository.save(newCompilation);

        log.info("Compilation create: {}", savedCompilation);

        events.stream().forEach(e -> e.setCompilation(savedCompilation));

        events.stream().forEach(iEventService::saveEvent);

        return CompilationMapper.toCompilationDto(savedCompilation);
    }

    @Override
    public List<CompilationDto> getAllCompilations(Optional<Boolean> pinned, int from, int size) {

        Pageable pageable = PageRequest.of(from / size, size);

        if (pinned.isEmpty()) {
            return iCompilationRepository.findAll(pageable).stream()
                    .map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
        }

        return iCompilationRepository.findAllByPinnedIsOrderByIdAsc(pinned.get(), pageable).stream()
                .map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(int compId) {
        Optional<Compilation> compilation = iCompilationRepository.findById(compId);

        if (compilation.isEmpty()) {
            log.error("NotFoundException: {}", "Компиляция с ИД " + compId + " не найдена");
            throw new NotFoundException("Компиляция с ИД " + compId + " не найдена");
        }

        return CompilationMapper.toCompilationDto(compilation.get());
    }

    @Override
    public HttpStatus addEventToCompilation(int compId, int eventId) {
        Optional<Compilation> compilation = iCompilationRepository.findById(compId);

        if (compilation.isEmpty()) {
            log.error("NotFoundException: {}", "Компиляция с ИД " + compId + " не найдена");
            throw new NotFoundException("Компиляция с ИД " + compId + " не найдена");
        }

        Optional<Event> eventToAdd = iEventService.getEvent(eventId);

        if (eventToAdd.isEmpty()) {
            log.error("NotFoundException: {}", "Событие с ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие с ИД " + eventId + " не найдено");
        }

        if (!eventToAdd.get().getState().equals(State.PUBLISHED)) {
            log.error("BadRequestException: {}", "Событие с ИД " + eventId + " не опубликовано");
            throw new BadRequestException("Событие с ИД " + eventId + " не опубликовано");
        }

        if (eventToAdd.get().getEventDate().isBefore(LocalDateTime.now())) {
            log.error("BadRequestException: {}", "Событие с ИД " + eventId + " уже завершено");
            throw new BadRequestException("Событие с ИД " + eventId + " уже завершено");
        }

        compilation.get().getEvents().add(eventToAdd.get());
        Compilation savedCompilation = iCompilationRepository.save(compilation.get());
        log.info("Compilation AddEventToCompilation: {}", savedCompilation);
        eventToAdd.get().setCompilation(savedCompilation);
        Event savedEvent = iEventService.saveEvent(eventToAdd.get());
        log.info("Compilation AddEventToCompilation: {}", savedEvent);

        return HttpStatus.OK;
    }

    @Override
    public HttpStatus deleteCompilation(int compId) {
        Optional<Compilation> compilation = iCompilationRepository.findById(compId);

        if (compilation.isEmpty()) {
            log.error("NotFoundException: {}", "Компиляция с ИД " + compId + " не найдена");
            throw new NotFoundException("Компиляция с ИД " + compId + " не найдена");
        }

        iCompilationRepository.deleteById(compId);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus deleteEventFromCompilation(int compId, int eventId) {

        Optional<Compilation> compilation = iCompilationRepository.findById(compId);

        if (compilation.isEmpty()) {
            log.error("NotFoundException: {}", "Компиляция с ИД " + compId + " не найдена");
            throw new NotFoundException("Компиляция с ИД " + compId + " не найдена");
        }

        Optional<Event> eventToRemove = iEventService.getEvent(eventId);

        if (eventToRemove.isEmpty()) {
            log.error("NotFoundException: {}", "Событие с ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие с ИД " + eventId + " не найдено");
        }

        if (!compilation.get().getEvents().contains(eventToRemove.get())) {
            log.error("NotFoundException: {}", "Событие с ИД " + eventId + " не входит в компиляцию с ИД " + compId);
            throw new NotFoundException("Событие с ИД " + eventId + " не входит в компиляцию с ИД " + compId);
        }
        compilation.get().getEvents().remove(eventToRemove.get());
        iCompilationRepository.save(compilation.get());
        eventToRemove.get().setCompilation(null);
        iEventService.saveEvent(eventToRemove.get());
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus unpinCompilation(int compId) {

        Optional<Compilation> compilation = iCompilationRepository.findById(compId);

        if (compilation.isEmpty()) {
            log.error("NotFoundException: {}", "Компиляция с ИД " + compId + " не найдена");
            throw new NotFoundException("Компиляция с ИД " + compId + " не найдена");
        }

        if (compilation.get().getPinned().equals(false)) {
            log.error("BadRequestException: {}", "Компиляция с ИД " + compId + " уже откреплена");
            throw new BadRequestException("Компиляция с ИД " + compId + " уже откреплена");
        }

        compilation.get().setPinned(false);
        iCompilationRepository.save(compilation.get());
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus pinCompilation(int compId) {
        Optional<Compilation> compilation = iCompilationRepository.findById(compId);

        if (compilation.isEmpty()) {
            log.error("NotFoundException: {}", "Компиляция с ИД " + compId + " не найдена");
            throw new NotFoundException("Компиляция с ИД " + compId + " не найдена");
        }

        if (compilation.get().getPinned().equals(true)) {
            log.error("BadRequestException: {}", "Компиляция с ИД " + compId + " уже прикреплена");
            throw new BadRequestException("Компиляция с ИД " + compId + " уже прикреплена");
        }

        compilation.get().setPinned(true);
        iCompilationRepository.save(compilation.get());
        return HttpStatus.OK;
    }


}
