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
public class CommentControler {

    private final CommentService commentService;
    private final BookService bookService;

    @GetMapping("/comments")
    public String showComments(@RequestParam("bookid") String bookId, Model model) {
        List<CommentDto> comments = commentService.findByBook(bookId);
        model.addAttribute("comments", comments);

        Book book = bookService.getById(bookId);
        model.addAttribute("book", book);
        return "bookComment";
    }

    @PostMapping("/comments/delete")
    public String deleteComment(@RequestParam("id") String id, @RequestParam("bookid") String bookId, Model model) {
        commentService.removeOne(id);
        return "redirect:/comments?bookid="+ bookId;
    }

    @PostMapping("/comments/add")
    public String addComment(@ModelAttribute CommentDto commentDto, @RequestParam("bookId") String bookId, Model model) {
        Book book = bookService.getById(bookId);
        commentService.save(commentDto.toDomainObject(book));
        return "redirect:/comments?bookid=" + bookId;
    }
}
