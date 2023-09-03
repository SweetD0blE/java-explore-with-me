package ru.practicum.ewm.main_service.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;
import ru.practicum.ewm.main_service.event.dto.NewEventDto;
import ru.practicum.ewm.main_service.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.main_service.event.service.EventService;
import ru.practicum.ewm.main_service.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class EventController {

    private final EventService eventService;

    @PostMapping("{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    private EventFullDto addEvent(@Valid @RequestBody NewEventDto newEventDto,
                                  @PathVariable("userId") Long userId) {
        log.info("Запрос на создание мероприятия");
        return eventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events")
    private List<EventShortDto> findAllEventsByUser(@PathVariable("userId") Long userId,
                                                    @RequestParam(name = "from", defaultValue = "0")
                                                    @PositiveOrZero int from,
                                                    @RequestParam(name = "size", defaultValue = "10")
                                                    @Positive int size) {
        log.info("Запрос на получение всех мероприятий пользователя с id {}", userId);
        return eventService.findAllEventsByUserId(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    private EventFullDto findAllEventsByUser(@PathVariable("userId") Long userId,
                                             @PathVariable("eventId") Long eventId) {
        log.info("Запрос на получение всех мероприятий пользователя с id {}", userId);
        return eventService.findEventByUserId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    private EventFullDto patchEvent(@PathVariable("userId") Long userId,
                                    @PathVariable("eventId") Long eventId,
                                    @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("Запрос на изменение события добавленного текущим пользователем с id {}", userId);
        return eventService.patchEventByUserId(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    private List<ParticipationRequestDto> findInfoRequestByEvent(@PathVariable("userId") Long userId,
                                                                 @PathVariable("eventId") Long eventId) {
        return eventService.findInfoRequestByEvent(userId, eventId);
    }
}