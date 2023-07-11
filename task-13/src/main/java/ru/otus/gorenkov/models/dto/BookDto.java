package ru.otus.gorenkov.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Genre;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private String id;
    private String name;
    AuthorDto author;
    GenreDto genre;

    public Book toDomainObject() {
        return Book.builder()
                .id(id)
                .name(name)
                .author(new Author(author.getFullName()))
                .genre(new Genre(genre.getTitle()))
                .build();
    }

    public static BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(AuthorDto.toDto(book.getAuthor()))
                .genre(GenreDto.toDto(book.getGenre()))
                .build();
    }
}
