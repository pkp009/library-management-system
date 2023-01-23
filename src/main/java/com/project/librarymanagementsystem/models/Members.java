package com.project.librarymanagementsystem.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Members")
public class Members {

    public Members(long userId, int noOfBooksIssued, String userName, int dues) {
        this.userId = userId;
        this.noOfBooksIssued = noOfBooksIssued;
        this.userName = userName;
        this.dues = dues;
    }

    private Long id;
    private long userId;
    private int noOfBooksIssued;
    private String userName;
    private int dues;


    @Id
    @Column(name = "userId", length = 50, nullable = false, unique = true)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "noOfBooksIssued", length = 50, nullable = false)
    public int getNoOfBooksIssued() {
        return noOfBooksIssued;
    }

    public void setNoOfBooksIssued(int noOfBooksIssued) {
        this.noOfBooksIssued = noOfBooksIssued;
    }

    @Column(name = "userName", length = 100, nullable = false)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "dues", length = 50, nullable = false)
    public int getDues() {
        return dues;
    }

    public void setDues(int dues) {
        this.dues = dues;
    }

}