package ru.practicum.explore.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explore.event.model.Mark;
import ru.practicum.explore.event.model.MarkKey;

import java.util.Optional;

public interface IMarkRepository extends JpaRepository<Mark, MarkKey> {

    void deleteAllByIdEventId(int eventId);

    @Query(
            value = "SELECT ROUND(AVG(m.MARK),1) FROM MARKS m WHERE m.event_id=?",
            nativeQuery = true
    )
    Double getEventRating(@Param("event_id") Integer eventId);

    @Query(
            value = "SELECT all FROM marks m WHERE m.event_id=? and m.user_id =?",
            nativeQuery = true
    )
    Optional<Mark> findMarkByUserIdAndEventId(@Param("event_id") Integer eventId,
                                              @Param("user_id") Integer userId);
}
