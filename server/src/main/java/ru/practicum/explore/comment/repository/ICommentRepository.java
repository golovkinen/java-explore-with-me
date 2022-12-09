package ru.practicum.explore.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.comment.enums.CommentState;
import ru.practicum.explore.comment.model.Comment;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ICommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findAllByIdIn(Collection<Integer> ids, Pageable pageable);

    Page<Comment> findAllByCreatedBefore(LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByCreatedAfter(LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByCreatedBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Comment> findAllByEventIdIn(Collection<Integer> eventIds, Pageable pageable);

    Page<Comment> findAllByEventIdInAndCreatedBefore(Collection<Integer> eventIds, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByEventIdInAndCreatedAfter(Collection<Integer> eventIds, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByEventIdInAndCreatedBetween(Collection<Integer> eventIds, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Comment> findAllByStateIn(Collection<CommentState> commentStates, Pageable pageable);

    Page<Comment> findAllByStateInAndCreatedBefore(Collection<CommentState> commentStates, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByStateInAndCreatedAfter(Collection<CommentState> commentStates, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByStateInAndCreatedBetween(Collection<CommentState> commentStates, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Comment> findAllByEventIdInAndStateIn(Collection<Integer> eventIds, Collection<CommentState> commentStates, Pageable pageable);

    Page<Comment> findAllByEventIdInAndStateInAndCreatedBefore(Collection<Integer> eventIds, Collection<CommentState> commentStates, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByEventIdInAndStateInAndCreatedAfter(Collection<Integer> eventIds, Collection<CommentState> commentStates, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByEventIdInAndStateInAndCreatedBetween(Collection<Integer> eventIds, Collection<CommentState> commentStates, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Comment> findAllByEventIdInAndStateIs(Collection<Integer> eventIds, CommentState state, Pageable pageable);

    Page<Comment> findAllByEventIdInAndStateIsAndCreatedBefore(Collection<Integer> eventIds, CommentState state, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByEventIdInAndStateIsAndCreatedAfter(Collection<Integer> eventIds, CommentState state, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByEventIdInAndStateIsAndCreatedBetween(Collection<Integer> eventIds, CommentState state, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Comment> findAllByTextContainsIgnoreCaseAndStateIs(String text, CommentState state, Pageable pageable);

    Page<Comment> findAllByTextContainsIgnoreCaseAndStateIsAndCreatedBefore(String text, CommentState state, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByTextContainsIgnoreCaseAndStateIsAndCreatedAfter(String text, CommentState state, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByTextContainsIgnoreCaseAndStateIsAndCreatedBetween(String text, CommentState state, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Comment> findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsOrderByEventIdAsc(Collection<Integer> eventIds, String text, CommentState state, Pageable pageable);

    Page<Comment> findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsAndCreatedBeforeOrderByEventIdAsc(Collection<Integer> eventIds, String text, CommentState state, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsAndCreatedAfterOrderByEventIdAsc(Collection<Integer> eventIds, String text, CommentState state, LocalDateTime created, Pageable pageable);

    Page<Comment> findAllByEventIdInAndTextContainsIgnoreCaseAndStateIsAndCreatedBetweenOrderByEventIdAsc(Collection<Integer> eventIds, String text, CommentState state, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Comment> findAllByUserIdAndAndStateIsNot(Integer userId, CommentState commentState, Pageable pageable);
}
