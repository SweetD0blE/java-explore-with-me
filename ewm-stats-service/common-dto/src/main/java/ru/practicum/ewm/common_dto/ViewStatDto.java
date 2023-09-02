package ru.practicum.ewm.common_dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@ToString
public final class ViewStatDto {

    String app;

    String uri;

    int hits;
}