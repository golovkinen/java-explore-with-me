package ru.practicum.explore.comment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.comment.dto.CommentFullDto;
import ru.practicum.explore.comment.dto.CommentInfoDto;
import ru.practicum.explore.comment.dto.CommentModerateDto;
import ru.practicum.explore.comment.dto.NewCommentDto;
import ru.practicum.explore.comment.enums.CommentState;
import ru.practicum.explore.comment.service.ICommentService;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Validated
public class CommentController {
    private final ICommentService iCommentService;

    public CommentController(ICommentService iCommentService) {
        this.iCommentService = iCommentService;
    }

    @GetMapping(value = "/comments")
    public List<CommentInfoDto> getAllCommentsPublic (@RequestParam(name = "events", required = false) Optional<List<Integer>> eventIds,
                                                     @NotNull @RequestParam(name = "text", required = false) String text,
                                                     @FutureOrPresent @RequestParam(name = "rangeStart", required = false) Optional<LocalDateTime> rangeFrom,
                                                     @Future @RequestParam(name = "rangeEnd", required = false) Optional<LocalDateTime> rangeEnd,
                                             @RequestParam(name = "onlyUseful", defaultValue = "false") Boolean onlyUseful,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") int size) {
        return iCommentService.getAllCommentsPublic(eventIds.orElse(Collections.emptyList()), text,  rangeFrom, rangeEnd, onlyUseful, from, size);
    }

    @GetMapping(value = "/users/{userId}/comments")
    public List<CommentFullDto> getAllUserComments (@Positive @PathVariable(name = "userId") int userId,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") int size) {
        return iCommentService.getAllUserComments(userId, from, size);
    }

    @GetMapping(value = "/admin/comments")
    public List<CommentFullDto> getAllCommentsAdmin (@RequestParam(name = "users", required = false) Optional<List<Integer>> ids,
                                                     @RequestParam(name = "events", required = false) Optional<List<Integer>> eventIds,
                                           @RequestParam(name = "states", required = false) Optional<List<CommentState>> states,
                                           @FutureOrPresent @RequestParam(name = "rangeStart", required = false) Optional<LocalDateTime> rangeFrom,
                                           @Future @RequestParam(name = "rangeEnd", required = false) Optional<LocalDateTime> rangeEnd,
                                           @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") int size) {
        return iCommentService.getAllCommentsAdmin(ids.orElse(Collections.emptyList()), eventIds.orElse(Collections.emptyList()), states.orElse(Collections.emptyList()),
                rangeFrom, rangeEnd, from, size);
    }

    @PostMapping(value = "/users/{userId}/events/{eventId}/comment")
    public CommentInfoDto createComment(@RequestBody NewCommentDto newCommentDto,
                                        @Positive @PathVariable(name = "userId") int userId,
                                        @Positive @PathVariable(name = "eventId") int eventId) {
        return iCommentService.createComment(newCommentDto, eventId, userId);
    }

    @PatchMapping(value = "/admin/comments/{commentId}/publish")
    public CommentModerateDto publishCommentAdmin(@Positive @PathVariable int commentId) {

        return iCommentService.publishCommentAdmin(commentId);
    }

    @PatchMapping(value = "/admin/comments/{commentId}/reject")
    public CommentModerateDto rejectCommentAdmin(@Positive @PathVariable int commentId) {

        return iCommentService.rejectCommentAdmin(commentId);
    }

    @PatchMapping(value = "/users/{userId}/comment")
    public CommentFullDto updateCommentPrivate(@RequestBody NewCommentDto newCommentDto,
                                               @Positive @PathVariable(name = "userId") int userId,
                                               @Positive @RequestParam(name = "commentId") int commentId) {
        return iCommentService.updateCommentPrivate(newCommentDto, commentId, userId);
    }

    @DeleteMapping(value = "/users/{userId}/comment")
    public HttpStatus deleteCommentPrivate(@Positive @PathVariable(name = "userId") int userId,
                                           @Positive @RequestParam(name = "commentId") int commentId) {
        return iCommentService.deleteCommentPrivate(commentId, userId);
    }

    @PatchMapping(value = "/users/{userId}/comment/{commentId}")
    public HttpStatus setCommentUsefulOrNot(@Positive @PathVariable(name = "userId") int userId,
                                           @Positive @PathVariable(name = "commentId") int commentId,
                                           @NotNull @RequestParam(name = "useful") Boolean useful) {
        return iCommentService.setCommentUsefulOrNot(userId, commentId, useful);
    }

}
