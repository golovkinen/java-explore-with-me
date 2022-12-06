package ru.practicum.explore.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explore.event.enums.State;
import ru.practicum.explore.event.model.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface IEventRepository extends JpaRepository<Event, Integer> {

    List<Event> findAllByIdIn(Collection<Integer> eventIds);

    List<Event> findAllByUserIdOrderByEventDateDesc(int userId);

    Page<Event> findAllByEventDateBefore(LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByEventDateAfter(LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByEventDateBetween(LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByCategoryIdIn(Collection<Integer> categoryId, Pageable pageable);

    Page<Event> findAllByCategoryIdInAndEventDateBefore(Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByCategoryIdInAndEventDateAfter(Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByCategoryIdInAndEventDateBetween(Collection<Integer> categoryId, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByStateIn(Collection<State> states, Pageable pageable);

    Page<Event> findAllByStateInAndCategoryIdIn(Collection<State> states, Collection<Integer> categoryId, Pageable pageable);

    Page<Event> findAllByStateInAndEventDateBefore(Collection<State> states, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateInAndEventDateAfter(Collection<State> states, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateInAndEventDateBetween(Collection<State> states, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByStateInAndCategoryIdInAndEventDateBefore(Collection<State> states, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateInAndCategoryIdInAndEventDateAfter(Collection<State> states, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateInAndCategoryIdInAndEventDateBetween(Collection<State> states, Collection<Integer> categoryId, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByUserIdIn(Collection<Integer> userIds, Pageable pageable);

    Page<Event> findAllByUserIdInAndCategoryIdIn(Collection<Integer> userIds, Collection<Integer> categoryId, Pageable pageable);

    Page<Event> findAllByUserIdInAndStateIn(Collection<Integer> userIds, Collection<State> states, Pageable pageable);

    Page<Event> findAllByUserIdInAndStateInAndCategoryIdIn(Collection<Integer> userIds, Collection<State> states, Collection<Integer> categoryId, Pageable pageable);

    Page<Event> findAllByUserIdInAndEventDateBefore(Collection<Integer> userIds, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByUserIdInAndEventDateAfter(Collection<Integer> userIds, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByUserIdInAndEventDateBetween(Collection<Integer> userIds, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByUserIdInAndCategoryIdInAndEventDateBefore(Collection<Integer> userIds, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByUserIdInAndCategoryIdInAndEventDateAfter(Collection<Integer> userIds, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByUserIdInAndCategoryIdInAndEventDateBetween(Collection<Integer> userIds, Collection<Integer> categoryId, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByUserIdInAndStateInAndCategoryIdInAndEventDateBefore(Collection<Integer> userIds, Collection<State> states, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByUserIdInAndStateInAndCategoryIdInAndEventDateAfter(Collection<Integer> userIds, Collection<State> states, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByUserIdInAndStateInAndCategoryIdInAndEventDateBetween(Collection<Integer> userIds, Collection<State> states, Collection<Integer> categoryId, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByUserIdInAndStateInAndEventDateBefore(Collection<Integer> userIds, Collection<State> states, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByUserIdInAndStateInAndEventDateAfter(Collection<Integer> userIds, Collection<State> states, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByUserIdInAndStateInAndEventDateBetween(Collection<Integer> userIds, Collection<State> states, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);


    Page<Event> findAllByStateIs(State state, Pageable pageable);

    Page<Event> findAllByStateIsAndEventDateBefore(State state, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndEventDateAfter(State state, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndEventDateBetween(State state, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdIn(State state, Collection<Integer> categoryId, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdInAndEventDateBefore(State state, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdInAndEventDateAfter(State state, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdInAndEventDateBetween(State state, Collection<Integer> categoryId, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);


    Page<Event> findAllByStateIsAndAvailableIs(State state, Boolean available, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndEventDateBefore(State state, Boolean available, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndEventDateAfter(State state, Boolean available, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndEventDateBetween(State state, Boolean available, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdIn(State state, Boolean available, Collection<Integer> categoryId, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBefore(State state, Boolean available, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateAfter(State state, Boolean available, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBetween(State state, Boolean available, Collection<Integer> categoryId, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIs(State state, Boolean available, Boolean paid, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdIn(State state, Boolean available, Boolean paid, Collection<Integer> categoryId, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBefore(State state, Boolean available, Boolean paid, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIsAndEventDateAfter(State state, Boolean available, Boolean paid, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBetween(State state, Boolean available, Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdInAndEventDateBefore(State state, Boolean available, Boolean paid, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdInAndEventDateAfter(State state, Boolean available, Boolean paid, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIsAndCategoryIdInAndEventDateBetween(State state, Boolean available, Boolean paid, Collection<Integer> categoryId, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Boolean paid, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdInAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Collection<Integer> categoryId, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Collection<Integer> categoryId, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Collection<Integer> categoryId, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdInAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Collection<Integer> categoryId, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Collection<Integer> categoryId, Boolean paid, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Collection<Integer> categoryId, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Collection<Integer> categoryId, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndAvailableIsAndCategoryIdInAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean available, Collection<Integer> categoryId, Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);


    Page<Event> findAllByStateIsAndPaidIs(State state, Boolean paid, Pageable pageable);

    Page<Event> findAllByStateIsAndPaidIsAndCategoryIdIn(State state, Boolean paid, Collection<Integer> categoryId, Pageable pageable);

    Page<Event> findAllByStateIsAndPaidIsAndEventDateBefore(State state, Boolean paid, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndPaidIsAndEventDateAfter(State state, Boolean paid, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndPaidIsAndEventDateBetween(State state, Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByStateIsAndPaidIsAndCategoryIdInAndEventDateBefore(State state, Boolean paid, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndPaidIsAndCategoryIdInAndEventDateAfter(State state, Boolean paid, Collection<Integer> categoryId, LocalDateTime eventDate, Pageable pageable);

    Page<Event> findAllByStateIsAndPaidIsAndCategoryIdInAndEventDateBetween(State state, Boolean paid, Collection<Integer> categoryId, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);

    Page<Event> findAllByStateIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean paid, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdInAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Collection<Integer> categoryId, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdInAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Collection<Integer> categoryId, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdInAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Collection<Integer> categoryId, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdInAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Collection<Integer> categoryId, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdInAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Collection<Integer> categoryId, Boolean paid, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdInAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Collection<Integer> categoryId, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdInAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Collection<Integer> categoryId, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);

    Page<Event> findAllByStateIsAndCategoryIdInAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(State state, Collection<Integer> categoryId, Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);

    Page<Event> findAllByLocationIdIn(Collection<Integer> locationIds, Pageable pageable);


    @Query(
            value = "SELECT ROUND(AVG(e.event_rating),1) FROM events e WHERE e.user_id=? AND e.event_rating notnull",
            nativeQuery = true
    )
    Double getUserRating(@Param("user_id") Integer userId);

    Page<Event> findAllByUserIdInOrderByEventDateAsc(Collection<Integer> ids, Pageable pageable);

    Page<Event> findAllByLocationIdInOrderByEventDateAsc(Collection<Integer> ids, Pageable pageable);

}
