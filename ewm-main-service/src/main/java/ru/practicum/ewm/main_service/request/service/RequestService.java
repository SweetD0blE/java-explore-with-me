package ru.practicum.ewm.main_service.request.service;

import ru.practicum.ewm.main_service.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main_service.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main_service.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.main_service.request.model.Request;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto addRequestByCurrentUser(Long userId, Long eventId);

    ParticipationRequestDto patchRequestByCurrentUser(Long userId, Long requestId);

    Request validateRequest(Long requestId);

    List<ParticipationRequestDto> findInformationAboutRequestByCurrentUser(Long userId);

    EventRequestStatusUpdateResult patchStatusRequestByCurrentUser(EventRequestStatusUpdateRequest eventRequestsStatusUpdateRequest, Long userId, Long eventId);
}