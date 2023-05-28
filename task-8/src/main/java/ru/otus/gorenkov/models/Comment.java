package ru.otus.gorenkov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "bookcomments")
public class Comment {

    @Id
    private String id;

    private String nickName;

    private String text;

    private Book book;
}
