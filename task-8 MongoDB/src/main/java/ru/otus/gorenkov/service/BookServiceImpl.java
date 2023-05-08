package ru.otus.gorenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.repositories.BookRepository;
import ru.otus.gorenkov.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Book save(Book book) {
        if (bookRepository.existsByName(book.getName())) {
            throw new RuntimeException("Book already exists");
        }

        return bookRepository.save(book);
    }

    @Override
    public Book getById(String id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElseThrow(() -> new RuntimeException("Книга не найдена"));
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }


    @Override
    public void deleteByIdWithComments(String id) {
        commentRepository.deleteByBookId(id);
        bookRepository.deleteById(id);
    }


}
