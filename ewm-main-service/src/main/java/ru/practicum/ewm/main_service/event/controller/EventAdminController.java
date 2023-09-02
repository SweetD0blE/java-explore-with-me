package ru.practicum.ewm.main_service.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.SearchObject;
import ru.practicum.ewm.main_service.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.main_service.event.service.EventService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
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
    public List<EventFullDto> getAllEvents(SearchObject searchObject) {
        log.info("from = {}, size = {}", searchObject.getFrom(), searchObject.getSize());
        return eventService.getAllEvents(searchObject);
    }

}