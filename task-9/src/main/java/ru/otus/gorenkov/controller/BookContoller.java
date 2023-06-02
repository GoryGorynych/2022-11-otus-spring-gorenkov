package ru.otus.gorenkov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.dto.BookDto;
import ru.otus.gorenkov.service.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookContoller {

    private final BookService bookService;

    @GetMapping("/")
    public String listBooks(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editBookPage(@RequestParam("id") String id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/edit")
    public String saveBook(@ModelAttribute BookDto bookDto, Model model) {
        bookService.update(bookDto);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute BookDto bookDto, Model model) {
        bookService.save(bookDto.toDomainObject());
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam String id, Model model) {
        bookService.deleteByIdWithComments(id);
        return "redirect:/";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }



}
