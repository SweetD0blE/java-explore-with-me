package ru.practicum.ewm.stats_server.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.common_dto.EndpointHitDto;
import ru.practicum.ewm.stats_server.model.HitModel;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HitModelMapper {

    public static HitModel toEndpointHit(EndpointHitDto endpointHitDto) {
        return HitModel.builder()
                .app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri())
                .ip(endpointHitDto.getIp())
                .build();
    }

}