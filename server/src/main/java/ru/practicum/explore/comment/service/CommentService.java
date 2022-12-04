package ru.practicum.explore.comment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.explore.comment.dto.CommentFullDto;
import ru.practicum.explore.comment.dto.CommentInfoDto;
import ru.practicum.explore.comment.dto.CommentModerateDto;
import ru.practicum.explore.comment.dto.NewCommentDto;
import ru.practicum.explore.comment.enums.CommentState;
import ru.practicum.explore.comment.mapper.CommentMapper;
import ru.practicum.explore.comment.model.Comment;
import ru.practicum.explore.comment.model.UsefulKey;
import ru.practicum.explore.comment.model.Usefulness;
import ru.practicum.explore.comment.repository.ICommentRepository;
import ru.practicum.explore.comment.repository.IUsefulnessRepository;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.service.IEventService;
import ru.practicum.explore.exceptionhandler.BadRequestException;
import ru.practicum.explore.exceptionhandler.ForbiddenException;
import ru.practicum.explore.exceptionhandler.NotFoundException;
import ru.practicum.explore.exceptionhandler.WrongStateException;
import ru.practicum.explore.user.model.User;
import ru.practicum.explore.user.service.IUserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService implements ICommentService {
    private final ICommentRepository iCommentRepository;
    private final IUserService iUserService;

    private final IEventService iEventService;

    private final IUsefulnessRepository iUsefulnessRepository;

    public CommentService(ICommentRepository iCommentRepository, IUserService iUserService, IEventService iEventService, IUsefulnessRepository iUsefulnessRepository) {
        this.iCommentRepository = iCommentRepository;
        this.iUserService = iUserService;
        this.iEventService = iEventService;
        this.iUsefulnessRepository = iUsefulnessRepository;
    }

    @Override
    public List<CommentFullDto> getAllCommentsAdmin(List<Integer> ids, List<Integer> events, List<CommentState> commentStates,
                                             Optional<LocalDateTime> rangeFrom, Optional<LocalDateTime> rangeEnd,
                                                    int from, int size) {

        Pageable pageable = PageRequest.of(from / size, size);

        List<Comment> commentList = Collections.emptyList();

        if(!commentStates.isEmpty()) {
            if(commentStates.stream().filter(f -> Arrays.stream(CommentState.class.getEnumConstants()).anyMatch(e -> e.name().equals(f)))
                    .count() != commentStates.size()) {
                log.error("WrongStateException: {}", "readAllUserBookings - Unknown state: UNSUPPORTED_STATUS");
                throw new WrongStateException("Unknown state: UNSUPPORTED_STATUS");
            }
        }

        //все комментарии по ИД
        if(!ids.isEmpty()) {
            commentList = iCommentRepository.findAllByIdIn(ids, pageable).getContent();
        }

        if(events.isEmpty() && rangeFrom.isEmpty() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAll(pageable).getContent();
        }

        //все комментарии не позже даты
        if(events.isEmpty() && commentStates.isEmpty() &&
                rangeFrom.isEmpty() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByCreatedBefore(rangeEnd.get(), pageable).getContent();
        }

        //все комментарии начиная с даты
        if(events.isEmpty() && commentStates.isEmpty() &&
                rangeFrom.isPresent() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByCreatedAfter(rangeFrom.get(), pageable).getContent();
        }

        //все комментарии в промежутке
        if(events.isEmpty() && commentStates.isEmpty() &&
                rangeFrom.isPresent() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByCreatedBetween(rangeFrom.get(), rangeEnd.get(), pageable).getContent();
        }

        //все комментарии в событиях
        if(!events.isEmpty() && commentStates.isEmpty() &&
                rangeFrom.isEmpty() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByEventIdIn(events, pageable).getContent();
        }

        //все комментарии в событиях не позже даты
        if(!events.isEmpty() && commentStates.isEmpty() &&
                rangeFrom.isEmpty() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByEventIdInAndCreatedBefore(events, rangeEnd.get(), pageable).getContent();
        }

        //все комментарии в событиях начиная с даты
        if(!events.isEmpty() && commentStates.isEmpty() &&
                rangeFrom.isPresent() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByEventIdInAndCreatedAfter(events, rangeFrom.get(), pageable).getContent();
        }

        //все комментарии в событиях в промежутке
        if(!events.isEmpty() && commentStates.isEmpty() &&
                rangeFrom.isPresent() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByEventIdInAndCreatedBetween(events, rangeFrom.get(), rangeEnd.get(), pageable).getContent();
        }

        //все комментарии в статусе
        if(events.isEmpty() && !commentStates.isEmpty() &&
                rangeFrom.isEmpty() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByStateIn(commentStates, pageable).getContent();
        }

        //все комментарии в статусе не позже даты
        if(events.isEmpty() && !commentStates.isEmpty() &&
                rangeFrom.isEmpty() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByStateInAndCreatedBefore(commentStates, rangeEnd.get(), pageable).getContent();
        }

        //все комментарии в статусе начиная с даты
        if(events.isEmpty() && !commentStates.isEmpty() &&
                rangeFrom.isPresent() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByStateInAndCreatedAfter(commentStates, rangeFrom.get(), pageable).getContent();
        }

        //все комментарии в статусе в промежутке
        if(events.isEmpty() && !commentStates.isEmpty() &&
                rangeFrom.isPresent() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByStateInAndCreatedBetween(commentStates, rangeFrom.get(), rangeEnd.get(), pageable).getContent();
        }

        //все комментарии в статусе по событиям
        if(!events.isEmpty() && !commentStates.isEmpty() &&
                rangeFrom.isEmpty() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateIn(events, commentStates, pageable).getContent();
        }

        //все комментарии в статусе по событиям не позже даты
        if(!events.isEmpty() && !commentStates.isEmpty() &&
                rangeFrom.isEmpty() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateInAndCreatedBefore(events, commentStates, rangeEnd.get(), pageable).getContent();
        }

        //все комментарии в статусе по событиям начиная с даты
        if(!events.isEmpty() && !commentStates.isEmpty() &&
                rangeFrom.isPresent() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateInAndCreatedAfter(events, commentStates, rangeFrom.get(), pageable).getContent();
        }

        //все комментарии в статусе по событиям в промежутке
        if(!events.isEmpty() && !commentStates.isEmpty() &&
                rangeFrom.isPresent() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateInAndCreatedBetween(events, commentStates, rangeFrom.get(), rangeEnd.get(), pageable).getContent();
        }

        return commentList.stream()
                .map(CommentMapper::toCommentFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentInfoDto> getAllCommentsPublic (List<Integer> eventIds, String text, Optional<LocalDateTime> rangeFrom, Optional<LocalDateTime> rangeEnd,
                                                    Boolean onlyUseful, int from, int size) {

        Pageable pageable = PageRequest.of(from / size, size);

        List<Comment> commentList = Collections.emptyList();

        //возвращать пустой список если оба параметра не указаны
        if(eventIds.isEmpty() && text.isEmpty()) {
            return Collections.emptyList();
        }

        //все комментарии к событиям
        if(!eventIds.isEmpty() && text.isEmpty() && !onlyUseful && rangeFrom.isEmpty() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateIs(eventIds, CommentState.PUBLISHED, pageable).getContent();
        }

        //все комментарии к событиям не позже даты
        if(!eventIds.isEmpty() && text.isEmpty() && !onlyUseful &&
                rangeFrom.isEmpty() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateIsAndCreatedBefore(eventIds, CommentState.PUBLISHED, rangeEnd.get(), pageable).getContent();
        }

        //все комментарии к событиям начиная с даты
        if(!eventIds.isEmpty() && text.isEmpty() && !onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateIsAndCreatedAfter(eventIds,CommentState.PUBLISHED,  rangeFrom.get(), pageable).getContent();
        }

        //все комментарии к событиям в промежутке
        if(!eventIds.isEmpty() && text.isEmpty() && !onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateIsAndCreatedBetween(eventIds,CommentState.PUBLISHED,  rangeFrom.get(), rangeEnd.get(), pageable).getContent();
        }

        //поиск по тексту комментария
        if(eventIds.isEmpty() && !text.isEmpty() && !onlyUseful && rangeFrom.isEmpty() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByTextContainsIgnoreCaseAndStateIs(text, CommentState.PUBLISHED, pageable).getContent();
        }

        //поиск по тексту комментария не позже даты
        if(eventIds.isEmpty() && !text.isEmpty() && !onlyUseful &&
                rangeFrom.isEmpty() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByTextContainsIgnoreCaseAndStateIsAndCreatedBefore(text, CommentState.PUBLISHED, rangeEnd.get(), pageable).getContent();
        }

        //поиск по тексту комментария начиная с даты
        if(eventIds.isEmpty() && !text.isEmpty() && !onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByTextContainsIgnoreCaseAndStateIsAndCreatedAfter(text,CommentState.PUBLISHED,  rangeFrom.get(), pageable).getContent();
        }

        //поиск по тексту комментария в промежутке
        if(eventIds.isEmpty() && !text.isEmpty() && !onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByTextContainsIgnoreCaseAndStateIsAndCreatedBetween(text,CommentState.PUBLISHED,  rangeFrom.get(), rangeEnd.get(), pageable).getContent();
        }

        //поиск по тексту комментария в событиях
        if(!eventIds.isEmpty() && !text.isEmpty() && !onlyUseful && rangeFrom.isEmpty() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsOrderByEventIdAsc(eventIds, text, CommentState.PUBLISHED, pageable).getContent();
        }

        //поиск по тексту комментария в событиях не позже даты
        if(!eventIds.isEmpty() && !text.isEmpty() && !onlyUseful &&
                rangeFrom.isEmpty() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsAndCreatedBeforeOrderByEventIdAsc(eventIds, text, CommentState.PUBLISHED, rangeEnd.get(), pageable).getContent();
        }

        //поиск по тексту комментария в событиях начиная с даты
        if(!eventIds.isEmpty() && !text.isEmpty() && !onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsAndCreatedAfterOrderByEventIdAsc(eventIds, text,CommentState.PUBLISHED,  rangeFrom.get(), pageable).getContent();
        }

        //поиск по тексту комментария в событиях в промежутке
        if(!eventIds.isEmpty() && !text.isEmpty() && !onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsAndCreatedBetweenOrderByEventIdAsc(eventIds, text,CommentState.PUBLISHED,  rangeFrom.get(), rangeEnd.get(), pageable).getContent();
        }

        //все полезные комментарии к событиям
        if(!eventIds.isEmpty() && text.isEmpty() && onlyUseful && rangeFrom.isEmpty() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateIs(eventIds, CommentState.PUBLISHED, pageable).getContent();
        }

        //все полезные комментарии к событиям не позже даты
        if(!eventIds.isEmpty() && text.isEmpty() && onlyUseful &&
                rangeFrom.isEmpty() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateIsAndCreatedBefore(eventIds, CommentState.PUBLISHED, rangeEnd.get(), pageable).getContent();
        }

        //все полезные комментарии к событиям начиная с даты
        if(!eventIds.isEmpty() && text.isEmpty() && onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isEmpty()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateIsAndCreatedAfter(eventIds,CommentState.PUBLISHED,  rangeFrom.get(), pageable).getContent();
        }

        //все полезные комментарии к событиям в промежутке
        if(!eventIds.isEmpty() && text.isEmpty() && onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isPresent()) {
            commentList = iCommentRepository.findAllByEventIdInAndStateIsAndCreatedBetween(eventIds,CommentState.PUBLISHED,  rangeFrom.get(), rangeEnd.get(), pageable).getContent();
        }

        //поиск по тексту комментария полезные
        if(eventIds.isEmpty() && !text.isEmpty() && onlyUseful && rangeFrom.isEmpty() && rangeEnd.isEmpty()) {
            List <Comment> commentsSearch = iCommentRepository.findAllByTextContainsIgnoreCaseAndStateIs(text, CommentState.PUBLISHED, pageable).getContent();
            commentList = commentsSearch.stream().filter(f->isUseful(f.getId())).collect(Collectors.toList());
        }

        //поиск по тексту комментария полезные не позже даты
        if(eventIds.isEmpty() && !text.isEmpty() && onlyUseful &&
                rangeFrom.isEmpty() && rangeEnd.isPresent()) {
            List <Comment> commentsSearch = iCommentRepository.findAllByTextContainsIgnoreCaseAndStateIsAndCreatedBefore(text, CommentState.PUBLISHED, rangeEnd.get(), pageable).getContent();
            commentList = commentsSearch.stream().filter(f-> isUseful(f.getId())).collect(Collectors.toList());

        }

        //поиск по тексту комментария полезные начиная с даты
        if(eventIds.isEmpty() && !text.isEmpty() && onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isEmpty()) {
            List <Comment> commentsSearch =  iCommentRepository.findAllByTextContainsIgnoreCaseAndStateIsAndCreatedAfter(text,CommentState.PUBLISHED,  rangeFrom.get(), pageable).getContent();
            commentList = commentsSearch.stream().filter(f-> isUseful(f.getId())).collect(Collectors.toList());
        }

        //поиск по тексту комментария полезные в промежутке
        if(eventIds.isEmpty() && !text.isEmpty() && onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isPresent()) {
            List <Comment> commentsSearch = iCommentRepository.findAllByTextContainsIgnoreCaseAndStateIsAndCreatedBetween(text,CommentState.PUBLISHED,  rangeFrom.get(), rangeEnd.get(), pageable).getContent();
            commentList = commentsSearch.stream().filter(f-> isUseful(f.getId())).collect(Collectors.toList());
        }

        //поиск по тексту комментария полезные в событиях
        if(!eventIds.isEmpty() && !text.isEmpty() && onlyUseful && rangeFrom.isEmpty() && rangeEnd.isEmpty()) {
            List <Comment> commentsSearch = iCommentRepository.findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsOrderByEventIdAsc(eventIds, text, CommentState.PUBLISHED, pageable).getContent();
            commentList = commentsSearch.stream().filter(f-> isUseful(f.getId())).collect(Collectors.toList());
        }

        //поиск по тексту комментария полезные в событиях не позже даты
        if(!eventIds.isEmpty() && !text.isEmpty() && onlyUseful &&
                rangeFrom.isEmpty() && rangeEnd.isPresent()) {
            List <Comment> commentsSearch = iCommentRepository.findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsAndCreatedBeforeOrderByEventIdAsc(eventIds, text, CommentState.PUBLISHED, rangeEnd.get(), pageable).getContent();
            commentList = commentsSearch.stream().filter(f-> isUseful(f.getId())).collect(Collectors.toList());
        }

        //поиск по тексту комментария полезные в событиях начиная с даты
        if(!eventIds.isEmpty() && !text.isEmpty() && onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isEmpty()) {
            List <Comment> commentsSearch = iCommentRepository.findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsAndCreatedAfterOrderByEventIdAsc(eventIds, text,CommentState.PUBLISHED,  rangeFrom.get(), pageable).getContent();
            commentList = commentsSearch.stream().filter(f-> isUseful(f.getId())).collect(Collectors.toList());
        }

        //поиск по тексту комментария полезные в событиях в промежутке
        if(!eventIds.isEmpty() && !text.isEmpty() && onlyUseful &&
                rangeFrom.isPresent() && rangeEnd.isPresent()) {
            List <Comment> commentsSearch = iCommentRepository.findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsAndCreatedBetweenOrderByEventIdAsc(eventIds, text,CommentState.PUBLISHED,  rangeFrom.get(), rangeEnd.get(), pageable).getContent();
            commentList = commentsSearch.stream().filter(f-> isUseful(f.getId())).collect(Collectors.toList());
        }

        return commentList.stream().map(CommentMapper::toCommentInfoDto).collect(Collectors.toList());
    }

    //показывает все комментарии пользователя, кроме удаленных
    @Override
    public List<CommentFullDto> getAllUserComments (int userId, int from, int size) {

        Optional<User> user = iUserService.getUser(userId);
        if (user.isEmpty()) {
            log.error("NotFoundException: {}", "Пользователь с ИД " + userId + " не найден");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }

        if(user.get().getComments().isEmpty()) {
            return Collections.emptyList();
        }

        Pageable pageable = PageRequest.of(from / size, size);
        return iCommentRepository.findAllByUserIdAndAndStateIsNot(userId, CommentState.DELETED, pageable).stream()
                .map(CommentMapper::toCommentFullDto).collect(Collectors.toList());
    }

    @Override
    public CommentInfoDto createComment(NewCommentDto newCommentDto, Integer eventId, Integer authorId) {
        Optional<User> author = iUserService.getUser(authorId);
        Optional<Event> event = iEventService.getEvent(eventId);
        if (author.isEmpty()) {
            log.error("NotFoundException: {}", "Только зарегистрированные пользователи могут оставлять комментарии");
            throw new NotFoundException("Пользователь с ИД " + authorId + " не найден");
        }

        if (event.isEmpty()) {
            log.error("NotFoundException: {}", "При создании комментария Событие с ИД " + eventId + " не найдено");
            throw new NotFoundException("Событие с ИД " + eventId + " не найдено");
        }

        if (!event.get().getState().equals(CommentState.PUBLISHED)) {
            log.error("BadRequestException: {}", "При создании комментария Событие с ИД " + eventId + " не опубликовано");
            throw new BadRequestException("Событие с ИД " + eventId + " не опубликовано");
        }

        Comment newComment = iCommentRepository.save(CommentMapper.toComment(newCommentDto, author.get(), event.get()));

        event.get().getComments().add(newComment);

        return CommentMapper.toCommentInfoDto(newComment);
    }

    @Override
    public CommentModerateDto publishCommentAdmin(int commentId) {
        Optional<Comment> commentToPublish = iCommentRepository.findById(commentId);

        if (commentToPublish.isEmpty()) {
            log.error("NotFoundException: {}", "Comment с ИД " + commentId + " не найден");
            throw new NotFoundException("Comment с ИД " + commentId + " не найден");
        }

        if(!commentToPublish.get().getState().equals(CommentState.PENDING)) {
            log.error("ForbiddenException: {}", "Публиковать можно только в статусе PENDING");
            throw new ForbiddenException("Only pending comments can be published");
        }

        commentToPublish.get().setState(CommentState.PUBLISHED);

        return CommentMapper.toCommentModerateDto(iCommentRepository.save(commentToPublish.get()));
    }

    @Override
    public CommentModerateDto rejectCommentAdmin(int commentId) {
        Optional<Comment> commentToReject = iCommentRepository.findById(commentId);

        if (commentToReject.isEmpty()) {
            log.error("NotFoundException: {}", "Comment с ИД " + commentId + " не найден");
            throw new NotFoundException("Comment с ИД " + commentId + " не найден");
        }

        if(!commentToReject.get().getState().equals(CommentState.PENDING)) {
            log.error("ForbiddenException: {}", "Отменить можно только в статусе PENDING");
            throw new ForbiddenException("Only pending comments can be rejected");
        }

        commentToReject.get().setState(CommentState.REJECTED);

        return CommentMapper.toCommentModerateDto(iCommentRepository.save(commentToReject.get()));
    }

    @Override
    public CommentFullDto updateCommentPrivate(NewCommentDto newCommentDto, int commentId, int userId) {
        Optional<User> author = iUserService.getUser(userId);
        Optional<Comment> commentToUpdate = iCommentRepository.findById(commentId);
        if (author.isEmpty()) {
            log.error("NotFoundException: {}", "Только зарегистрированные пользователи могут оставлять комментарии");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }

        if (commentToUpdate.isEmpty()) {
            log.error("NotFoundException: {}", "При создании комментария Событие с ИД " + commentId + " не найдено");
            throw new NotFoundException("Коментарий с ИД " + commentId + " не найдено");
        }

        if(commentToUpdate.get().getUser().getId() != userId) {
            log.error("BadRequestException: {}", "Редактировать комментарий может только автор");
            throw new BadRequestException("Редактировать комментарий может только автор");
        }

        if (commentToUpdate.get().getState().equals(CommentState.PENDING)) {
            commentToUpdate.get().setText(newCommentDto.getText());
            return CommentMapper.toCommentFullDto(iCommentRepository.save(commentToUpdate.get()));
        }

        if (commentToUpdate.get().getState().equals(CommentState.PUBLISHED)) {
            commentToUpdate.get().setText(newCommentDto.getText());
            commentToUpdate.get().setState(CommentState.PENDING);
            iUsefulnessRepository.deleteAllByIdCommentId(commentId);
            return CommentMapper.toCommentFullDto(iCommentRepository.save(commentToUpdate.get()));
        }

        log.error("BadRequestException: {}", "Comment must be in PENDING or PUBLISHED state to be updated");
        throw new BadRequestException("Comment must be in PENDING or PUBLISHED state to be updated");
    }

    @Override
    public HttpStatus deleteCommentPrivate(int userId, int commentId) {
        Optional<User> author = iUserService.getUser(userId);
        Optional<Comment> commentToDelete = iCommentRepository.findById(commentId);
        if (author.isEmpty()) {
            log.error("NotFoundException: {}", "Только зарегистрированные пользователи могут оставлять комментарии");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }

        if (commentToDelete.isEmpty()) {
            log.error("NotFoundException: {}", "Коментарий с ИД " + commentId + " не найден");
            throw new NotFoundException("Коментарий с ИД " + commentId + " не найден");
        }

        if(commentToDelete.get().getUser().getId() != userId) {
            log.error("BadRequestException: {}", "Удалить комментарий может только автор");
            throw new BadRequestException("Удалить комментарий может только автор");
        }

        if (commentToDelete.get().getState().equals(CommentState.DELETED)) {
            log.error("BadRequestException: {}", "Коментарий с ИД " + commentId + " уже удален");
            throw new BadRequestException("Коментарий с ИД " + commentId + " уже удален");
        }
        commentToDelete.get().setState(CommentState.DELETED);
        iUsefulnessRepository.deleteAllByIdCommentId(commentId);
        iCommentRepository.save(commentToDelete.get());
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus setCommentUsefulOrNot(int userId, int commentId, Boolean useful) {
        Optional<User> user = iUserService.getUser(userId);
        Optional<Comment> commentToRate = iCommentRepository.findById(commentId);
        if (user.isEmpty()) {
            log.error("NotFoundException: {}", "Только зарегистрированные пользователи могут оставлять комментарии");
            throw new NotFoundException("Пользователь с ИД " + userId + " не найден");
        }

        if (commentToRate.isEmpty()) {
            log.error("NotFoundException: {}", "Коментарий с ИД " + commentId + " не найден");
            throw new NotFoundException("Коментарий с ИД " + commentId + " не найден");
        }

        if(commentToRate.get().getUser().getId() == userId) {
            log.error("BadRequestException: {}", "Автор не может оценить свой комментарий");
            throw new BadRequestException("Автор не может оценить свой комментарий");
        }

        if (!commentToRate.get().getState().equals(CommentState.PUBLISHED)) {
            log.error("BadRequestException: {}", "Оценить можно только опубликованный комментарий");
            throw new BadRequestException("Оценить можно только опубликованный комментарий");
        }

        Optional<Usefulness> previousVote = iUsefulnessRepository.findAllById_UserIdAndId_CommentId(userId, commentId);

        if(previousVote.isPresent()) {
            previousVote.get().setUseful(useful);
            iUsefulnessRepository.save(previousVote.get());
        } else {
            Usefulness opinion = new Usefulness(new UsefulKey(userId, commentId), useful, null, null);
            iUsefulnessRepository.save(opinion);
        }

        return HttpStatus.OK;
    }

    private Boolean isUseful (int commentId) {

        return iUsefulnessRepository.getCommentUsefulCount(commentId) > iUsefulnessRepository.getCommentNotUsefulCount(commentId);

    }
}
