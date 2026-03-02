package com.jiseong.crud.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotBlank(message = "댓글을 입력해주세요.")
    String comment;
}
