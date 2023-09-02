package ru.practicum.ewm.main_service.event.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Collection;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchEventObject {
    String annotation;
    String description;
    Collection<Long> categoryId;
    Boolean paid;
    LocalDateTime start;
    LocalDateTime end;
}
