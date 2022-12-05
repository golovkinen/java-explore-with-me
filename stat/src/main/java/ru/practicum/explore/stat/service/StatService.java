package ru.practicum.explore.stat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.explore.stat.dto.HitDto;
import ru.practicum.explore.stat.dto.ViewStatDto;
import ru.practicum.explore.stat.mapper.StatMapper;
import ru.practicum.explore.stat.repository.IStatRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatService implements IStatService {

    private final IStatRepository iStatRepository;


    public StatService(IStatRepository iStatRepository) {
        this.iStatRepository = iStatRepository;
    }

    @Override
    public HttpStatus create(HitDto hitDto) {

        iStatRepository.save(StatMapper.toStat(hitDto));
        return HttpStatus.OK;
    }

    @Override
    public List<ViewStatDto> getStat(String start, String end, String uris, Boolean unique) {

        String startDecoded;
        String endDecoded;
        String urisDecoded;

        try {
            startDecoded = decode(start);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            endDecoded = decode(end);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            urisDecoded = decode(uris);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        LocalDateTime startT = StatMapper.toDateTime(startDecoded);
        LocalDateTime endT = StatMapper.toDateTime(endDecoded);

        List<String> urisList = Arrays.asList(urisDecoded.split(",", -1));

        if (unique) {

            return urisList.stream().map(u -> StatMapper.toViewStatDto(iStatRepository.getAppName(u), u, iStatRepository.getUriStatDistinct(u, startT, endT))).collect(Collectors.toList());
        }

        return urisList.stream().map(u -> StatMapper.toViewStatDto(iStatRepository.getAppName(u), u, iStatRepository.getUriStatAll(u, startT, endT))).collect(Collectors.toList());

    }

    private String decode(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }

}


