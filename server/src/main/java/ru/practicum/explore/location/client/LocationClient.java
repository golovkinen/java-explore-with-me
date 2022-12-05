package ru.practicum.explore.location.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore.client.BaseClient;
import ru.practicum.explore.location.dto.NominatimLocationDto;

import java.util.Map;

@Service

public class LocationClient extends BaseClient {

    @Autowired
    public LocationClient(@Value("https://nominatim.openstreetmap.org/reverse?format=jsonv2") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public NominatimLocationDto getLocationDetails(double lat, double lon) {
        Map<String, Object> parameters = Map.of(
                "lat", lat,
                "lon", lon
        );
        return getLoc("https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=" + lat + "&lon=" + lon, parameters);
    }
}
