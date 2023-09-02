package ru.practicum.ewm.main_service.event.service;

import ru.practicum.ewm.main_service.event.dto.*;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.request.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    List<EventShortDto> findAllEventsByUserId(Long userId, int from, int size);

    EventFullDto findEventByUserId(Long userId, Long eventId);

    EventFullDto patchEventByUserId(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    Event validatedEvent(Long eventId);

    EventFullDto patchDataEventAndState(UpdateEventAdminRequest updateEventAdminRequest, Long eventsId);

    void updateConfirmedRequest(int confirmedRequest, Long eventId);

    List<EventFullDto> getAllEvents(SearchObject searchObject);

    List<EventShortDto> findAllEventsByText(SearchObject searchObject, HttpServletRequest request);

    EventFullDto findEventById(Long eventId, HttpServletRequest request);

    List<ParticipationRequestDto> findInfoRequestByEvent(Long userId, Long eventId);
}