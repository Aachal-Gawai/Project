package com.library.book.service.impl;

import com.library.book.entities.Book;
import com.library.book.exception.SearchRecordsNotFoundException;
import com.library.book.model.BookDetails;
import com.library.book.repositries.IBookRepository;
import com.library.book.service.IBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final ModelMapper modelMapper;
    private final IBookRepository bookRepository;


    @Override
    public List<BookDetails> getAllBooks() {
            log.info("Get all the book");
            List<Book> book =bookRepository.findAll();
            if(!book.isEmpty()){
                List<BookDetails> bookDetails = book.stream().map(books -> modelMapper.map(books, BookDetails.class))
                        .collect(Collectors.toList());
                return bookDetails;
            }
            log.info("No Book added");
            return null;
    }

    @Override
    public List<BookDetails> getBookByTitle(String bookTitle) {
        log.info("Get the book by title {}",bookTitle);
        if(!bookTitle.isEmpty()){
            List<Book> book =bookRepository.findByTitle(bookTitle);
            if(!book.isEmpty()){
                List<BookDetails> bookDetails = book.stream().map(books -> modelMapper.map(books, BookDetails.class))
                        .collect(Collectors.toList());
                return bookDetails;
            }
        }
        log.info("No Book present with title {}",bookTitle);
        return null;
    }

    @Override
    public BookDetails createBook(BookDetails bookDetails) {
        log.info("Create a new book record");
        Book book =new Book();
        modelMapper.map(bookDetails,book);
        Book bookResponse=bookRepository.save(book);
        BookDetails bookDetailsResponse=new BookDetails();
        modelMapper.map(bookResponse,bookDetailsResponse);
        return bookDetailsResponse;
    }

    @Override
    public BookDetails updateBook(Long bookId, BookDetails updatedBook) {
        log.info("Update the book record for book id {}",bookId);
        Book book=new Book();
        modelMapper.map(updatedBook,book);
        Optional<Book> existingBook = bookRepository.findById(bookId);
            if (existingBook.isPresent()) {
                log.info("Update fields of existingBook with values from updatedBook");
                Book bookEntity=existingBook.get();
                if(!updatedBook.getTitle().isBlank()) {
                    bookEntity.setTitle(updatedBook.getTitle());
                }
                if(!updatedBook.getAuthor().isBlank()) {
                    bookEntity.setAuthor(updatedBook.getAuthor());                }
                if(!updatedBook.getPublicationBy().isBlank()) {
                    bookEntity.setPublicationBy(updatedBook.getPublicationBy());
                }
                Book bookresponse=bookRepository.save(bookEntity);

                BookDetails bookDetailsResponse=new BookDetails();
                modelMapper.map(bookresponse,bookDetailsResponse);
                return bookDetailsResponse;
            }

            log.info("Book record is not persent for the id {}",bookId);
            return null;

    }

    @Override
    public String deleteBook(Long bookId) {
            log.info("Find the book record for id {}", bookId);
            Optional<Book> existingBook = bookRepository.findById(bookId);
            if (existingBook.isPresent()) {
                log.info("Book record present");
                Book bookEntity = existingBook.get();
                bookRepository.deleteById(bookId);
                return "Deleted Successfully";
            }
            return "Book record not present";
    }

}

