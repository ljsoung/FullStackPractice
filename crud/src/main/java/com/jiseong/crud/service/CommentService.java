package com.jiseong.crud.service;

import com.jiseong.crud.domain.AppUser;
import com.jiseong.crud.domain.Board;
import com.jiseong.crud.domain.Comment;
import com.jiseong.crud.dto.comment.CommentCreateRequest;
import com.jiseong.crud.dto.comment.CommentUpdateRequest;
import com.jiseong.crud.repository.AppUserRepository;
import com.jiseong.crud.repository.BoardRepository;
import com.jiseong.crud.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final AppUserRepository appUserRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public CommentService(AppUserRepository appUserRepository, BoardRepository boardRepository, CommentRepository commentRepository) {
        this.appUserRepository = appUserRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    public Long addComment(CommentCreateRequest request, Long boardId, String username) {
        AppUser user = appUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));

        Comment comment = new Comment();
        comment.setContent(request.getComment());
        comment.setBoard(board);
        comment.setAuthor(user);

        commentRepository.save(comment);
        return comment.getId();
    }

    public Long updateComment(CommentUpdateRequest request, Long commentId, String username) {
        AppUser user = appUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        if (!comment.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("수정 권한 없음");
        }

        comment.setContent(request.getComment());

        commentRepository.save(comment);
        return comment.getId();
    }

    public Long deleteComment(Long commentId, String username) {
        AppUser user = appUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("댓글이 없습니다."));
        if (!comment.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("수정 권한 없음");
        }

        commentRepository.delete(comment);
        return comment.getId();
    }
}
