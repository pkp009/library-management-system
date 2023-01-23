package com.project.librarymanagementsystem.repository;

import com.project.librarymanagementsystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findById(long bookId);

    List<Book> findByTitleContainingIgnoreCase(String title);
}