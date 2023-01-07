package ru.otus.gorenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.gorenkov.domain.Author;
import ru.otus.gorenkov.domain.Book;
import ru.otus.gorenkov.domain.Genre;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookProcessor implements CommandProcessor {

    private final BookService bookService;

    @Override
    public String showAll() {
        return bookService.getAll().toString();
    }

    @Override
    public String showById(long id) {
        if (id == 0) {
            return "Укажите ид книги";
        }
        Book book = bookService.getById(id);
        return Objects.isNull(book) ? "" : book.toString();
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

        bookService.update(book, id);

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
}
