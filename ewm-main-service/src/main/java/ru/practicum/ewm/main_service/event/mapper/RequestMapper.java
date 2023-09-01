package ru.practicum.ewm.main_service.event.mapper;

import ru.practicum.ewm.main_service.event.dto.ParticipationRequestDto;
import ru.practicum.ewm.main_service.event.model.Request;


public interface RequestMapper {


    ParticipationRequestDto toParticipationRequestDto(Request request);
}