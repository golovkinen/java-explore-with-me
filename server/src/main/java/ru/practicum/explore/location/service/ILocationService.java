package ru.practicum.explore.location.service;

import org.springframework.http.HttpStatus;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.LocationInfoDto;
import ru.practicum.explore.location.dto.NewLocationDto;
import ru.practicum.explore.location.model.Location;

import java.util.List;

public interface ILocationService {
    Location createEventLocation(LocationDto locationDto, String title);
    LocationInfoDto createLocation(NewLocationDto newLocationDto);

    List<LocationInfoDto> getAll(List<Integer> ids, int from, int size);

    LocationInfoDto update(NewLocationDto newLocationDto, int locationId);

    void updateEventLocation(LocationDto locationDto, int locationId, String title);

    HttpStatus delete(int id);

    List<LocationInfoDto> findAllLocationsOrderedByDistance(LocationDto locationDto);

    List<LocationInfoDto> findAllLocationsWithinRadius(LocationDto locationDto, double radius);

    List<Location> findLocationsByAddress(String city, String suburb, String road, String houseNumber);
}
