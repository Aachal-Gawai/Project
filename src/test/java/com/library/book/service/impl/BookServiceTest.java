package com.library.book.service.impl;

import com.library.book.entities.Book;
import com.library.book.exception.SearchRecordsNotFoundException;
import com.library.book.model.BookDetails;
import com.library.book.repositries.IBookRepository;
import com.library.book.service.IBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private IBookRepository bookRepository;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testGetAllBooks() {
        // Create a list of dummy books
        List<Book> Books = new ArrayList<>();
        Book book=new Book();
        book.setId(1L);
        book.setUserId("aachalgawai@gmail.com");
        book.setAuthor("Morgan Housel");
        book.setTitle("The Psychology of Money");
        book.setPublicationBy("Jaico Publishing House");
        Books.add(book);

        // Mock the behavior of bookRepository.findAll()
        when(bookRepository.findAll()).thenReturn(Books);

        // Create a list of dummy BookDetails objects that correspond to the dummy books
        List<BookDetails> dummyBookDetails = Books.stream()
                .map(books  -> {
                    BookDetails bookDetails = new BookDetails();
                    bookDetails.setId(book.getId());
                    bookDetails.setTitle(book.getTitle());
                    bookDetails.setAuthor(book.getAuthor());
                    bookDetails.setPublicationBy(book.getPublicationBy());
                    // Set other attributes as needed
                    return bookDetails;
                })
                .collect(Collectors.toList());

        // Mock the behavior of modelMapper.map
        when(modelMapper.map(any(Book.class), eq(BookDetails.class)))
                .thenAnswer(invocation -> {
                    Book sourceBook = invocation.getArgument(0);
                    BookDetails bookDetails = new BookDetails();
                    bookDetails.setId(sourceBook.getId());
                    bookDetails.setTitle(sourceBook.getTitle());
                    bookDetails.setAuthor(sourceBook.getAuthor());
                    bookDetails.setPublicationBy(sourceBook.getPublicationBy());
                    // Set other attributes as needed
                    return bookDetails;
                });

        // Call the getAllBooks method
        List<BookDetails> bookDetailsList = bookService.getAllBooks();

        // Verify that bookRepository.findAll() was called
        verify(bookRepository, times(1)).findAll();

        // Verify the result
        assertNotNull(bookDetailsList);
        assertEquals(Books.size(), bookDetailsList.size());
        // Add more assertions to compare individual BookDetails objects if needed
    }

    @Test
    public void testGetAllBooks1() {
        // Create a list of books
        List<Book> Books = new ArrayList<>();
        Book book=new Book();
        book.setId(1L);
        book.setUserId("aachalgawai@gmail.com");
        book.setAuthor("Morgan Housel");
        book.setTitle("The Psychology of Money");
        book.setPublicationBy("Jaico Publishing House");
        Books.add(book);

        // Mock the behavior of the bookRepository.findAll() method
        when(bookRepository.findAll()).thenReturn(Books);

        // Call the getAllBooks method
        List<BookDetails> bookDetailsList = bookService.getAllBooks();

        // Verify that the bookRepository.findAll() method was called once
        verify(bookRepository, times(1)).findAll();

        // Verify the result
        assertNotNull(bookDetailsList);
        assertEquals(1, bookDetailsList.size());

    }
    @Test
    public void testGetBookByTitle() {
        // Define a book title to search for
        String bookTitle = "The Psychology of Money";

        // Create a list of books
        List<Book> Books = new ArrayList<>();
        Book book=new Book();
        book.setId(1L);
        book.setUserId("aachalgawai@gmail.com");
        book.setAuthor("Morgan Housel");
        book.setTitle("The Psychology of Money");
        book.setPublicationBy("Jaico Publishing House");
        Books.add(book);

        // Mock the behavior of the bookRepository.findByTitle() method
        when(bookRepository.findByTitle(bookTitle)).thenReturn(Books);

        // Call the getBookByTitle method
        List<BookDetails> bookDetailsList = bookService.getBookByTitle(bookTitle);

        // Verify that the bookRepository.findByTitle() method was called once with the specified title
        verify(bookRepository, times(1)).findByTitle(bookTitle);

        // Verify the result
        assertNotNull(bookDetailsList);
        assertEquals(1, bookDetailsList.size());

    }

    @Test
    public void testGetBookByTitleNoMatch() {
        // Define a book title to search for
        String bookTitle = "Non-Existent Book";

        // Mock the behavior of the bookRepository.findByTitle() method to return an empty list
        when(bookRepository.findByTitle(bookTitle)).thenReturn(new ArrayList<>());

        // Call the getBookByTitle method
        List<BookDetails> bookDetailsList = bookService.getBookByTitle(bookTitle);

        // Verify that the bookRepository.findByTitle() method was called once with the specified title
        verify(bookRepository, times(1)).findByTitle(bookTitle);

        // Verify that the result is null, as there are no matching books
        assertNull(bookDetailsList);
    }

    @Test
    public void testCreateBook() {
        // Create a BookDetails object
        BookDetails bookDetails = new BookDetails();
        bookDetails.setId(1L);
        bookDetails.setUserId("aachalgawai@gmail.com");
        bookDetails.setAuthor("Morgan Housel");
        bookDetails.setTitle("The Psychology of Money");
        bookDetails.setPublicationBy("Jaico Publishing House");

        // Create a Book object
        Book book = new Book();
        book.setId(1L);
        book.setUserId("aachalgawai@gmail.com");
        book.setAuthor("Morgan Housel");
        book.setTitle("The Psychology of Money");
        book.setPublicationBy("Jaico Publishing House");

        // Mock the behavior of the bookRepository.save() method
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Call the createBook method
        BookDetails createdBookDetails = bookService.createBook(bookDetails);

        // Verify that the bookRepository.save() method was called once with the correct argument
        verify(bookRepository, times(1)).save(any(Book.class));

        // Verify the result
        assertNotNull(createdBookDetails);
//        assertEquals(book.getId(), createdBookDetails.getId());
//        assertEquals(book.getTitle(), createdBookDetails.getTitle());
//        assertEquals(book.getAuthor(), createdBookDetails.getAuthor());
    }

    @Test
    public void testUpdateBook() {
        // Define a book ID and an updated BookDetails object
        Long bookId = 1L;
        BookDetails updatedBookDetails = new BookDetails();
        updatedBookDetails.setTitle("The Psychology of Money");
        updatedBookDetails.setPublicationBy("");
        updatedBookDetails.setAuthor("");
        // Add more updates as needed

        // Create existing Book object
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setUserId("aachalgawai@gmail.com");
        existingBook.setAuthor("Morgan Housel");
        existingBook.setTitle("Psychology of Money");
        existingBook.setPublicationBy("Jaico Publishing House");

        Book UpdatedBook = new Book();
        UpdatedBook.setId(1L);
        UpdatedBook.setUserId("aachalgawai@gmail.com");
        UpdatedBook.setAuthor("Morgan Housel");
        UpdatedBook.setTitle("The Psychology of Money");
        UpdatedBook.setPublicationBy("Jaico Publishing House");

        // Mock the behavior of the bookRepository.findById() and bookRepository.save() methods
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        when(bookRepository.save(any(Book.class))).thenReturn(UpdatedBook);

        // Call the updateBook method
        BookDetails updatedBookDetailsResponse = bookService.updateBook(bookId, updatedBookDetails);

        // Verify that the bookRepository.findById() and bookRepository.save() methods were called
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(Book.class));

        // Verify the result
        assertNotNull(updatedBookDetailsResponse);
//        assertEquals(bookId, updatedBookDetailsResponse.getId());
//        assertEquals(updatedBookDetails.getTitle(), updatedBookDetailsResponse.getTitle());
    }


    @Test
    public void testDeleteBook() {
        // Define a book ID
        Long bookId = 1L;

        // Mock the behavior of the bookRepository.findById() method
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));

        // Call the deleteBook method
        String deleteResult = bookService.deleteBook(bookId);

        // Verify that the bookRepository.findById() and bookRepository.deleteById() methods were called
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);

        // Verify the result
        assertEquals("Deleted Successfully", deleteResult);
    }
}

