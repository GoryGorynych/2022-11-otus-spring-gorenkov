package ru.otus.gorenkov.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.gorenkov.models.Genre;

@Data
@NoArgsConstructor
public class GenreDto {

    private String title;

    public GenreDto(String title) {
        this.title = title;
    }

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getTitle());
    }

    public Genre toDomainObject() {
        return new Genre(title);
    }
}
