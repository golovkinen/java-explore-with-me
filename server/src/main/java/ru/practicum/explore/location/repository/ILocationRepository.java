package ru.practicum.explore.location.repository;


import com.vividsolutions.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explore.location.model.Location;

import java.util.List;

public interface ILocationRepository extends JpaRepository<Location, Integer> {

    Page<Location> findByIdIn(List<Integer> ids, Pageable pageable);

    @Query(
            value = "select all round(cast(st_distancesphere(l.position, :point) as numeric), 2) as distance from locations l order by distance asc",
            nativeQuery = true
    )
    List<Location> findAllLocationsOrderedByDistance(@Param("point") Point point);

    @Query(
            value = "select all from locations l where st_within(l.position, :circle) = true",
            nativeQuery = true
    )
    List<Location> findAllLocationsWithinRadius(@Param("circle") Geometry circle);

    List<Location> findAllByCityContainingIgnoreCase(String city);

    List<Location> findAllByCityContainingIgnoreCaseAndSuburbContainingIgnoreCase(String city, String suburb);

    List<Location> findAllByCityContainingIgnoreCaseAndRoadContainingIgnoreCase(String city, String road);

    List<Location> findAllByCityContainingIgnoreCaseAndRoadContainingIgnoreCaseAndHouseNumberContainingIgnoreCase(String city, String road, String houseNumber);

    List<Location> findAllByCityContainingIgnoreCaseAndSuburbContainingIgnoreCaseAndHouseNumberContainingIgnoreCase(String city, String suburb, String houseNumber);


}
