package ru.otus.gorenkov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BookPagesContoller {

    @GetMapping("/")
    public String listBooks(Model model) {
        return "list";
    }

    @GetMapping("/edit/{id}")
    public String editBookPage(@PathVariable String id, Model model) {
        model.addAttribute("bookId", id);
        return "edit";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }



}
