package ru.otus.gorenkov.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Genre;

@AllArgsConstructor
@Data
public class BookDto {

    private String id;
    private String name;
    Author author;
    Genre genre;

    public Book toDomainObject() {
        return Book.builder()
                .id(id)
                .name(name)
                .author(new Author(author.getFullName()))
                .genre(new Genre(genre.getTitle()))
                .build();
    }
}
