package ru.practicum.ewm.main_service.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.main_service.location.model.Location;

import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventUserRequest {

    @Size(max = 2000, min = 20)
    String annotation;

    Long categoryDto;

    @Size(max = 7000, min = 20)
    String description;

    String eventDate;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    String stateAction;

    @Size(max = 120, min = 3)
    String title;

}