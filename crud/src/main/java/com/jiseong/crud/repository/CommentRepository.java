package com.jiseong.crud.repository;

import com.jiseong.crud.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
        select c
        from Comment c
        join fetch c.author
        where c.board.id = :boardId
        order by c.id asc
    """)
    List<Comment> findAllByBoardIdWithAuthor(@Param("boardId") Long boardId);

    Optional<Comment> findById(Long commentId);
}
