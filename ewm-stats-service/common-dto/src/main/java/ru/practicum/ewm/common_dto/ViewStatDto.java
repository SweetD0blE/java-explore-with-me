package ru.practicum.ewm.common_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatDto {
    private String app;
    private String uri;
    private Long hits;
}