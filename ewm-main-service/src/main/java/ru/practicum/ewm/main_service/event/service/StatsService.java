package ru.practicum.ewm.main_service.event.service;


import ru.practicum.ewm.common_dto.ViewStatDto;
import ru.practicum.ewm.main_service.event.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatsService {
    void addHit(HttpServletRequest request);

    List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    Map<Long, Long> getViews(List<Event> events);

    Map<Long, Long> getConfirmedRequests(List<Event> events);
}