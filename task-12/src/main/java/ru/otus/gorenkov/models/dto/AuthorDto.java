package ru.otus.gorenkov.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.gorenkov.models.Author;

@Data
@NoArgsConstructor
public class AuthorDto {

    private String fullName;

    public AuthorDto(String fullName) {
        this.fullName = fullName;
    }

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getFullName());
    }

    public Author toDomainObject() {
        return new Author(fullName);
    }

}
