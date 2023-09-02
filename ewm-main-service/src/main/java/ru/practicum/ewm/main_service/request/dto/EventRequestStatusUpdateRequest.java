package ru.practicum.ewm.main_service.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class EventRequestStatusUpdateRequest {

    List<Long> requestIds;

    String status;
}