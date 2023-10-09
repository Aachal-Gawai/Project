package com.library.book.controller;

import com.library.book.entities.Book;
import com.library.book.model.BookDetails;
import com.library.book.model.BookUpdateDetails;
import com.library.book.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookController {
    private final IBookService bookService;

    @GetMapping("/getAllBook")
    public List<BookDetails> getAllBooks() {
        return bookService.getAllBooks();
    }


    @GetMapping("/getBookByTitle/{bookTitle}")
    public List<BookDetails> getBookByTitle(@PathVariable String bookTitle) {
        return bookService.getBookByTitle(bookTitle);
    }

    @PostMapping("/createBook")
    public BookDetails createBook(@RequestBody @Valid BookDetails bookDetails) {
        return bookService.createBook(bookDetails);
    }

    @PutMapping("/updateBook/{bookId}")
    public BookDetails updateBook(@PathVariable Long bookId, @RequestBody @Valid BookUpdateDetails updatedBook) {
        return bookService.updateBook(bookId, updatedBook);
    }

    @DeleteMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        return bookService.deleteBook(bookId);
    }

}
