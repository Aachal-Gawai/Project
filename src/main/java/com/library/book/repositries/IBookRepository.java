package com.library.book.repositries;

import com.library.book.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBookRepository extends JpaRepository<Book,Long> {
    List<Book> findByTitle(String title);

    @Modifying
    @Query("DELETE FROM Book b WHERE b.title = :title")
    void deleteByTitle(String title);

}
