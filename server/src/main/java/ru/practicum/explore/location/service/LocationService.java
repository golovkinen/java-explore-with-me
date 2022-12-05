package ru.practicum.explore.location.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.util.GeometricShapeFactory;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.explore.exceptionhandler.BadRequestException;
import ru.practicum.explore.exceptionhandler.NotFoundException;
import ru.practicum.explore.location.client.LocationClient;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.LocationInfoDto;
import ru.practicum.explore.location.dto.NewLocationDto;
import ru.practicum.explore.location.dto.NominatimLocationDto;
import ru.practicum.explore.location.mapper.LocationMapper;
import ru.practicum.explore.location.model.Location;
import ru.practicum.explore.location.repository.ILocationRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocationService implements ILocationService {

    GeometryFactory geometryFactory = new GeometryFactory();
    private final ILocationRepository iLocationRepository;
    private final LocationClient locationClient;
    private final ObjectMapper objectMapper;

    public LocationService(ILocationRepository iLocationRepository, LocationClient locationClient, ObjectMapper objectMapper) {
        this.iLocationRepository = iLocationRepository;
        this.locationClient = locationClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Location createEventLocation(LocationDto locationDto, String title) {

        if (locationDto.getLat() > 90 || locationDto.getLat() < -90 || locationDto.getLon() > 180 || locationDto.getLon() < -180) {
            log.error("BadRequestException: {}", "Широта должна быть от -90 до 90, в запросе : " + locationDto.getLat() + "Долгота должна быть от -180 до 180, в запросе : " + locationDto.getLon());
            throw new BadRequestException("Ошибка в координатах");
        }


        Point point = geometryFactory.createPoint(new Coordinate(locationDto.getLat(), locationDto.getLon()));

        NominatimLocationDto nominatimLocationDto = locationClient.getLocationDetails(locationDto.getLat(), locationDto.getLon());
/*
        try {
            nominatimLocationDto = getLocationDetails(locationDto.getLat(), locationDto.getLon());
        } catch (JsonProcessingException e) {
            log.error("BadRequestException: {}", "Ошибка десериализации объекта от nominatim");
            throw new BadRequestException("Ошибка десериализации объекта от nominatim");
        }


 */
        // 55.710697, 37.607448

        Location eventLocation = LocationMapper.toEventLocation(locationDto, point, title, LocationMapper.checkNLD(nominatimLocationDto));


        return iLocationRepository.save(eventLocation);
    }

    @Override
    public LocationInfoDto createLocation(NewLocationDto newLocationDto) {

        if (newLocationDto.getLat() > 90 || newLocationDto.getLat() < -90 || newLocationDto.getLon() > 180 || newLocationDto.getLon() < -180) {
            log.error("BadRequestException: {}", "Широта должна быть от -90 до 90, в запросе : " + newLocationDto.getLat() + "Долгота должна быть от -180 до 180, в запросе : " + newLocationDto.getLon());
            throw new BadRequestException("Ошибка в координатах");
        }

        NominatimLocationDto nominatimLocationDto = locationClient.getLocationDetails(newLocationDto.getLat(), newLocationDto.getLon());
/*
        try {
            nominatimLocationDto = getLocationDetails(newLocationDto.getLat(), newLocationDto.getLon());
        } catch (JsonProcessingException e) {
            log.error("BadRequestException: {}", "Ошибка десериализации объекта от nominatim");
            throw new BadRequestException("Ошибка десериализации объекта от nominatim");
        }

 */

        Point point = geometryFactory.createPoint(new Coordinate(newLocationDto.getLat(), newLocationDto.getLon()));

        return LocationMapper.toLocationInfoDto(iLocationRepository.save(LocationMapper.toLocation(newLocationDto, point, LocationMapper.checkNLD(nominatimLocationDto))));
    }

    @Override
    public List<LocationInfoDto> getAll(List<Integer> ids, int from, int size) {

        int page = from / size;

        Pageable pageable = PageRequest.of(page, size);

        List<Location> locationsList;

        if (!ids.isEmpty()) {
            locationsList = iLocationRepository.findByIdIn(ids, pageable).getContent();
        } else {
            locationsList = iLocationRepository.findAll(pageable).getContent();
        }

        return locationsList.stream().map(LocationMapper::toLocationInfoDto)
                .collect(Collectors.toList());
    }

    @Override
    public LocationInfoDto update(NewLocationDto newLocationDto, int locationId) {

        Optional<Location> locationToUpdate = iLocationRepository.findById(locationId);

        if (locationToUpdate.isEmpty()) {
            log.error("NotFoundException: {}", "При обновлении, Локация с ИД " + locationId + " не найдена");
            throw new NotFoundException("Локация с ИД " + locationId + " не найдена");
        }

        if (newLocationDto.getTitle() != null) {
            locationToUpdate.get().setTitle(newLocationDto.getTitle());
        }

        if (locationToUpdate.get().getLat() == newLocationDto.getLat() &&
                locationToUpdate.get().getLon() == newLocationDto.getLon()) {

            return LocationMapper.toLocationInfoDto(iLocationRepository.save(locationToUpdate.get()));
        }
        GeometryFactory geometryFactory = new GeometryFactory();

        Point point = geometryFactory.createPoint(new Coordinate(newLocationDto.getLat(), newLocationDto.getLon()));
        locationToUpdate.get().setPosition(point);

        NominatimLocationDto nLDinput = locationClient.getLocationDetails(newLocationDto.getLat(), newLocationDto.getLon());

        NominatimLocationDto nLD = LocationMapper.checkNLD(nLDinput);
        /*
        try {
            nLD = getLocationDetails(newLocationDto.getLat(), newLocationDto.getLon());
        } catch (JsonProcessingException e) {
            log.error("BadRequestException: {}", "Ошибка десериализации объекта от nominatim");
            throw new BadRequestException("Ошибка десериализации объекта от nominatim");
        }

         */

        locationToUpdate.get().setCountry(nLD.getAddress().getCountry());
        locationToUpdate.get().setCity(nLD.getAddress().getCity());
        locationToUpdate.get().setSuburb(nLD.getAddress().getSuburb());
        locationToUpdate.get().setRoad(nLD.getAddress().getRoad());
        locationToUpdate.get().setHouseNumber(nLD.getAddress().getHouseNumber());
        locationToUpdate.get().setDisplayName(nLD.getDisplayName());


        return LocationMapper.toLocationInfoDto(iLocationRepository.save(locationToUpdate.get()));

    }

    @Override
    public void updateEventLocation(LocationDto locationDto, int locationId, String title) {
        Optional<Location> locationToUpdate = iLocationRepository.findById(locationId);

        GeometryFactory geometryFactory = new GeometryFactory();

        if (locationToUpdate.isEmpty()) {
            log.error("NotFoundException: {}", "При обновлении, Локация с ИД " + locationId + " не найдена");
            throw new NotFoundException("Локация с ИД " + locationId + " не найдена");
        }

        if (title != null) {
            locationToUpdate.get().setTitle(title);
        }
        if (locationDto.getLat() != null) {
            locationToUpdate.get().setLat(locationDto.getLat());
            Point point = geometryFactory.createPoint(new Coordinate(locationDto.getLat(), locationToUpdate.get().getLon()));
            locationToUpdate.get().setPosition(point);
        }
        if (locationDto.getLon() != null) {
            locationToUpdate.get().setLon(locationDto.getLon());
            Point point = geometryFactory.createPoint(new Coordinate(locationToUpdate.get().getLat(), locationDto.getLon()));
            locationToUpdate.get().setPosition(point);
        }

        iLocationRepository.save(locationToUpdate.get());
    }

    @Override
    public HttpStatus delete(int locationId) {
        if (iLocationRepository.findById(locationId).isEmpty()) {
            log.error("NotFoundException: {}", "При удалении, Локация с ИД " + locationId + " не найдена");
            throw new NotFoundException("Локация с ИД " + locationId + " не найдена");
        }
        iLocationRepository.deleteById(locationId);
        return HttpStatus.OK;
    }

    @Override
    public List<LocationInfoDto> findAllLocationsOrderedByDistance(LocationDto locationDto) {

        if (locationDto.getLat() > 90 || locationDto.getLat() < -90 || locationDto.getLon() > 180 || locationDto.getLon() < -180) {
            log.error("BadRequestException: {}", "Широта должна быть от -90 до 90, в запросе : " + locationDto.getLat() + "Долгота должна быть от -180 до 180, в запросе : " + locationDto.getLon());
            throw new BadRequestException("Ошибка в координатах");
        }

        Point point = geometryFactory.createPoint(new Coordinate(locationDto.getLat(), locationDto.getLon()));

        return iLocationRepository.findAllLocationsOrderedByDistance(point).stream()
                .map(LocationMapper::toLocationInfoDto).collect(Collectors.toList());
    }

    @Override
    public List<LocationInfoDto> findAllLocationsWithinRadius(LocationDto locationDto, double radius) {

        if (locationDto.getLat() > 90 || locationDto.getLat() < -90 || locationDto.getLon() > 180 || locationDto.getLon() < -180) {
            log.error("BadRequestException: {}", "Широта должна быть от -90 до 90, в запросе : " + locationDto.getLat() + "Долгота должна быть от -180 до 180, в запросе : " + locationDto.getLon());
            throw new BadRequestException("Ошибка в координатах");
        }

        Geometry circle = createCircle(locationDto.getLat(), locationDto.getLon(), radius);

        return iLocationRepository.findAllLocationsWithinRadius(circle).stream()
                .map(LocationMapper::toLocationInfoDto).collect(Collectors.toList());
    }

/*
    private NominatimLocationDto getLocationDetails (double lan, double lon) throws JsonProcessingException {

        ResponseEntity<Object> data = locationClient.getLocationDetails(lan, lon);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = data.getBody().toString();
        return objectMapper.readValue(json, NominatimLocationDto.class);
    }

 */

    @Override
    public List<Location> findLocationsByAddress(String city, String suburb, String road, String houseNumber) {

        if (suburb == null && road == null && houseNumber == null) {
            return iLocationRepository.findAllByCityContainingIgnoreCase(city);
        }

        if (suburb != null && road == null && houseNumber == null) {
            return iLocationRepository.findAllByCityContainingIgnoreCaseAndSuburbContainingIgnoreCase(city, suburb);
        }

        if (suburb != null && road == null && houseNumber != null) {
            return iLocationRepository.findAllByCityContainingIgnoreCaseAndSuburbContainingIgnoreCaseAndHouseNumberContainingIgnoreCase(city, suburb, houseNumber);
        }

        if (suburb == null && road != null && houseNumber == null) {
            return iLocationRepository.findAllByCityContainingIgnoreCaseAndRoadContainingIgnoreCase(city, road);
        }

        if (suburb == null && road != null && houseNumber != null) {
            return iLocationRepository.findAllByCityContainingIgnoreCaseAndRoadContainingIgnoreCaseAndHouseNumberContainingIgnoreCase(city, road, houseNumber);
        }

        return Collections.emptyList();
    }

    public Geometry createCircle(double x, double y, double radius) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(32);
        shapeFactory.setCentre(new com.vividsolutions.jts.geom.Coordinate(x, y));
        shapeFactory.setSize(radius * 2);
        return shapeFactory.createCircle();
    }
}
