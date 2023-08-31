package ru.practicum.ewm.main_service.event.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.main_service.event.dto.LocationDto;
import ru.practicum.ewm.main_service.event.model.Location;

@Component
public interface LocationMapper {

    Location toLocation(LocationDto locationDto);

    LocationDto toLocationDto(Location location);
}