package ru.practicum.ewm.common_dto;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ViewStatDto {

    private final String app;
    private final String uri;
    private final long hits;

}