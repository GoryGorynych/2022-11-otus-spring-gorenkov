package ru.otus.gorenkov.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.gorenkov.models.User;
import ru.otus.gorenkov.mongock.DataGenerator;
import ru.otus.gorenkov.repositories.BookRepository;
import ru.otus.gorenkov.repositories.CommentRepository;
import ru.otus.gorenkov.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ChangeLog
public class DataBaseChangeLog {

    @ChangeSet(order = "000", id = "000", author = "vgorenkov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "001", id = "001", author = "vgorenkov")
    public void addBooks(BookRepository bookRepository) {
        bookRepository.saveAll(DataGenerator.getAllBooks());
    }

    @ChangeSet(order = "002", id = "002", author = "vgorenkov")
    public void addComents(CommentRepository commentRepository) {
        commentRepository.saveAll(DataGenerator.getAllComments());
    }

    @ChangeSet(order = "003", id = "003", author = "vgorenkov")
    public void addUsers(UserRepository userRepository, PasswordEncoder encoder) {
        List<User> userList = new ArrayList<>();
        userList.add(new User(null, "user", encoder.encode("user"), Set.of("USER")));
        userList.add(new User(null, "admin", encoder.encode("admin"), Set.of("ADMIN")));
        userRepository.saveAll(userList);
    }
}
