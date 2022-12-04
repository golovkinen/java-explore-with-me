package ru.practicum.explore.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explore.comment.model.UsefulKey;
import ru.practicum.explore.comment.model.Usefulness;

import java.util.Optional;

public interface IUsefulnessRepository extends JpaRepository<Usefulness, UsefulKey> {

    void deleteAllByIdCommentId(int commentId);

    Optional<Usefulness> findAllById_UserIdAndId_CommentId (Integer userId, Integer commentId);

    @Query(
            value = "SELECT count(u.useful = true) FROM usefulness u WHERE u.comment_id=?",
            nativeQuery = true
    )
    Integer getCommentUsefulCount(@Param("comment_id") Integer comment_id);

    @Query(
            value = "SELECT count(u.useful = false) FROM usefulness u WHERE u.comment_id=?",
            nativeQuery = true
    )
    Integer getCommentNotUsefulCount(@Param("comment_id") Integer comment_id);

}
