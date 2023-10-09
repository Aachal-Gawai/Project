package com.library.book.service;


import com.library.book.entities.Book;
import com.library.book.model.BookDetails;
import com.library.book.model.BookUpdateDetails;


import java.util.List;

public interface IBookService {

    public List<BookDetails> getAllBooks();

    public List<BookDetails> getBookByTitle(String title);


    public BookDetails createBook(BookDetails bookDetails);

    public BookDetails updateBook(Long bookId, BookUpdateDetails updatedBook);

    public String deleteBook(Long bookId);


}
