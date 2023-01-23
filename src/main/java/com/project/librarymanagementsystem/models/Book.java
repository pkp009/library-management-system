package com.project.librarymanagementsystem.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@Entity
@Table(name = "Book")
@NoArgsConstructor
public class Book {

    @Id
    private long bookId;
    private int noOfCopies;
    private String title;
    private String author;

    public Book(long bookId, int noOfCopies, String title, String author) {
        this.bookId = bookId;
        this.noOfCopies = noOfCopies;
        this.title = title;
        this.author = author;
    }

    @Id
    @Column(name = "bookId", length = 50, nullable = false, unique = true)
    public long bookId() {
        return bookId;
    }

    @Column(name = "noOfCopies", length = 50, nullable = false)
    public int getNoOfCopies() {
        return noOfCopies;
    }

    @Column(name = "title", length = 100, nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "author", length = 100, nullable = false)
    public String getAuthor() {
        return author;
    }

}

