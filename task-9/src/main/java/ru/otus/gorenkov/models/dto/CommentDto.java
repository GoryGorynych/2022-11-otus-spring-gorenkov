package ru.otus.gorenkov.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Comment;

@AllArgsConstructor
@Data
public class CommentDto {

    private String id;
    private String nickName;
    private String text;

    public Comment toDomainObject(Book book) {
        return Comment.builder()
                .book(book)
                .nickName(nickName)
                .text(text)
                .build();
    }
}
