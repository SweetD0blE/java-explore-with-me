package ru.practicum.ewm.main_service.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.main_service.location.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {

    @Size(max = 2000, min = 20)
    @NotNull
    @NotBlank
    String annotation;

    Long category;

    @Size(max = 7000, min = 20)
    @NotNull
    String description;

    String eventDate;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    @Size(max = 120, min = 3)
    String title;

}