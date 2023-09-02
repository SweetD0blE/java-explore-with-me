package ru.practicum.ewm.main_service.location.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class LocationDto {

    Float lat;

    Float lon;
}