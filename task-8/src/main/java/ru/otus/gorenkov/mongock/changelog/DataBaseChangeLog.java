package ru.otus.gorenkov.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.gorenkov.mongock.DataGenerator;
import ru.otus.gorenkov.repositories.BookRepository;
import ru.otus.gorenkov.repositories.CommentRepository;

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
}
