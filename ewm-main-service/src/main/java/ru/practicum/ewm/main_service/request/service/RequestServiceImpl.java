package ru.practicum.ewm.main_service.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.event.repository.EventRepository;
import ru.practicum.ewm.main_service.event.service.EventService;
import ru.practicum.ewm.main_service.event.util.EventState;
import ru.practicum.ewm.main_service.exception.ConflictException;
import ru.practicum.ewm.main_service.exception.ObjectNotFoundException;
import ru.practicum.ewm.main_service.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main_service.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main_service.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.main_service.request.mapper.RequestMapper;
import ru.practicum.ewm.main_service.request.model.Request;
import ru.practicum.ewm.main_service.request.repository.RequestRepository;
import ru.practicum.ewm.main_service.request.util.RequestStatus;
import ru.practicum.ewm.main_service.user.model.User;
import ru.practicum.ewm.main_service.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {

    private final UserService userService;
    private final EventService eventService;
    private final RequestRepository requestRepository;
    public final EventRepository eventRepository;

    public Request validateRequest(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Запроса на участие с id = %d не существует", requestId)));
    }

    @Override
    public ParticipationRequestDto addRequestByCurrentUser(Long userId, Long eventId) {
        User user = userService.validateUser(userId);
        Event event = eventService.validatedEvent(eventId);
        Request request = RequestMapper.newRequest(event, user);
        request.setRequester(user);

        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ConflictException("Запрашивающий является инициатором события");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Событие не опубликовано");
        }

        List<Request> requestList = requestRepository.findByRequester(user);
        if (!requestList.isEmpty()) {
            for (Request e : requestList
            ) {
                if (e.getEvent().equals(event)) {
                    throw new ConflictException("Запрос уже сутществует");
                }
            }
        }
        if (event.getConfirmedRequests() >= event.getParticipantLimit() && event.getParticipantLimit() != 0) {
            throw new ConflictException("Превышен лимит участников");
        }
        request.setEvent(event);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }
        requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public ParticipationRequestDto patchRequestByCurrentUser(Long userId, Long requestId) {
        User user = userService.validateUser(userId);
        Request request = validateRequest(requestId);
        if (request.getRequester().equals(user)) {
            request.setStatus(RequestStatus.CANCELED);
        } else {
            throw new IllegalArgumentException("Нельзя отменить запрос не принадлежащий пользователю");
        }

        requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> findInformationAboutRequestByCurrentUser(Long userId) {
        return requestRepository.findAllByRequesterId(userId)
                .stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult patchStatusRequestByCurrentUser(EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest, Long userId, Long eventId) {
        Event event = eventService.validatedEvent(eventId);
        if (event.getConfirmedRequests() >= event.getParticipantLimit() && event.getParticipantLimit() != 0) {
            throw new ConflictException("Подтевржденные запросы закончились");
        }
        List<Long> requestIds = eventRequestStatusUpdateRequest.getRequestIds();
        List<Request> requests = requestRepository.findAllById(requestIds);
        RequestStatus requestStatus  = RequestStatus.changeStringToState(eventRequestStatusUpdateRequest.getStatus());

        if (requests.stream().anyMatch(request -> request.getStatus() != RequestStatus.PENDING))
            throw new IllegalArgumentException(
                    "Статус можно изменить только у заявок, находящихся в состоянии ожидания"
            );

        List<ParticipationRequestDto> confirmedParticipationRequest = new ArrayList<>();
        List<ParticipationRequestDto> rejectedParticipationRequest = new ArrayList<>();
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();
        if (requestStatus == RequestStatus.CONFIRMED)
            requests.forEach(request -> {
                if (event.getConfirmedRequests() <= event.getParticipantLimit()) {
                    request.setStatus(RequestStatus.CONFIRMED);
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                } else
                    request.setStatus(RequestStatus.REJECTED);
            });
        else
            requests.forEach(request ->
                    request.setStatus(RequestStatus.REJECTED)
            );

        for (Request request : requests) {
            if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
                confirmedParticipationRequest.add(RequestMapper.toParticipationRequestDto(request));
            } else {
                rejectedParticipationRequest.add(RequestMapper.toParticipationRequestDto(request));
            }
        }
        eventRequestStatusUpdateResult.setConfirmedRequests(confirmedParticipationRequest);
        eventRequestStatusUpdateResult.setRejectedRequests(rejectedParticipationRequest);
        eventService.updateConfirmedRequest(confirmedParticipationRequest.size(), eventId);
        return eventRequestStatusUpdateResult;
    }

}