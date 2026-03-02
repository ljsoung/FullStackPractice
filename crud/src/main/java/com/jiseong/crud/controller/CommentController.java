package com.jiseong.crud.controller;

import com.jiseong.crud.dto.comment.CommentCreateRequest;
import com.jiseong.crud.dto.comment.CommentUpdateRequest;
import com.jiseong.crud.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<?> enter(@RequestBody CommentCreateRequest request, @PathVariable Long boardId, @AuthenticationPrincipal UserDetails userdetails) {
        Long id = commentService.addComment(request, boardId, userdetails.getUsername());
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> update(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request, @AuthenticationPrincipal UserDetails userdetails) {
        Long id = commentService.updateComment(request, commentId, userdetails.getUsername());
        return ResponseEntity.status(201).body(id);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails userdetails) {
        Long id = commentService.deleteComment(commentId, userdetails.getUsername());
        return ResponseEntity.noContent().build(); // 204
    }
}
