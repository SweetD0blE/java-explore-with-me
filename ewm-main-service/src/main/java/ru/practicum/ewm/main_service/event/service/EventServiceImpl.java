package ru.practicum.ewm.main_service.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.mapper.CategoryMapper;
import ru.practicum.ewm.main_service.category.model.Category;
import ru.practicum.ewm.main_service.category.repository.CategoryRepository;
import ru.practicum.ewm.main_service.category.service.CategoryService;
import ru.practicum.ewm.main_service.event.dto.*;
import ru.practicum.ewm.main_service.event.mapper.EventMapper;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.event.repository.EventRepository;
import ru.practicum.ewm.main_service.event.util.EventState;
import ru.practicum.ewm.main_service.event.util.StateAction;
import ru.practicum.ewm.main_service.exception.ConflictException;
import ru.practicum.ewm.main_service.exception.ObjectNotFoundException;
import ru.practicum.ewm.main_service.exception.ValidationException;
import ru.practicum.ewm.main_service.location.repository.LocationRepository;
import ru.practicum.ewm.main_service.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.main_service.request.mapper.RequestMapper;
import ru.practicum.ewm.main_service.request.repository.RequestRepository;
import ru.practicum.ewm.main_service.user.model.User;
import ru.practicum.ewm.main_service.user.repository.UserRepository;
import ru.practicum.ewm.main_service.user.service.UserServiceImpl;
import ru.practicum.ewm.main_service.utility.TimeUtil;
import ru.practicum.ewm.stats_client.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserServiceImpl userService;
    private final CategoryService categoryService;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final StatsClient client;

    @Override
    public Event validatedEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new ObjectNotFoundException(String.format("События с id = %d не существует", eventId)));

    }

    @Override
    public EventFullDto patchDataEventAndState(UpdateEventAdminRequest updateEventAdminRequest, Long eventsId) {
        Event event = validatedEvent(eventsId);
        CategoryDto category = categoryService.findCategoryById(updateEventAdminRequest.getCategory() != null ?
                updateEventAdminRequest.getCategory() : event.getCategory().getId());
        EventFullDto eventFullDto = EventMapper.toUpdateEventAdminRequest(updateEventAdminRequest, event, category);
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Событие уже опубликовано");
        }

        if (event.getState().equals(EventState.CANCELED)) {
            throw new ConflictException("Событие уже отменено");
        }
        if (updateEventAdminRequest.getStateAction() != null) {
            if (updateEventAdminRequest.getStateAction().contains("PUBLISH")) {
                eventFullDto.setState(EventState.PUBLISHED.toString());
                event.setState(EventState.PUBLISHED);
            } else {
                eventFullDto.setState(EventState.CANCELED.toString());
                event.setState(EventState.CANCELED);
            }
        }
        if (updateEventAdminRequest.getEventDate() != null) {
            if (LocalDateTime.parse(updateEventAdminRequest.getEventDate(), TimeUtil.FORMATTER).isBefore(event.getEventDate())) {
                throw new ValidationException("Неправильно указано время");
            }
        }
        eventRepository.save(event);

        return eventFullDto;
    }

    private Event validatedEvent(Long eventId, Long userId) {
        Event event = validatedEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new IllegalArgumentException(String.format("Запрашиваемое событие не принадлежит полльзовутелю с id=%d", userId));
        }
        return event;
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        if (LocalDateTime.parse(newEventDto.getEventDate(), TimeUtil.FORMATTER).isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Неправильно указана дата");
        }
        if (newEventDto.getPaid() == null) {
            newEventDto.setPaid(false);
        }
        if (newEventDto.getRequestModeration() == null) {
            newEventDto.setRequestModeration(true);
        }
        if (newEventDto.getParticipantLimit() == null) {
            newEventDto.setParticipantLimit(0);
        }
        User user = userService.validateUser(userId);
        Category category = CategoryMapper.toCategory(categoryService.findCategoryById(newEventDto.getCategory()));
        Event event = EventMapper.toEvent(newEventDto, user, category);

        locationRepository.save(newEventDto.getLocation());
        event.setState(EventState.PENDING);
        event.setViews(0L);
        event.setConfirmedRequests(0);
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> findAllEventsByUserId(Long userId, int from, int size) {
        PageRequest page = PageRequest.of(from / size, size);
        userService.validateUser(userId);
        List<EventShortDto> eventShortDto = eventRepository.findAllByInitiatorId(userId, page)
                .stream()
                .map(EventMapper::toEventShortDto).collect(Collectors.toList());
        if (eventShortDto.isEmpty()) {
            return Collections.emptyList();
        }
        return eventShortDto;
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto findEventByUserId(Long userId, Long eventId) {
        Event event = validatedEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new IllegalArgumentException(String.format("Запрашиваемое событие не принадлежит полльзовутелю с id=%d", userId));
        }
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto patchEventByUserId(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        User user = userService.validateUser(userId);
        Event event = validatedEvent(eventId, userId);
        if (updateEventUserRequest.getEventDate() != null) {
            if (LocalDateTime.parse(updateEventUserRequest.getEventDate(), TimeUtil.FORMATTER).isBefore(event.getEventDate())) {
                throw new ValidationException("Неправильно указано время");
            }
        }

        if (event.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Событие уже опубликовано");
        }
        if (updateEventUserRequest.getStateAction() != null) {
            if (StateAction.changeStringToState(updateEventUserRequest.getStateAction()) == (StateAction.CANCEL_REVIEW)) {
                event.setState(EventState.CANCELED);
            } else {
                event.setState(EventState.PENDING);
            }
        }

        if (!user.equals(event.getInitiator())) {
            throw new ConflictException(String.format("Пользователь с ID = %d не является инициатором события", userId));
        }
        EventFullDto eventFullDto = EventMapper.toEventFullDto(EventMapper.toEventFullDto(event), updateEventUserRequest);
        eventFullDto.setCategory(updateEventUserRequest.getCategoryDto() != null ? categoryService.findCategoryById(updateEventUserRequest.getCategoryDto()) : CategoryMapper.toCategoryDto(event.getCategory()));
        return eventFullDto;

    }

    @Override
    public void updateConfirmedRequest(int confirmedRequest, Long eventId) {
        eventRepository.updateConfirmedRequest(confirmedRequest, eventId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getAllEvents(List<Long> users, List<EventState> states, List<Long> categories,
                                           LocalDateTime startLocal, LocalDateTime endLocal, Integer from, Integer size) {
        Sort sortByDate = Sort.by(Sort.Direction.ASC, "id");
        PageRequest page = PageRequest.of(from / size, size,sortByDate);
        LocalDateTime start;
        if (users.isEmpty()) {
            users = userRepository.findAll().stream().map(User::getId).collect(Collectors.toList());
        }
        if (startLocal != null) {
            start = startLocal;
        } else {
            start = LocalDateTime.now();
        }
        LocalDateTime end;
        if (endLocal != null) {
            end = endLocal;
        } else {
            end = LocalDateTime.of(3333, 3, 3, 3, 3);
        }
        if (end.isBefore(LocalDateTime.now()) || end.isBefore(start)) {
            throw new IllegalArgumentException("Неправильный запрос");
        }

        List<Event> events = eventRepository.findByInitiatorIdIn(users, page);

        return events.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> findAllEventsByText(SearchObject searchObject, HttpServletRequest request) {
        Sort sortByDate = Sort.by(Sort.Direction.ASC, "id");
        Integer index = searchObject.getFrom() / searchObject.getSize();
        PageRequest page = PageRequest.of(index, searchObject.getSize(), sortByDate);
        LocalDateTime start;

        if (searchObject.getCategories() == null || searchObject.getCategories().isEmpty()) {
             categoryRepository.findAll().stream().map(Category::getId).collect(Collectors.toList());
        }
        if (searchObject.getStartLocal() != null) {
            start = searchObject.getStartLocal();
        } else {
            start = LocalDateTime.now();
        }
        LocalDateTime end;
        if (searchObject.getEndLocal() != null) {
            end = searchObject.getEndLocal();
        } else {
            end = LocalDateTime.of(3333, 3, 3, 3, 3);
        }
        if (end.isBefore(LocalDateTime.now()) || end.isBefore(start)) {
            throw new ValidationException("Неправильный запрос");
        }

        List<Event> events = eventRepository.searchEventsByAnnotationContainsOrDescriptionContainsAndCategoryIdInAndPaidAndCreatedOnBetween(
                       searchObject, page).stream()
                .filter(event -> event.getParticipantLimit() > event.getConfirmedRequests()).collect(Collectors.toList());
        if (searchObject.getSortParam().equals("EVENT_DATE")) {
            events = events.stream().sorted(Comparator.comparing(Event::getEventDate)).collect(Collectors.toList());
        } else if (searchObject.getSortParam().equals("VIEWS")) {
            events = events.stream().sorted(Comparator.comparing(Event::getViews)).collect(Collectors.toList());
        }
        if (events.isEmpty()) {
            return Collections.emptyList();
        }
        client.postHit(request.getRequestURI(),request.getRemoteAddr());

        return events.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto findEventById(Long eventId, HttpServletRequest request) {

        Event event = validatedEvent(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ObjectNotFoundException("Событие не опубликовано");
        }
        client.postHit(request.getRequestURI(), request.getRemoteAddr());
        event.setViews(client.getViews(request.getRequestURI()));
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> findInfoRequestByEvent(Long userId, Long eventId) {
        return requestRepository.findByEventId(eventId)
                .stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

}