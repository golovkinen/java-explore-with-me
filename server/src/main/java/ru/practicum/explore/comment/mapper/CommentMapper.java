package ru.practicum.explore.comment.mapper;

import ru.practicum.explore.comment.dto.CommentFullDto;
import ru.practicum.explore.comment.dto.CommentInfoDto;
import ru.practicum.explore.comment.dto.CommentModerateDto;
import ru.practicum.explore.comment.dto.NewCommentDto;
import ru.practicum.explore.comment.enums.CommentState;
import ru.practicum.explore.comment.model.Comment;
import ru.practicum.explore.event.dto.EventInfoDto;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.user.dto.UserShortDto;
import ru.practicum.explore.user.model.User;

public class CommentMapper {
    public static Comment toComment(NewCommentDto commentDto, User author, Event event) {
        return new Comment(null, commentDto.getText(), null, CommentState.PENDING, 0, 0,
                event, author);
    }

    public static CommentInfoDto toCommentInfoDto(Comment comment) {
        return new CommentInfoDto(comment.getId(), comment.getText(), comment.getUser().getName(),
                comment.getCreated(), comment.getUseful(), comment.getNotUseful());
    }

    public static CommentFullDto toCommentFullDto(Comment comment) {
        return new CommentFullDto(comment.getId(), comment.getText(), new UserShortDto(comment.getUser().getId(), comment.getUser().getName()), new EventInfoDto(comment.getEvent().getId(), comment.getEvent().getTitle()),
                comment.getCreated(), comment.getState(), comment.getUseful(), comment.getNotUseful());
    }

    public static CommentModerateDto toCommentModerateDto(Comment comment) {
        return new CommentModerateDto(comment.getId(), comment.getText(), new UserShortDto(comment.getUser().getId(), comment.getUser().getName()),
                comment.getCreated(), comment.getState());
    }
}
