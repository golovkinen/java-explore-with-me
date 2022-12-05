package ru.practicum.explore.event.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore.client.BaseClient;
import ru.practicum.explore.datetimeformatter.DateTimeFormatter;
import ru.practicum.explore.event.dto.HitDto;
import ru.practicum.explore.event.dto.ViewStatDto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class EventClient extends BaseClient {

    @Autowired
    public EventClient(@Value("http://localhost:9090") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public List<ViewStatDto> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        String encodedStart;
        String encodedEnd;
        String encodedUris;

        try {
            encodedStart = encodeValue(DateTimeFormatter.dateTimeToString(start));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            encodedEnd = encodeValue(DateTimeFormatter.dateTimeToString(end));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        if (!uris.isEmpty()) {

            String urisString = String.join(",", uris);

            try {
                encodedUris = encodeValue(urisString);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            return getStat("/stats?start=" + encodedStart + "&end=" + encodedEnd + "&uris=" + encodedUris + "&unique=" + unique);
        }

        return getStat("/stats?start=" + encodedStart + "&end=" + encodedEnd + "&unique=" + unique);
    }

    public void sendStat(HitDto hitDto) {
        post("/hit", hitDto);
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
}
