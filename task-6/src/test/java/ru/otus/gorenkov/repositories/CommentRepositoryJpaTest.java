package ru.otus.gorenkov.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.gorenkov.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий JPA для работы с комментариями ")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    @Autowired
    CommentRepositoryJpa repositoryJpa;

    @Autowired
    TestEntityManager em;

    private static final long FIRST_BOOK_ID = 1L;
    private static final long FIRST_COMMENT_ID = 1L;

    @DisplayName("должен возвращать все комментарии по книге")
    @Test
    public void shouldReturnAllCommentByBook() {
        List<Comment> actualComments = repositoryJpa.findByBookId(FIRST_BOOK_ID);

        assertThat(actualComments).isNotNull().hasSize(1);

        Comment expectedFirstComment = Comment.builder().id(1L).nickName("user_01").text("comment_01").bookId(FIRST_BOOK_ID).build();
        Comment actualFirstComment = actualComments.get(0);
        assertThat(actualFirstComment).isNotNull().usingRecursiveComparison().isEqualTo(expectedFirstComment);
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    public void shouldSaveNewComment() {
        Comment newComment = Comment.builder().nickName("user_02").text("comment_02").bookId(FIRST_BOOK_ID).build();
        repositoryJpa.save(newComment);

        List<Comment> actualBookComments = repositoryJpa.findByBookId(FIRST_BOOK_ID);
        assertThat(actualBookComments).isNotNull().hasSize(2);
    }

    @DisplayName("должен удалять комментарий по ид")
    @Test
    public void shouldRemoveCommentById() {
        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(comment).isNotNull();
        em.detach(comment);

        repositoryJpa.deleteById(1L);
        Comment deletedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(deletedComment).isNull();

    }

    @DisplayName("должен удалять все комментарии по книге")
    @Test
    public void shouldRemoveAllBookComments() {
        List<Comment> commentsFirstBook = repositoryJpa.findByBookId(FIRST_BOOK_ID);
        assertThat(commentsFirstBook).hasSize(1);

        repositoryJpa.deleteByBookId(FIRST_BOOK_ID);
        List<Comment> deletedComments = repositoryJpa.findByBookId(FIRST_BOOK_ID);
        assertThat(deletedComments).hasSize(0);
    }
}