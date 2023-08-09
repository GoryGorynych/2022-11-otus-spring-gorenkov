package ru.otus.gorenkov.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Document("users")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private Set<String> roles;

}


