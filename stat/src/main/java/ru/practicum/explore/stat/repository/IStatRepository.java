package ru.practicum.explore.stat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explore.stat.model.Stat;

import java.time.LocalDateTime;

public interface IStatRepository extends JpaRepository<Stat, Integer> {

    @Query(
            value = "SELECT distinct s.stat_app FROM stats s WHERE s.stat_uri like ?",
            nativeQuery = true
    )
    String getAppName(@Param("stat_uri") String statUri);

    @Query(
            value = "SELECT count(distinct s.ip) FROM Stat s WHERE s.uri like :stat_uri and s.timestamp > :start and s.timestamp < :end"
    )
    Integer getUriStatDistinct(@Param("stat_uri") String statUri,
                               @Param("start") LocalDateTime start,
                               @Param("end") LocalDateTime end);

    @Query(
            value = "SELECT count( s.ip ) FROM Stat s WHERE s.uri like :stat_uri and s.timestamp > :start and s.timestamp < :end"
    )
    Integer getUriStatAll(@Param("stat_uri") String statUri,
                          @Param("start") LocalDateTime start,
                          @Param("end") LocalDateTime end);
}
