package ru.practicum.ewm.main_service.event.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchObject {

    String text;

    List<Long> categories;

    Boolean paid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startLocal;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endLocal;

    Boolean onlyAvailable = false;

    String sortParam;

    @PositiveOrZero
    Integer from = 0;

    @Positive
    Integer size = 11;
}
