package ru.otus.gorenkov.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookWithCommentsDto {

    private BookDto book;
    private List<CommentDto> comments;
}
