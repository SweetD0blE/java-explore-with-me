package ru.practicum.ewm.main_service.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.mapper.CategoryMapper;
import ru.practicum.ewm.main_service.category.model.Category;
import ru.practicum.ewm.main_service.event.dto.*;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.user.mapper.UserMapper;
import ru.practicum.ewm.main_service.user.model.User;
import ru.practicum.ewm.main_service.utility.TimeUtil;

import java.time.LocalDateTime;


@UtilityClass
public class EventMapper {


    public static Event toEvent(NewEventDto newEventDto, User user, Category category) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .category(category)
                .createdOn(LocalDateTime.now())
                .publishedOn(LocalDateTime.now())
                .description(newEventDto.getDescription())
                .eventDate(LocalDateTime.parse(newEventDto.getEventDate(), TimeUtil.FORMATTER))
                .location(newEventDto.getLocation())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .initiator(user)
                .build();
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(LocalDateTime.now().format(TimeUtil.FORMATTER))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(TimeUtil.FORMATTER))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn() == null ? "" : event.getPublishedOn().format(TimeUtil.FORMATTER))
                .requestModeration(event.getRequestModeration())
                .state(event.getState().toString() == null ? "" : event.getState().toString())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }


    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(TimeUtil.FORMATTER))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();

    }

    public static EventFullDto toEventFullDto(EventFullDto eventFullDto, UpdateEventUserRequest updateEventUserRequest) {
        return EventFullDto.builder()
                .id(eventFullDto.getId())
                .annotation(updateEventUserRequest.getAnnotation() != null ? updateEventUserRequest.getAnnotation()
                        : eventFullDto.getAnnotation())
                .description(updateEventUserRequest.getDescription() != null ? updateEventUserRequest.getDescription()
                        : eventFullDto.getDescription())
                .eventDate(updateEventUserRequest.getEventDate() != null ? updateEventUserRequest.getEventDate()
                        : eventFullDto.getEventDate())
                .location(updateEventUserRequest.getLocation() != null ? updateEventUserRequest.getLocation()
                        : eventFullDto.getLocation())
                .paid(updateEventUserRequest.getPaid() != null ? updateEventUserRequest.getPaid()
                        : eventFullDto.getPaid())
                .participantLimit(updateEventUserRequest.getParticipantLimit() != null ? updateEventUserRequest.getParticipantLimit()
                        : eventFullDto.getParticipantLimit())
                .title(updateEventUserRequest.getTitle() != null ? updateEventUserRequest.getTitle()
                        : eventFullDto.getTitle())
                .confirmedRequests(eventFullDto.getConfirmedRequests())
                .createdOn(eventFullDto.getCreatedOn())
                .initiator((eventFullDto.getInitiator()))
                .publishedOn(eventFullDto.getPublishedOn())
                .requestModeration(eventFullDto.getRequestModeration())
                .state(eventFullDto.getState())
                .views(eventFullDto.getViews())
                .build();
    }

    public static EventFullDto toUpdateEventAdminRequest(UpdateEventAdminRequest updateEventAdminRequest, Event event, CategoryDto categoryDto) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(updateEventAdminRequest.getAnnotation() != null ? updateEventAdminRequest.getAnnotation()
                        : event.getAnnotation())
                .category(categoryDto)
                .description(updateEventAdminRequest.getDescription() != null ? updateEventAdminRequest.getDescription()
                        : event.getDescription())
                .eventDate(updateEventAdminRequest.getEventDate() != null ? updateEventAdminRequest.getEventDate()
                        : (TimeUtil.FORMATTER.format(event.getEventDate())))
                .location(updateEventAdminRequest.getLocation() != null ? updateEventAdminRequest.getLocation()
                        : event.getLocation())
                .paid(updateEventAdminRequest.getPaid() != null ? updateEventAdminRequest.getPaid()
                        : event.getPaid())
                .participantLimit(updateEventAdminRequest.getParticipantLimit() != null ? updateEventAdminRequest.getParticipantLimit()
                        : event.getParticipantLimit())
                .requestModeration(updateEventAdminRequest.getRequestModeration() != null ? updateEventAdminRequest.getRequestModeration()
                        : event.getRequestModeration())
                .title(updateEventAdminRequest.getTitle() != null ? updateEventAdminRequest.getTitle()
                        : event.getTitle())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(TimeUtil.FORMATTER.format(event.getCreatedOn()))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .publishedOn(TimeUtil.FORMATTER.format(event.getPublishedOn()))
                .views(event.getViews())
                .build();
    }

}