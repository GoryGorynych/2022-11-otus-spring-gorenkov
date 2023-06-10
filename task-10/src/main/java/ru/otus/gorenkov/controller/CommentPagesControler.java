package ru.otus.gorenkov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.dto.CommentDto;
import ru.otus.gorenkov.service.BookService;
import ru.otus.gorenkov.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentPagesControler {

    @GetMapping("/comments")
    public String showComments(@RequestParam String bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "bookComment";
    }
}
