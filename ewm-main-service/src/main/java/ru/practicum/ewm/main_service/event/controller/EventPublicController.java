package ru.practicum.ewm.main_service.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;
import ru.practicum.ewm.main_service.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Validated
public class EventPublicController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> findAllEventsByText(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false, name = "rangeStart")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startLocal,
            @RequestParam(required = false, name = "rangeEnd")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endLocal,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(defaultValue = "EVENT_DATE", name = "sort") String sortParam,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "11") Integer size, HttpServletRequest request) {
        return eventService.findAllEventsByText(text, categories, paid, startLocal, endLocal, onlyAvailable, sortParam, from, size,request);

    }

    @GetMapping("{eventId}")
    public EventFullDto findEventById(@PathVariable("eventId") Long eventId, HttpServletRequest request) {
        return eventService.findEventById(eventId, request);
    }
}