package ru.practicum.ewm.main_service.category.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class CategoryDto {

    Long id;

    @NotBlank
    @Length(max = 50, min = 1)
    String name;
}