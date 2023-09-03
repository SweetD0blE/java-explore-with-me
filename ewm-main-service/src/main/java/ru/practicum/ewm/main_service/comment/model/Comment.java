package ru.practicum.ewm.main_service.comment.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "COMMENTS")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "text")
    String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    User author;

    @Column(name = "created")
    LocalDateTime created;

    @Column(name = "edited")
    LocalDateTime edited;

}