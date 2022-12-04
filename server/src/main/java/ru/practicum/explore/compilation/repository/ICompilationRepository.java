package ru.practicum.explore.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.compilation.model.Compilation;

public interface ICompilationRepository extends JpaRepository<Compilation, Integer> {


    Page<Compilation> findAllByPinnedIsOrderByIdAsc(Boolean pinned, Pageable pageable);

}
