package ru.practicum.ewm.main_service.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.comment.service.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/comments")
public class CommentAdminController {

    private final CommentService commentService;

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeComment(@PathVariable Long commentId) {
        commentService.deleteCommentByAdmin(commentId);
    }
}