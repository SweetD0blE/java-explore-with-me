package ru.practicum.ewm.main_service.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main_service.category.model.Category;
import ru.practicum.ewm.main_service.event.model.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiatorId(Long userId, PageRequest pageRequest);

    @Modifying
    @Query(value = "UPDATE event " +
            "SET confirmed_requests = ?1 " +
            "WHERE id = ?2 ",
            nativeQuery = true)
    void updateConfirmedRequest(int confirmedRequest, Long eventId);

    List<Event> findByInitiatorIdIn(List<Long> users, PageRequest pageRequest);

    List<Event> searchEventsByAnnotationContainsOrDescriptionContainsAndCategoryIdInAndPaidAndCreatedOnBetween(String annotation, String description, Collection<Long> categoryId, Boolean paid, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    Optional<Event> findByCategory(Category category);

    List<Event> findAllByIdIn(List<Long> id);
}