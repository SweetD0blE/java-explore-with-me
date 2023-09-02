package ru.practicum.ewm.main_service.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;
import ru.practicum.ewm.main_service.event.dto.SearchObject;
import ru.practicum.ewm.main_service.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventPublicController {
    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> findAllEventsByText(SearchObject searchObject, HttpServletRequest request) {
        return eventService.findAllEventsByText(searchObject, request);

    }

    @GetMapping("{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto findEventById(@PathVariable("eventId") Long eventId, HttpServletRequest request) {
        return eventService.findEventById(eventId, request);
    }
}