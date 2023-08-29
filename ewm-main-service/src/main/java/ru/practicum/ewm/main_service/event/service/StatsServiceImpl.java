package ru.practicum.ewm.main_service.event.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common_dto.EndpointHitDto;
import ru.practicum.ewm.common_dto.ViewStatDto;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.event.repository.RequestRepository;
import ru.practicum.ewm.stats_client.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsClient statsClient;
    private final RequestRepository requestRepository;
    private final EndpointHitDto endpointHitDto;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value(value = "${app.name}")
    private String appName;

    @Override
    public void addHit(HttpServletRequest request) {
        log.info("Отправлен запрос на регистрацию обращения к серверу статистики с параметрами request = {}", request);
        statsClient.addHit(endpointHitDto);
    }

    @Override
    public List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("Отправлен запрос на получение статистики к серверу статистики с параметрами " +
                "start = {}, end = {}, uris = {}, unique = {}", start, end, uris, unique);


        List<ViewStatDto> response = statsClient.getStats(start, end, uris, unique);

        return response;
    }

    @Override
    public Map<Long, Long> getViews(List<Event> events) {
        log.info("Отправлен запрос на получение статистики неуникальных посещений в виде Map<eventId, count> " +
                "для списка событий.");

        Map<Long, Long> views = new HashMap<>();

        List<Event> publishedEvents = getPublished(events);

        if (events.isEmpty()) {
            return views;
        }

        Optional<LocalDateTime> minPublishedOn = publishedEvents.stream()
                .map(Event::getPublishedOn)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo);

        if (minPublishedOn.isPresent()) {
            LocalDateTime start = minPublishedOn.get();
            LocalDateTime end = LocalDateTime.now();
            List<String> uris = publishedEvents.stream()
                    .map(Event::getId)
                    .map(id -> ("/events/" + id))
                    .collect(Collectors.toList());

            List<ViewStatDto> stats = getStats(start, end, uris, true);
            stats.forEach(stat -> {
                Long eventId = Long.parseLong(stat.getUri()
                        .split("/", 0)[2]);
                views.put(eventId, views.getOrDefault(eventId, 0L) + stat.getHits());
            });
        }

        return views;
    }

    @Override
    public Map<Long, Long> getConfirmedRequests(List<Event> events) {
        List<Long> eventsId = getPublished(events).stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        Map<Long, Long> requestStats = new HashMap<>();

        if (!eventsId.isEmpty()) {
            requestRepository.getConfirmedRequests(eventsId)
                    .forEach(stat -> requestStats.put(stat.getEventId(), stat.getConfirmedRequests()));
        }

        return requestStats;
    }

    private List<Event> getPublished(List<Event> events) {
        return events.stream()
                .filter(event -> event.getPublishedOn() != null)
                .collect(Collectors.toList());
    }
}