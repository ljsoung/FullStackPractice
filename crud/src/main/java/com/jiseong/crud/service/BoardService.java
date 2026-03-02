package com.jiseong.crud.service;

import com.jiseong.crud.domain.AppUser;
import com.jiseong.crud.domain.Board;
import com.jiseong.crud.domain.Comment;
import com.jiseong.crud.dto.board.BoardCreateRequest;
import com.jiseong.crud.dto.board.BoardReadResponse;
import com.jiseong.crud.dto.board.BoardUpdateRequest;
import com.jiseong.crud.dto.comment.CommentReadResponse;
import com.jiseong.crud.repository.AppUserRepository;
import com.jiseong.crud.repository.BoardRepository;
import com.jiseong.crud.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final AppUserRepository userRepository;
    private final CommentRepository commentRepository;

    public BoardService(BoardRepository boardRepository, AppUserRepository appUserRepository, AppUserRepository userRepository, CommentRepository commentRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Long create(BoardCreateRequest request, String username) {

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setAuthor(user);

        return boardRepository.save(board).getId();
    }

    public BoardReadResponse read(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        List<CommentReadResponse> comments = commentRepository.findAllByBoardIdWithAuthor(boardId)
                                            .stream()
                                            .map(c -> new CommentReadResponse(
                                                    c.getId(),
                                                    c.getContent(),
                                                    c.getAuthor().getUsername(),
                                                    c.getCreatedAt()
                                            )).toList();

        return new BoardReadResponse(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getAuthor().getUsername(),
                board.getCreatedAt(),
                comments
        );
    }

    public Long update(BoardUpdateRequest request, Long boardId, String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        if (!board.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("수정 권한 없음");
        }

        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        return boardRepository.save(board).getId();
    }

    public Long delete(Long boardId, String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        if (!board.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("삭제 권한 없음");
        }

        boardRepository.delete(board);
        return board.getId();
    }
}
