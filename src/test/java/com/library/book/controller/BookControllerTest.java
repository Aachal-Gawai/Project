package com.library.book.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.book.entities.Book;
import com.library.book.model.BookDetails;
import com.library.book.model.BookUpdateDetails;
import com.library.book.service.IBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IBookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        // Create a list of dummy BookDetails objects
        List<BookDetails> books = new ArrayList<>();
        BookDetails book=new BookDetails();
        book.setId(1L);
        book.setTitle("The Psychology of Money");
        book.setAuthor("Morgan Housel");
        book.setUserId("agawai@dataeconomy.io");
        books.add(book);

        // Mock the behavior of bookService.getAllBooks()
        when(bookService.getAllBooks()).thenReturn(books);

        // Perform a GET request to /getAllBook
        mockMvc.perform(get("/api/books/getAllBook"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("The Psychology of Money"))
                .andExpect(jsonPath("$[0].author").value("Morgan Housel"));
        // Add more assertions to compare the response content with dummyBookDetails
    }




    @Test
    public void testGetBookByTitle() throws Exception {
        String bookTitle = "The Psychology of Money";

        // Create a list of dummy BookDetails objects with the specified title
        List<BookDetails> books = new ArrayList<>();
        BookDetails book = new BookDetails();
        book.setId(1L);
        book.setTitle("The Psychology of Money");
        book.setAuthor("Morgan Housel");
        book.setUserId("agawai@dataeconomy.io");
        books.add(book);

        // Mock the behavior of bookService.getBookByTitle()
        when(bookService.getBookByTitle(bookTitle)).thenReturn(books);

        // Perform a GET request to /getBookByTitle/{bookTitle}
        mockMvc.perform(get("/api/books/getBookByTitle/{bookTitle}", bookTitle))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value(book.getTitle()));

    }

    @Test
    public void testCreateBook() throws Exception {
        // Create a dummy BookDetails object
        BookDetails bookDetails = new BookDetails();
        bookDetails.setTitle("The Psychology of Money");
        bookDetails.setAuthor("Morgan Housel");
        bookDetails.setCreateDate("2023-10-09");
        bookDetails.setUpdateDate("2023-10-09");

        // Set other attributes as needed

        // Convert the BookDetails object to JSON
        String bookDetailsJson = objectMapper.writeValueAsString(bookDetails);

        // Mock the behavior of bookService.createBook()
        when(bookService.createBook(any(BookDetails.class))).thenReturn(bookDetails);

        // Perform a POST request to /createBook with JSON payload
        mockMvc.perform(post("/api/books/createBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDetailsJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(bookDetails.getTitle()));

    }

    @Test
    public void testUpdateBook() throws Exception {
        Long bookId = 1L;

        // Create a dummy BookDetails object for the updated book
        BookDetails updatedBookResponse = new BookDetails();
        updatedBookResponse.setTitle("The Psychology of Money");
        updatedBookResponse.setAuthor("Morgan Housel");

        // Set other attributes as needed

        BookUpdateDetails updatedBookDetails=new BookUpdateDetails();
        updatedBookDetails.setTitle("The Psychology of Money");
        updatedBookDetails.setPublicationBy("");
        updatedBookDetails.setAuthor("");
        updatedBookDetails.setUpdateDate("2023-10-09");
        // Convert the updated BookDetails object to JSON
        String updatedBookDetailsJson = objectMapper.writeValueAsString(updatedBookDetails);

        // Mock the behavior of bookService.updateBook()
        when(bookService.updateBook(eq(bookId), any(BookUpdateDetails.class))).thenReturn(updatedBookResponse);

        // Perform a PUT request to /updateBook/{bookId} with JSON payload
        mockMvc.perform(put("/api/books/updateBook/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBookDetailsJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(updatedBookResponse.getTitle()));

    }

    @Test
    public void testDeleteBook() throws Exception {
        Long bookId = 1L;

        // Mock the behavior of bookService.deleteBook()
        when(bookService.deleteBook(eq(bookId))).thenReturn("Deleted Successfully");

        // Perform a DELETE request to /deleteBook/{bookId}
        mockMvc.perform(delete("/api/books/deleteBook/{bookId}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Successfully"));

    }
}








