package ru.otus.gorenkov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.gorenkov.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteByBookId(long bookId);
}
