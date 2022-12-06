package ru.practicum.explore.location.mapper;

import org.locationtech.jts.geom.Point;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.LocationInfoDto;
import ru.practicum.explore.location.dto.NewLocationDto;
import ru.practicum.explore.location.dto.NominatimLocationDto;
import ru.practicum.explore.location.model.Location;

public class LocationMapper {

    public static Location toEventLocation(LocationDto locationDto, Point point, String title, NominatimLocationDto nLD) {

        return new Location(null, title, point, locationDto.getLat(), locationDto.getLon(),
                nLD.getAddress().getCountry(), nLD.getAddress().getCity(), nLD.getAddress().getSuburb(),
                nLD.getAddress().getRoad(), nLD.getAddress().getHouseNumber(), nLD.getDisplayName(), null);
    }

    public static Location toLocation(NewLocationDto newLocationDto, Point point, NominatimLocationDto nLD) {
        return new Location(null, newLocationDto.getTitle(), point, newLocationDto.getLat(), newLocationDto.getLon(), nLD.getAddress().getCountry(), nLD.getAddress().getCity(), nLD.getAddress().getSuburb(),
                nLD.getAddress().getRoad(), nLD.getAddress().getHouseNumber(), nLD.getDisplayName(), null);
    }

    public static LocationInfoDto toLocationInfoDto(Location location) {
        return new LocationInfoDto(location.getId(), location.getTitle(), location.getLat(), location.getLon(),
                location.getDisplayName());
    }

    public static LocationDto toLocationDto(Location location) {
        return new LocationDto(location.getLat(), location.getLon());
    }

    public static NominatimLocationDto checkNLD(NominatimLocationDto nld) {

        NominatimLocationDto nldChecked = new NominatimLocationDto("", new NominatimLocationDto.Address("", "", "", "", "", ""));

        if (nld.getDisplayName() == null) {
            nldChecked.setDisplayName("");
        } else {
            nldChecked.setDisplayName(nld.getDisplayName());
        }

        if (nld.getAddress() == null) {
            NominatimLocationDto.Address address = new NominatimLocationDto.Address("", "", "", "", "", "");
            nldChecked.setAddress(address);
        } else {

            if (nld.getAddress().getCountry() == null) {
                nldChecked.getAddress().setCountry("");
            } else {
                nldChecked.getAddress().setCountry(nld.getAddress().getCountry());
            }

            if (nld.getAddress().getRegion() == null) {
                nldChecked.getAddress().setRegion("");
            } else {
                nldChecked.getAddress().setRegion(nld.getAddress().getRegion());
            }
            if (nld.getAddress().getSuburb() == null) {
                nldChecked.getAddress().setSuburb("");
            } else {
                nldChecked.getAddress().setSuburb(nld.getAddress().getSuburb());
            }
            if (nld.getAddress().getCity() == null) {
                nldChecked.getAddress().setCity("");
            } else {
                nldChecked.getAddress().setCity(nld.getAddress().getCity());
            }

            if (nld.getAddress().getRoad() == null) {
                nldChecked.getAddress().setRoad("");
            } else {
                nldChecked.getAddress().setRoad(nld.getAddress().getRoad());
            }

            if (nld.getAddress().getHouseNumber() == null) {
                nldChecked.getAddress().setHouseNumber("");
            } else {
                nldChecked.getAddress().setHouseNumber(nld.getAddress().getHouseNumber());
            }
        }

        return nldChecked;
    }
}
