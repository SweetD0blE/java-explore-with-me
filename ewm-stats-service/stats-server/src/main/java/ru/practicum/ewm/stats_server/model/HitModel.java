package ru.practicum.ewm.stats_server.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
@Table(name = "stats")
public class HitModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id")
    Long id;

    @Column(name = "app")
    String app;

    @Column(name = "uri")
    String uri;

    @Column(name = "ip")
    String ip;

    @Column(name = "timestamp")
    LocalDateTime timestamp;

}