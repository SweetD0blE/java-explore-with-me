package ru.practicum.ewm.main_service.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.main_service.request.model.Request;
import ru.practicum.ewm.main_service.user.model.User;
import ru.practicum.ewm.main_service.utility.TimeUtil;

import java.time.LocalDateTime;

@UtilityClass
public class RequestMapper {

    public static Request newRequest(Event event, User user) {
        return Request.builder()
                .event(event)
                .requester(user)
                .created(LocalDateTime.now())
                .build();
    }

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus().toString())
                .created(TimeUtil.FORMATTER.format(request.getCreated()))
                .build();
    }
}
