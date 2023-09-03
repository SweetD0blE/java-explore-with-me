package ru.practicum.ewm.main_service.comment.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.main_service.comment.dto.CommentDto;
import ru.practicum.ewm.main_service.comment.model.Comment;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {
    public static Comment toComment(CommentDto commentDto, User user, Event event) {

    return Comment.builder()
            .id(commentDto.getId())
            .author(user)
            .text(commentDto.getText())
            .event(event)
            .created(LocalDateTime.now())
            .build();
}

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .authorId(comment.getAuthor().getId())
                .text(comment.getText())
                .eventId(comment.getEvent().getId())
                .created(comment.getCreated())
                .edited(comment.getEdited())
                .build();
    }

    public static Comment toCommentUpdated(CommentDto commentDto, Comment comment, User user, Event event) {

        return Comment.builder()
                .id(comment.getId())
                .author(user)
                .text(commentDto.getText())
                .event(event)
                .created(comment.getCreated())
                .edited(LocalDateTime.now())
                .build();
    }

}