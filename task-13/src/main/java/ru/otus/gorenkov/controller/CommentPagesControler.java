package ru.otus.gorenkov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentPagesControler {

    @GetMapping("/comments")
    public String showComments(@RequestParam String bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "bookComment";
    }
}
