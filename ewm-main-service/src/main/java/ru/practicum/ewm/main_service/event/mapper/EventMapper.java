package ru.practicum.ewm.main_service.event.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main_service.category.mapper.CategoryMapper;
import ru.practicum.ewm.main_service.category.model.Category;
import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;
import ru.practicum.ewm.main_service.event.dto.NewEventDto;
import ru.practicum.ewm.main_service.event.enums.EventState;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.event.model.Location;
import ru.practicum.ewm.main_service.user.mapper.UserMapper;
import ru.practicum.ewm.main_service.user.model.User;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class, LocationMapper.class})
public interface EventMapper {

    Event toEvent(NewEventDto newEventDto, User initiator, Category category, Location location, LocalDateTime createdOn, EventState state);

    EventFullDto toEventFullDto(Event event, Long confirmedRequests, Long views);

    EventShortDto toEventShortDto(Event event, Long confirmedRequests, Long views);
}