package ru.otus.gorenkov.mongock;

import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Comment;
import ru.otus.gorenkov.models.Genre;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataGenerator {

    private final static List<Author> authors = Stream.of(
            "Джон Роналд Толкин",
                    "Маргарет Митчелл",
                    "Брэм Стокер",
                    "Артур Конан Дойль",
                    "Эрнест Хемингуэй")
            .map(Author::new)
            .collect(Collectors.toList());

    private final static List<Genre> genres = Stream.of(
            "фэнтези",
                    "роман",
                    "ужасы",
                    "детектив",
                    "повесть")
            .map(Genre::new)
            .collect(Collectors.toList());

    private final static List<Book> books = List.of(
            Book.builder().name("Властелин колец").author(authors.get(0)).genre(genres.get(0)).build(),
            Book.builder().name("Унесённые ветром").author(authors.get(1)).genre(genres.get(1)).build(),
            Book.builder().name("Дракула").author(authors.get(2)).genre(genres.get(2)).build(),
            Book.builder().name("Собака Баскервилей").author(authors.get(3)).genre(genres.get(3)).build(),
            Book.builder().name("Старик и море").author(authors.get(4)).genre(genres.get(4)).build()
    );

    private final static List<Comment> comments = List.of(
            Comment.builder().book(books.get(0)).text("comment_01").nickName("user_01").build(),
            Comment.builder().book(books.get(0)).text("comment_02").nickName("user_02").build(),
            Comment.builder().book(books.get(1)).text("comment_03").nickName("user_03").build(),
            Comment.builder().book(books.get(1)).text("comment_04").nickName("user_04").build(),
            Comment.builder().book(books.get(2)).text("comment_05").nickName("user_05").build(),
            Comment.builder().book(books.get(2)).text("comment_06").nickName("user_06").build(),
            Comment.builder().book(books.get(3)).text("comment_07").nickName("user_07").build(),
            Comment.builder().book(books.get(3)).text("comment_08").nickName("user_08").build(),
            Comment.builder().book(books.get(4)).text("comment_09").nickName("user_09").build(),
            Comment.builder().book(books.get(4)).text("comment_10").nickName("user_10").build()
            );

    public static List<Book> getAllBooks() {
        return books;
    }

    public static List<Comment> getAllComments() {
        return comments;
    }
}
