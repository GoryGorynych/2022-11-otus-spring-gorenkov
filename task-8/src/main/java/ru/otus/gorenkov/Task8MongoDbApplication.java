package ru.otus.gorenkov;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.repositories.BookRepository;
import ru.otus.gorenkov.repositories.CommentRepository;

@SpringBootApplication
@EnableMongoRepositories
@EnableMongock
public class Task8MongoDbApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Task8MongoDbApplication.class, args);

//        BookRepository bookRepository = context.getBean(BookRepository.class);
//        CommentRepository commentRepository = context.getBean(CommentRepository.class);

//        Book book = new Book("Main book");
//        bookRepository.save(book);

//        System.out.println("===========================================");
//        bookRepository.findAll().forEach(book2 -> System.out.println(book2.getName()));
//        commentRepository.findAll().forEach(com -> System.out.println(com.getText()));
//        System.out.println("===========================================");

    }

}
