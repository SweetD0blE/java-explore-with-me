package ru.practicum.ewm.common_dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class EndpointHitDto {

    Long id;

    @NotBlank
    String app;

    @NotBlank
    String uri;

    @NotBlank
    String ip;

    String timestamp;

}