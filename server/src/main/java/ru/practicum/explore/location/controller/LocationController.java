package ru.practicum.explore.location.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.LocationInfoDto;
import ru.practicum.explore.location.dto.NewLocationDto;
import ru.practicum.explore.location.model.Location;
import ru.practicum.explore.location.service.ILocationService;

import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Validated
@RequestMapping(value = "/admin/locations")
public class LocationController {
    private final ILocationService iLocationService;

    public LocationController(ILocationService iLocationService) {
        this.iLocationService = iLocationService;
    }


    @GetMapping
    public List<LocationInfoDto> getAll(@RequestParam(name = "id", required = false) Optional<List<Integer>> ids,
    @RequestParam(name = "from", defaultValue = "0") int from,
    @RequestParam(name = "size", defaultValue = "10") int size) {
        return iLocationService.getAll(ids.orElse(Collections.emptyList()), from, size);
    }


    @PostMapping
    public LocationInfoDto create(@RequestBody NewLocationDto newLocationDto) {

        return iLocationService.createLocation(newLocationDto);
    }

    @PatchMapping(value = "/{locationId}")
    public LocationInfoDto updateLocation (@Positive @PathVariable(name = "locationId") int locationId,
                                      @RequestBody NewLocationDto newLocationDto) {

        return iLocationService.update(newLocationDto, locationId);
    }



    @DeleteMapping(value = "/{locationId}")
    public HttpStatus deleteLocation (@Positive @PathVariable(name = "locationId") int locationId) {

        return iLocationService.delete(locationId);
    }

    @GetMapping(value = "/radius")
    public List<LocationInfoDto> findAllLocationsWithinRadius(@RequestBody LocationDto locationDto,
                                                         @Positive @RequestParam(name = "radius", defaultValue = "500") double radius) {
        return iLocationService.findAllLocationsWithinRadius(locationDto, radius);
    }

    @GetMapping(value = "/events/location/address")
    public List<Location> findAllLocationsByAddress(@RequestParam(name = "city") String city,
                                                    @RequestParam(name = "suburb", required = false) String suburb,
                                                    @RequestParam(name = "road", required = false) String road,
                                                    @RequestParam(name = "houseNumber", required = false) String houseNumber) {
        return iLocationService.findLocationsByAddress(city, suburb, road, houseNumber);
    }

}
