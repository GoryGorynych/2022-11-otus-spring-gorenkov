package ru.otus.gorenkov.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentDto {

    private String nickName;
    private String text;
}
