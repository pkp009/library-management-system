package com.project.librarymanagementsystem.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Ledger")
public class Ledger {

    private Long id;
    private long userId;
    private long bookId;
    private LocalDate checkOutDate;
    private LocalDate expectedReturnDate;

    private LocalDate returnDate;
    private int dues;

    public Ledger() {

    }

    @Id
    @Column(name = "userId", length = 50, nullable = false, unique = true)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "bookId", length = 50, nullable = false, unique = true)
    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Column(name = "checkOutDate", length = 100, nullable = false)
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Column(name = "expectedReturnDate", length = 100, nullable = false)
    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDate returnDate) {
        this.expectedReturnDate = returnDate;
    }

    @Column(name = "returnDate", length = 100, nullable = true)
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Column(name = "dues", length = 50, nullable = true)
    public int getDues() {
        return dues;
    }

    public void setDues(int dues) {
        this.dues = dues;
    }


    public Ledger(long userId, long bookId, LocalDate checkOutDate, LocalDate expectedReturnDate, int dues) {
        this.userId = userId;
        this.bookId = bookId;
        this.checkOutDate = checkOutDate;
        this.expectedReturnDate = expectedReturnDate;
        this.dues = dues;
    }

}
