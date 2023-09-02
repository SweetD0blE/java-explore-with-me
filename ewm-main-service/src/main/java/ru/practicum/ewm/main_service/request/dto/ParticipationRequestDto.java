package ru.practicum.ewm.main_service.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class ParticipationRequestDto {

    String created;

    Long event;

    Long id;

    Long requester;

    String status;

}