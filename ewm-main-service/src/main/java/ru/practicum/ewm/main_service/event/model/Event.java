package ru.practicum.ewm.main_service.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.main_service.category.model.Category;
import ru.practicum.ewm.main_service.event.util.EventState;
import ru.practicum.ewm.main_service.location.model.Location;
import ru.practicum.ewm.main_service.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "EVENT")
public class Event {

    @Column(name = "annotation")
    String annotation;

    @JoinColumn(name = "category")
    @ManyToOne(fetch = FetchType.LAZY)
    Category category;

    @Column(name = "confirmed_requests")
    int confirmedRequests;

    @Column(name = "created_on")
    LocalDateTime createdOn;

    @Column(name = "description")
    String description;

    @Column(name = "event_date")
    LocalDateTime eventDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @JoinColumn(name = "initiator")
    @ManyToOne(fetch = FetchType.LAZY)
    User initiator;

    @JoinColumn(name = "location")
    @ManyToOne
    Location location;

    @Column(name = "paid")
    Boolean paid;

    @Column(name = "participant_limit")
    Integer participantLimit;

    @Column(name = "published_on")
    LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    Boolean requestModeration;

    @Column(name = "state")
    EventState state;

    @Column(name = "title")
    String title;

    @Column(name = "views")
    Long views;

}