package ru.practicum.ewm.stats_server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.common_dto.EndpointHitDto;
import ru.practicum.ewm.common_dto.ViewStatDto;
import ru.practicum.ewm.stats_server.service.StatsServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsServiceImpl service;

    @PostMapping("/hit")
    public ResponseEntity<EndpointHitDto> createHit(@RequestBody EndpointHitDto hitDto) {
        log.info("Создан запрос на сохранение информации к эндпоинту");
        return new ResponseEntity<>(service.addHit(hitDto), HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public List<ViewStatDto> getAllStatistic(@RequestParam(value = "start") String start,
                                             @RequestParam(value = "end") String end,
                                             @RequestParam(value = "uris", defaultValue = "") List<String> uris,
                                             @RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        log.info("Создан запрос на получение статистики посещаемости");
        return service.findStatistic(start, end, uris, unique);
    }

}