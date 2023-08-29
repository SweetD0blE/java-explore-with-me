package ru.practicum.ewm.main_service.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.main_service.event.dto.ParticipationRequestDto;
import ru.practicum.ewm.main_service.event.model.Request;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    ParticipationRequestDto toParticipationRequestDto(Request request);
}