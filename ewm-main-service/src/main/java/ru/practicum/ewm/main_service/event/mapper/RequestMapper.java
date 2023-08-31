package ru.practicum.ewm.main_service.event.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.main_service.event.dto.ParticipationRequestDto;
import ru.practicum.ewm.main_service.event.model.Request;

@Component
public interface RequestMapper {

    ParticipationRequestDto toParticipationRequestDto(Request request);
}