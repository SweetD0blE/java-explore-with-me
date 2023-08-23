package ru.practicum.ewm.stats_server.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "stats")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String app;

    String uri;

    long hits;

    public StatsModel(String app, String uri, long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }

}