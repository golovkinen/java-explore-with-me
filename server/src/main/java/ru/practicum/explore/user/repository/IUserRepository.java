package ru.practicum.explore.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.user.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Page<User> findByIdIn(List<Integer> ids, Pageable pageable);

    @Query("select u from User u ")
    List<User> findAllUsersPageable(Pageable pageable);
}
