package ru.practicum.ewm.main_service.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.main_service.event.service.EventService;
import ru.practicum.ewm.main_service.event.util.EventState;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/admin/events")
public class EventAdminController {

    private final EventService eventService;

    @PatchMapping("{eventsId}")
    public EventFullDto patchDataEventAndState(@RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest,
                                               @PathVariable("eventsId") Long eventsId) {
        log.info("Запрос на редактирование данный события и его статуста (отклонение/публикация)");
        return eventService.patchDataEventAndState(updateEventAdminRequest, eventsId);
    }

    @GetMapping
    public List<EventFullDto> getAllEvents(
            @RequestParam(name = "users", defaultValue = "") List<Long> users,
            @RequestParam(name = "states", defaultValue = "") List<EventState> states,
            @RequestParam(value = "categories", defaultValue = "") List<Long> categories,
            @RequestParam(required = false, name = "rangeStart")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startLocal,
            @RequestParam(required = false, name = "rangeEnd")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endLocal,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("from = {}, size = {}", from, size);
        return eventService.getAllEvents(users, states, categories, startLocal, endLocal, from, size);
    }
}