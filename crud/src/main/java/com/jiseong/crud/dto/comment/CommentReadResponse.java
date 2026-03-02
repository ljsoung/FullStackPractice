package com.jiseong.crud.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentReadResponse {

    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}
