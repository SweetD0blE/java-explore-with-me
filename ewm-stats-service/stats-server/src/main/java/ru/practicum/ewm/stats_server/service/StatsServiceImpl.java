package ru.practicum.ewm.stats_server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.common_dto.EndpointHitDto;
import ru.practicum.ewm.common_dto.ViewStatDto;
import ru.practicum.ewm.stats_server.exception.ValidationException;
import ru.practicum.ewm.stats_server.mapper.HitModelMapper;
import ru.practicum.ewm.stats_server.model.HitModel;
import ru.practicum.ewm.stats_server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StatsRepository statsRepository;

    @Override
    public List<ViewStatDto> findStatistic(String start, String end, List<String> uris, boolean unique) {
        LocalDateTime from = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime to = LocalDateTime.parse(end, FORMATTER);
        if (from.isAfter(to)) {
            throw new ValidationException("start is after end");
        }
        if (uris == null || uris.size() == 0 || uris.get(0).equals("events/") || uris.get(0).isBlank()) {
            uris = statsRepository.getDistinctUri();
            log.info("Сколько было запросов {}", uris);
        }
        List<ViewStatDto> listDTO = new ArrayList<>();
        List<String> urisToListUnique;
        List<String> urisToList;
        if (unique) {
            urisToListUnique = statsRepository.findUriByUniqueIp(uris, from, to);
            for (String uri : uris) {
                ViewStatDto statsDTO = new ViewStatDto("ewm-main-service", uri, Collections.frequency(urisToListUnique, uri));
                listDTO.add(statsDTO);
            }

        } else {
            urisToList = statsRepository.getUrisByUri(uris, from, to);
            log.info("Сколько было запросов если не уникальный {}", urisToList);
            for (String uri : uris) {
                ViewStatDto statsDTO = new ViewStatDto("ewm-main-service", uri, Collections.frequency(urisToList, uri));
                listDTO.add(statsDTO);
            }

        }
        listDTO.sort((dto1, dto2) -> dto2.getHits() - dto1.getHits());
        log.info("listDTO = {}", listDTO);
        log.info("Данные " + (listDTO.get(0).getHits()));
        return listDTO;
    }

    @Override
    public EndpointHitDto addHit(EndpointHitDto hitDto) {
        HitModel hit = HitModelMapper.toEndpointHit(hitDto);
        hit.setTimestamp(LocalDateTime.now());
        statsRepository.save(hit);
        log.info("Информация по endpoint = {} сохранена", hit.getUri());
        return validationHit(hit.getId());
    }

    private EndpointHitDto validationHit(Long hitId) {
        return HitModelMapper.toHitDto(statsRepository.findById(hitId).orElseThrow());

    }

}