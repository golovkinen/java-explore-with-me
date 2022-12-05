package ru.practicum.explore.comment.service;

import org.springframework.http.HttpStatus;
import ru.practicum.explore.comment.dto.CommentFullDto;
import ru.practicum.explore.comment.dto.CommentInfoDto;
import ru.practicum.explore.comment.dto.CommentModerateDto;
import ru.practicum.explore.comment.dto.NewCommentDto;
import ru.practicum.explore.comment.enums.CommentState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ICommentService {

    CommentInfoDto createComment(NewCommentDto newCommentDto, Integer eventId, Integer userId);

    CommentModerateDto publishCommentAdmin(int commentId);

    CommentModerateDto rejectCommentAdmin(int commentId);

    List<CommentFullDto> getAllCommentsAdmin(List<Integer> ids, List<Integer> events, List<CommentState> commentStates,
                                             Optional<LocalDateTime> rangeFrom, Optional<LocalDateTime> rangeEnd,
                                             int from, int size);

    List<CommentInfoDto> getAllCommentsPublic(List<Integer> eventIds, String text, Optional<LocalDateTime> rangeFrom, Optional<LocalDateTime> rangeEnd,
                                              Boolean onlyUseful, int from, int size);

    List<CommentFullDto> getAllUserComments(int userId, int from, int size);

    CommentFullDto updateCommentPrivate(NewCommentDto newCommentDto, int commentId, int userId);

    HttpStatus deleteCommentPrivate(int userId, int commentId);

    HttpStatus setCommentUsefulOrNot(int userId, int commentId, Boolean useful);
}
