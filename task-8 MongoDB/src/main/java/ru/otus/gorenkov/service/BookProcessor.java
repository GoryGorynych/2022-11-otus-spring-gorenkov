package ru.otus.gorenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Comment;
import ru.otus.gorenkov.models.Genre;
import ru.otus.gorenkov.utils.Converter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookProcessor {

    private final BookService bookService;
    private final CommentService commentService;
    private final Converter converter;

    public String showAll() {
        return converter.booksToText(bookService.getAll());
    }

    public String showById(String id) {
        if ("0".equals(id)) {
            return "Укажите ид книги";
        }
        Book book = bookService.getById(id);
        return Objects.isNull(book) ? "" : converter.bookToText(book);
    }

    public String add(String bookName, String authorName, String genre) {

        if (bookName.isBlank()) {
            return "Укажите название книги";
        }
        if (authorName.isBlank()) {
            return "Укажите полное имя автора";
        }
        if (genre.isBlank()) {
            return "Укажите жанр книги";
        }

        Book book = Book.builder()
                .name(bookName)
                .author(new Author(authorName))
                .genre(new Genre(genre))
                .build();

        bookService.save(book);

        return "Книга сохранена";
    }

    public String edit(String id, String bookName, String authorName, String genre) {

        if ("0".equals(id)) {
            return "Укажите ид книги";
        }
        Book book = bookService.getById(id);

        boolean nothingUpdate = true;
        if (!bookName.isBlank()) {
            book.setName(bookName);
            nothingUpdate = false;
        }
        if (!authorName.isBlank()) {
            book.setAuthor(new Author(authorName));
            nothingUpdate = false;
        }
        if (!genre.isBlank()) {
            book.setGenre(new Genre(genre));
            nothingUpdate = false;
        }

        if (nothingUpdate) {
            return "Нечего обновлять";
        }

        bookService.save(book);

        return "Книга изменена";
    }

    public String delete(String id) {

        if ("0".equals(id)) {
            return "Укажите ид книги";
        }
        bookService.deleteByIdWithComments(id);

        return "Книга удалена";
    }

    public String addComment(String bookId, String nickName, String text) {

        Book book = bookService.getById(bookId);
        Comment comment = Comment.builder().book(book).nickName(nickName).text(text).build();
        commentService.save(comment);

        return "Комментарий добавлен";
    }

    public String editComment(String id, String text) {
        Comment comment = commentService.findById(id);
        comment.setText(text);
        commentService.save(comment);

        return "Комментарий изменен";
    }

    public String deleteAllCommentsByBook(String bookId) {
        commentService.removeByBook(bookId);

        return "Комментарии по книге удалены";
    }

    public String showAllCommentsByBook(String bookId) {
        return converter.commentsToText(Optional.ofNullable(commentService.findByBook(bookId))
                .orElse(new ArrayList<>()));
    }

}
