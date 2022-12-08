package ru.practicum.explore.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explore.event.dto.ViewStatDto;
import ru.practicum.explore.location.dto.NominatimLocationDto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }


    protected List<ViewStatDto> getStat(String path) {
        return requestForStat(path);
    }

    protected NominatimLocationDto getLoc(String path) {
        return requestForLocationDetails(path);
    }


    protected <T> void post(String path, T body) {

        HttpEntity<T> requestEntity = new HttpEntity<>(body);

        try {
            rest.exchange(path, HttpMethod.POST, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
    }

    private List<ViewStatDto> requestForStat(String path) {

        ResponseEntity<ViewStatDto[]> serverResponse = rest.getForEntity(path, ViewStatDto[].class);

        ViewStatDto[] viewStatDtos = serverResponse.getBody();

        return Arrays.asList(viewStatDtos);
    }

    private NominatimLocationDto requestForLocationDetails(String path) {

        ResponseEntity<NominatimLocationDto> serverResponse;

        try {
            serverResponse = rest.getForEntity(path, NominatimLocationDto.class);
        } catch (HttpStatusCodeException e) {
            return new NominatimLocationDto(null, null);
        }

        return Objects.requireNonNull(serverResponse.getBody());

    }
}
