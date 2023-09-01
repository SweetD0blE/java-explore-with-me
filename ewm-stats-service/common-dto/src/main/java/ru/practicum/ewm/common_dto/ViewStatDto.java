package ru.practicum.ewm.common_dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ViewStatDto {

    String app;

    String uri;

    int hits;
}