package ru.practicum.ewm.main_service.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.comment.dto.CommentDto;
import ru.practicum.ewm.main_service.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event/{eventId}/comments")
public class CommentPrivateController {

    private final CommentService commentService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@RequestParam Long userId,
                                    @Valid @RequestBody CommentDto commentDto,
                                    @PathVariable Long eventId) {
        return commentService.createComment(commentDto, userId, eventId);
    }

    @PatchMapping("{commentId}")
    public CommentDto updateComment(@RequestParam Long userId,
    @PathVariable Long eventId, @PathVariable Long commentId, @Valid @RequestBody CommentDto commentDto) {
        return commentService.updateComment(userId, eventId, commentId, commentDto);
    }

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeComment(@RequestParam Long userId,
                              @PathVariable Long eventId,
                              @PathVariable Long commentId) {
        commentService.deleteComment(userId, eventId, commentId);
    }

    @GetMapping()
    public List<CommentDto> findAllCommentsByEvent(@PathVariable Long eventId,
                                                   @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                   @Positive @RequestParam(defaultValue = "10") Integer size) {
        return commentService.findAllCommentsByEvent(eventId, from, size);
    }
}