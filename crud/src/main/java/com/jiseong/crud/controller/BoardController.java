package com.jiseong.crud.controller;

import com.jiseong.crud.dto.board.BoardCreateRequest;
import com.jiseong.crud.dto.board.BoardUpdateRequest;
import com.jiseong.crud.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(Map.of("boards", boardService.findAll()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody BoardCreateRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Long id = boardService.create(request, userDetails.getUsername());
        return ResponseEntity.status(201).body(id);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> read(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.read(boardId));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<?> update(@Valid @RequestBody BoardUpdateRequest request, @PathVariable Long boardId, @AuthenticationPrincipal UserDetails userDetails) {
        Long id = boardService.update(request, boardId, userDetails.getUsername());
        return ResponseEntity.status(201).body(id);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> delete(@PathVariable Long boardId, @AuthenticationPrincipal UserDetails userDetails) {
        Long id = boardService.delete(boardId, userDetails.getUsername());
        return ResponseEntity.noContent().build(); // 204
    }
}
