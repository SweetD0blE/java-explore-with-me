package ru.practicum.ewm.main_service.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestStatusUpdateRequest {

    List<Long> requestIds;

    String status;
}