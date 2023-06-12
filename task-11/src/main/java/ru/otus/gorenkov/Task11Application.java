package ru.otus.gorenkov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class Task11Application {

    public static void main(String[] args) {
        SpringApplication.run(Task11Application.class, args);
    }

}
