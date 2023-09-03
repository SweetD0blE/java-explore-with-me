package ru.practicum.ewm.main_service.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompilationDto {

    private Long id;

    private boolean pinned;

    private String title;

    private List<EventShortDto> events;

}