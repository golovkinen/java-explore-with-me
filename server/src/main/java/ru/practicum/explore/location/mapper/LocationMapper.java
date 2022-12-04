package ru.practicum.explore.location.mapper;

import org.locationtech.jts.geom.Point;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.LocationInfoDto;
import ru.practicum.explore.location.dto.NewLocationDto;
import ru.practicum.explore.location.dto.NominatimLocationDto;
import ru.practicum.explore.location.model.Location;

public class LocationMapper {

    public static Location toEventLocation (LocationDto locationDto, Point point, String title, NominatimLocationDto nLD) {

        return new Location(null, title, point, locationDto.getLat(), locationDto.getLon(),
                nLD.getAddress().getCountry(), nLD.getAddress().getCity(), nLD.getAddress().getSuburb(),
                nLD.getAddress().getRoad(), nLD.getAddress().getHouse_number(), nLD.getDisplay_name(), null);
    }

    public static Location toLocation (NewLocationDto newLocationDto, Point point, NominatimLocationDto nLD) {
        return new Location(null, newLocationDto.getTitle(), point, newLocationDto.getLat(), newLocationDto.getLon(), nLD.getAddress().getCountry(), nLD.getAddress().getCity(), nLD.getAddress().getSuburb(),
                nLD.getAddress().getRoad(), nLD.getAddress().getHouse_number(), nLD.getDisplay_name(), null);
    }

    public static LocationInfoDto toLocationInfoDto(Location location) {
        return new LocationInfoDto(location.getId(), location.getTitle(), location.getLat(), location.getLon(),
                location.getDisplayName());
    }

    public static LocationDto toLocationDto (Location location) {
        return new LocationDto(location.getLat(), location.getLon());
    }

    public static NominatimLocationDto checkNLD (NominatimLocationDto nld) {

        NominatimLocationDto NLDchecked = new NominatimLocationDto("", new NominatimLocationDto.Address("", "", "", "", "", ""));

        if (nld.getDisplay_name() == null) {
            NLDchecked.setDisplay_name("");
        } else {
            NLDchecked.setDisplay_name(nld.getDisplay_name());
        }

        if (nld.getAddress() == null) {
            NominatimLocationDto.Address address = new NominatimLocationDto.Address("", "", "", "", "", "");
            NLDchecked.setAddress(address);
        } else {

            if (nld.getAddress().getCountry() == null) {
                NLDchecked.getAddress().setCountry("");
            } else {
                NLDchecked.getAddress().setCountry(nld.getAddress().getCountry());
            }

            if (nld.getAddress().getRegion() == null) {
                NLDchecked.getAddress().setRegion("");
            } else {
                NLDchecked.getAddress().setRegion(nld.getAddress().getRegion());
            }
            if (nld.getAddress().getSuburb() == null) {
                NLDchecked.getAddress().setSuburb("");
            } else {
                NLDchecked.getAddress().setSuburb(nld.getAddress().getSuburb());
            }
            if (nld.getAddress().getCity() == null) {
                NLDchecked.getAddress().setCity("");
            } else {
                NLDchecked.getAddress().setCity(nld.getAddress().getCity());
            }

            if (nld.getAddress().getRoad() == null) {
                NLDchecked.getAddress().setRoad("");
            } else {
                NLDchecked.getAddress().setRoad(nld.getAddress().getRoad());
            }

            if (nld.getAddress().getHouse_number() == null) {
                NLDchecked.getAddress().setHouse_number("");
            } else {
                NLDchecked.getAddress().setHouse_number(nld.getAddress().getHouse_number());
            }
        }

        return NLDchecked;
    }
}
