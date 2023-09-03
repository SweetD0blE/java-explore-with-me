package ru.practicum.ewm.main_service.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ParticipationRequestDto {

    String created;

    Long event;

    Long id;

    Long requester;

    String status;

}