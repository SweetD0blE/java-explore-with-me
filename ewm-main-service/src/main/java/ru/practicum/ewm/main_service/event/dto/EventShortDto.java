package ru.practicum.ewm.main_service.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.user.dto.UserShortDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {

    String annotation;

    CategoryDto category;

    int confirmedRequests;

    String eventDate;

    Long id;

    UserShortDto initiator;

    Boolean paid;

    String title;

    Long views;

}