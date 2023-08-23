package ru.practicum.ewm.stats_server.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.common_dto.ViewStatDto;
import ru.practicum.ewm.stats_server.model.StatsModel;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsModelMapper {

    public static ViewStatDto toViewStatDto(StatsModel viewStat) {
        return ViewStatDto.builder()
                .app(viewStat.getApp())
                .uri(viewStat.getUri())
                .hits(viewStat.getHits())
                .build();
    }

}