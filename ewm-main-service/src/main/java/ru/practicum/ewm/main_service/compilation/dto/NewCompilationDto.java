package ru.practicum.ewm.main_service.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {

    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;

    private Set<Long> events;
}