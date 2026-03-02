package com.jiseong.crud.dto.board;

import com.jiseong.crud.dto.comment.CommentReadResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class BoardReadResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private List<CommentReadResponse> comments;
}