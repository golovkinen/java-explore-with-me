package ru.practicum.explore.request.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explore.request.model.Request;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IRequestRepository extends JpaRepository<Request, Integer> {

    Optional<Request> findRequestsByEventIdIsAndUserIdIs(int eventId, int userId);

    List<Request> findAllByUserIdOrderByCreatedOnDesc(int userId);

    Page<Request> findAllByUserIdInOrderByUserIdAsc(Collection<Integer> ids, Pageable pageable);

    @Query("select r from Request r where r.user.id <> :userId")
    List<Request> getPagedRequests(
            @Param(value = "userId") int itemId,
            Pageable pageable);
}
