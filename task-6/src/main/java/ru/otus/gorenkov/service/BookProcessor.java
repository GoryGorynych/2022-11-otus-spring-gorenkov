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
public class BookProcessor implements CommandProcessor {

    private final BookService bookService;
    private final CommentService commentService;
    private final Converter converter;

    @Override
    public String showAll() {
        return converter.booksToText(bookService.getAll());
    }

    @Override
    public String showById(long id) {
        if (id == 0) {
            return "Укажите ид книги";
        }
        Book book = bookService.getById(id);
        return Objects.isNull(book) ? "" : converter.bookToText(book);
    }

    @Override
    public String add(String... attributes) {

        String bookName = attributes[0];
        String authorName = attributes[1];
        String genre = attributes[2];

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
                .author(Author.builder().fullName(authorName).build())
                .genre(Genre.builder().genre(genre).build())
                .build();

        bookService.save(book);

        return "Книга сохранена";
    }

    @Override
    public String edit(long id, String... attributes) {

        if (id == 0) {
            return "Укажите ид книги";
        }
        Book book = bookService.getById(id);

        String bookName = attributes[0];
        String authorName = attributes[1];
        String genre = attributes[2];

        boolean nothingUpdate = true;
        if (!bookName.isBlank()) {
            book.setName(bookName);
            nothingUpdate = false;
        }
        if (!authorName.isBlank()) {
            book.setAuthor(Author.builder().fullName(authorName).build());
            nothingUpdate = false;
        }
        if (!genre.isBlank()) {
            book.setGenre(Genre.builder().genre(genre).build());
            nothingUpdate = false;
        }

        if (nothingUpdate) {
            return "Нечего обновлять";
        }

        bookService.save(book);

        return "Книга изменена";
    }

    @Override
    public String delete(long id) {

        if (id == 0) {
            return "Укажите ид книги";
        }
        bookService.deleteById(id);

        return "Книга удалена";
    }

    @Override
    public String addComment(Object... attributes) {
        long bookId = (long) attributes[0];
        String nickName = (String) attributes[1];
        String text = (String) attributes[2];

        Comment comment = Comment.builder().bookId(bookId).nickName(nickName).text(text).build();
        commentService.save(comment);

        return "Комментарий добавлен";
    }

    @Override
    public String editComment(long id, String text) {
        Comment comment = commentService.findById(id);
        comment.setText(text);
        commentService.save(comment);

        return "Комментарий изменен";
    }

    @Override
    public String deleteAllCommentsByBook(long bookId) {
        commentService.removeByBook(bookId);

        return "Комментарии по книге удалены";
    }

    @Override
    public String showAllCommentsByBook(long bookId) {
        return converter.commentsToText(Optional.ofNullable(commentService.findByBook(bookId))
                .orElse(new ArrayList<>()));
    }

}
