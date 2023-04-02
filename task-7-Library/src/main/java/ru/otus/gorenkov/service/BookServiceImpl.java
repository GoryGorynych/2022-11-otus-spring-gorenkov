package ru.otus.gorenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Genre;
import ru.otus.gorenkov.repositories.AuthorRepository;
import ru.otus.gorenkov.repositories.BookRepository;
import ru.otus.gorenkov.repositories.GenreRepository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public Book save(Book book) {

        if (bookRepository.existsByName(book.getName())) {
            throw new RuntimeException("Book already exists");
        }

        Author foundAuthor = authorRepository.findByFullName(book.getAuthor().getFullName());
        if (foundAuthor == null) {
            foundAuthor = authorRepository.save(book.getAuthor());
        }
        book.setAuthor(foundAuthor);

        Genre foundGenre = genreRepository.findByGenre(book.getGenre().getGenre());
        if (foundGenre == null) {
            foundGenre = genreRepository.save(book.getGenre());
        }
        book.setGenre(foundGenre);

        return bookRepository.save(book);
    }

    @Override
    public Book getById(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElseThrow(() -> new RuntimeException("Книга не найдена"));
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Author findAuthorByFullName(String fullName) {
        try {
            return authorRepository.findByFullName(fullName);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Genre findGenreByGenre(String genre) {
        try {
            return genreRepository.findByGenre(genre);
        } catch (NoResultException e) {
            return null;
        }
    }


}
