package ru.practicum.explore.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.category.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findByIdIn(List<Integer> ids, Pageable pageable);

    Optional<Category> findByName(String name);
}
