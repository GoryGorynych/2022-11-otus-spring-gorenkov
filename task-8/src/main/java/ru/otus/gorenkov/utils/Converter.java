package ru.otus.gorenkov.utils;

import org.springframework.stereotype.Component;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.dto.CommentDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {

    public String booksToText(List<Book> books) {
        return books.stream()
                .map(book -> concatBookField(book) + "\n")
                .collect(Collectors.joining());
    }

    public String bookToText(Book book) {
        return concatBookField(book);
    }

    public String commentsToText(List<CommentDto> comments) {
        return comments.stream()
                .map(comment -> comment.getId() + ": " + comment.getNickName() + ": " + comment.getText() + "\n")
                .collect(Collectors.joining());
    }

    private String concatBookField(Book book) {
        return book.getId() + ": " + book.getName() + ". " + book.getAuthor() + ". " + book.getGenre();
    }
}
