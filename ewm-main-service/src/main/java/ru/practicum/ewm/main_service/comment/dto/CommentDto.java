package ru.practicum.ewm.main_service.comment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentDto {

    Long id;

    @NotBlank(message = "Комментарий не может быть пустым")
    String text;

    Long authorId;

    Long eventId;

    LocalDateTime created;

    LocalDateTime edited;

}