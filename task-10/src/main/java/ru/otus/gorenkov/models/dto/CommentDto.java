package ru.otus.gorenkov.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Comment;

@AllArgsConstructor
@Data
@NoArgsConstructor
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

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getBook().getId(), comment.getNickName(), comment.getText());
    }
}
