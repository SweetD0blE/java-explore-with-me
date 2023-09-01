package ru.practicum.ewm.main_service.request.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.request.util.RequestStatus;
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
@Table(name = "REQUESTS")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created")
    LocalDateTime created;

    @JoinColumn(name = "event")
    @ManyToOne(fetch = FetchType.LAZY)
    Event event;

    @JoinColumn(name = "requester")
    @ManyToOne(fetch = FetchType.LAZY)
    User requester;

    @Column(name = "status")
    RequestStatus status;

}