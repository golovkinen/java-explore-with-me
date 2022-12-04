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
    Page<Event> findAllByCategoryIdIn(Collection<Integer> category_id, Pageable pageable);
    Page<Event> findAllByCategoryIdInAndEventDateBefore(Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByCategoryIdInAndEventDateAfter(Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByCategoryIdInAndEventDateBetween(Collection<Integer> category_id, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByStateIn(Collection<State> states, Pageable pageable);
    Page<Event> findAllByStateInAndCategoryIdIn(Collection<State> states, Collection<Integer> category_id, Pageable pageable);
    Page<Event> findAllByStateInAndEventDateBefore(Collection<State> states, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByStateInAndEventDateAfter(Collection<State> states, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByStateInAndEventDateBetween(Collection<State> states, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByStateInAndCategoryIdInAndEventDateBefore(Collection<State> states, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByStateInAndCategoryIdInAndEventDateAfter(Collection<State> states, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByStateInAndCategoryIdInAndEventDateBetween(Collection<State> states, Collection<Integer> category_id, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByUserIdIn(Collection<Integer> userIds, Pageable pageable);
    Page<Event> findAllByUserIdInAndCategoryIdIn(Collection<Integer> userIds, Collection<Integer> CategoryId, Pageable pageable);
    Page<Event> findAllByUserIdInAndStateIn(Collection<Integer> userIds, Collection<State> states, Pageable pageable);
    Page<Event> findAllByUserIdInAndStateInAndCategoryIdIn(Collection<Integer> userIds, Collection<State> states, Collection<Integer> category_id, Pageable pageable);
    Page<Event> findAllByUserIdInAndEventDateBefore(Collection<Integer> userIds, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByUserIdInAndEventDateAfter(Collection<Integer> userIds, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByUserIdInAndEventDateBetween(Collection<Integer> userIds, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByUserIdInAndCategoryIdInAndEventDateBefore(Collection<Integer> userIds, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByUserIdInAndCategoryIdInAndEventDateAfter(Collection<Integer> userIds, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByUserIdInAndCategoryIdInAndEventDateBetween(Collection<Integer> userIds, Collection<Integer> category_id, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByUserIdInAndStateInAndCategoryIdInAndEventDateBefore(Collection<Integer> userIds, Collection<State> states, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByUserIdInAndStateInAndCategoryIdInAndEventDateAfter(Collection<Integer> userIds, Collection<State> states, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByUserIdInAndStateInAndCategoryIdInAndEventDateBetween(Collection<Integer> userIds, Collection<State> states, Collection<Integer> category_id, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByUserIdInAndStateInAndEventDateBefore(Collection<Integer> userIds, Collection<State> states, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByUserIdInAndStateInAndEventDateAfter(Collection<Integer> userIds, Collection<State> states, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByUserIdInAndStateInAndEventDateBetween(Collection<Integer> userIds, Collection<State> states, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);


    Page<Event> findAllByAvailableIs(Boolean available, Pageable pageable);
    Page<Event> findAllByAvailableIsAndEventDateBefore(Boolean available, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByAvailableIsAndEventDateAfter(Boolean available, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByAvailableIsAndEventDateBetween(Boolean available, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdIn(Boolean available, Collection<Integer> category_id, Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdInAndEventDateBefore(Boolean available, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdInAndEventDateAfter(Boolean available, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdInAndEventDateBetween(Boolean available, Collection<Integer> category_id, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIs(Boolean available, Boolean paid, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIsAndCategoryIdIn(Boolean available, Boolean paid, Collection<Integer> category_id, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIsAndEventDateBefore(Boolean available, Boolean paid, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIsAndEventDateAfter(Boolean available, Boolean paid, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIsAndEventDateBetween(Boolean available, Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIsAndCategoryIdInAndEventDateBefore(Boolean available, Boolean paid, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIsAndCategoryIdInAndEventDateAfter(Boolean available, Boolean paid, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIsAndCategoryIdInAndEventDateBetween(Boolean available, Boolean paid, Collection<Integer> category_id, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Boolean paid, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdInAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Collection<Integer> category_id, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdInAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Collection<Integer> category_id, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdInAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Collection<Integer> category_id, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdInAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Collection<Integer> category_id, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdInAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Collection<Integer> category_id, Boolean paid, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdInAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Collection<Integer> category_id, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdInAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Collection<Integer> category_id, Boolean paid,  LocalDateTime eventDate, String text1, String text2,Pageable pageable);
    Page<Event> findAllByAvailableIsAndCategoryIdInAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean available, Collection<Integer> category_id, Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);


    Page<Event> findAllByPaidIs(Boolean paid, Pageable pageable);
    Page<Event> findAllByPaidIsAndCategoryIdIn(Boolean paid, Collection<Integer> category_id, Pageable pageable);
    Page<Event> findAllByPaidIsAndEventDateBefore(Boolean paid, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByPaidIsAndEventDateAfter(Boolean paid, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByPaidIsAndEventDateBetween(Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByPaidIsAndCategoryIdInAndEventDateBefore(Boolean paid, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByPaidIsAndCategoryIdInAndEventDateAfter(Boolean paid, Collection<Integer> category_id, LocalDateTime eventDate, Pageable pageable);
    Page<Event> findAllByPaidIsAndCategoryIdInAndEventDateBetween(Boolean paid, Collection<Integer> category_id, LocalDateTime eventDate, LocalDateTime eventDate2, Pageable pageable);
    Page<Event> findAllByAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String text1, String text2, Pageable pageable);
    Page<Event> findAllByEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);
    Page<Event> findAllByPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean paid, String text1, String text2, Pageable pageable);
    Page<Event> findAllByPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);
    Page<Event> findAllByCategoryIdInAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Collection<Integer> category_id, String text1, String text2, Pageable pageable);
    Page<Event> findAllByCategoryIdInAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Collection<Integer> category_id, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByCategoryIdInAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Collection<Integer> category_id, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByCategoryIdInAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Collection<Integer> category_id, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);
    Page<Event> findAllByCategoryIdInAndPaidIsAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Collection<Integer> category_id, Boolean paid, String text1, String text2, Pageable pageable);
    Page<Event> findAllByCategoryIdInAndPaidIsAndEventDateBeforeAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Collection<Integer> category_id, Boolean paid, LocalDateTime eventDate, String text1, String text2, Pageable pageable);
    Page<Event> findAllByCategoryIdInAndPaidIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Collection<Integer> category_id, Boolean paid,  LocalDateTime eventDate, String text1, String text2,Pageable pageable);
    Page<Event> findAllByCategoryIdInAndPaidIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Collection<Integer> category_id, Boolean paid, LocalDateTime eventDate, LocalDateTime eventDate2, String text1, String text2, Pageable pageable);

    Page<Event> findAllByLocationIdIn(Collection<Integer> locationIds, Pageable pageable);

    @Query(
            value = "SELECT ROUND(AVG(e.event_rating),1) FROM events e WHERE e.user_id=? AND e.event_rating notnull",
            nativeQuery = true
    )
    Double getUserRating(@Param("user_id") Integer userId);

    Page<Event> findAllByUserIdInOrderByEventDateAsc (Collection<Integer> ids, Pageable pageable);

    Page<Event> findAllByLocationIdInOrderByEventDateAsc (Collection<Integer> ids, Pageable pageable);

}
