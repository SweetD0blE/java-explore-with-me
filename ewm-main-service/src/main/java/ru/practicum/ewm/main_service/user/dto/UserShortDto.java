package ru.practicum.ewm.main_service.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class UserShortDto {

    Long id;

    @NotBlank
    @NotEmpty
    @Length(max = 250, min = 2)
    String name;

}