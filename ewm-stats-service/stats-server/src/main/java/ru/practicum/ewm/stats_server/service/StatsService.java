package ru.practicum.ewm.stats_server.service;

import ru.practicum.ewm.common_dto.EndpointHitDto;
import ru.practicum.ewm.common_dto.ViewStatDto;

import java.util.List;

public interface StatsService {

    List<ViewStatDto> findStatistic(String start, String end, List<String> uris, boolean unique);

    EndpointHitDto addHit(EndpointHitDto statDto);

}