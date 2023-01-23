package com.project.librarymanagementsystem.controller;

import com.project.librarymanagementsystem.exception.LimitExceededException;
import com.project.librarymanagementsystem.exception.NotFoundException;
import com.project.librarymanagementsystem.exception.PendingDuesException;
import com.project.librarymanagementsystem.models.Book;
import com.project.librarymanagementsystem.models.Ledger;
import com.project.librarymanagementsystem.models.Members;
import com.project.librarymanagementsystem.models.Request;
import com.project.librarymanagementsystem.services.LibraryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/library")
public class LibraryController {
    @Autowired
    private LibraryServices libraryService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> handleNotFoundException(NotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(PendingDuesException.class)
    public Map<String, String> handlePendingDuesException(PendingDuesException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(LimitExceededException.class)
    public Map<String, String> handleLimitExceededException(LimitExceededException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleOtherExceptions(PendingDuesException ex) {
        return Map.of("error", ex.getMessage());
    }

    @PostMapping("/add/book")
    public ResponseEntity<Map<String, String>> addBook(@RequestBody Book book) {
        libraryService.addBook(book);
        return ResponseEntity.ok((Map.of("message", "Book added successfully")));
    }

    @PostMapping("/add/member")
    public ResponseEntity<Map<String, String>> addMember(@RequestBody Members members) {
        libraryService.addMember(members);
        return ResponseEntity.ok((Map.of("message", "Member added successfully")));
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(libraryService.getAllBooks());
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> findBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok(libraryService.findBookById(bookId));
    }

    @PostMapping("/books/checkout/")
    public ResponseEntity<Ledger> checkOutBook(@RequestBody Request request) {
        return ResponseEntity.ok(libraryService.checkOutBook(request.getBookId(), request.getUserId()));
    }

    @PostMapping("/books/return/")
    public ResponseEntity<Map<String, String>> returnBook(@RequestBody Request request) {
        return ResponseEntity.ok(libraryService.returnBook(request.getBookId(), request.getUserId()));

    }

    @PostMapping("/books/fine/")
    public ResponseEntity<Map<String, Integer>> calculateFine(@RequestBody Request request) {
        return ResponseEntity.ok(Map.of("duesToBePaid",
                libraryService.calculateFine(request.getBookId(), request.getUserId())));
    }

    @PostMapping("/books/fine/{userId}")
    public ResponseEntity<String> payDues(@PathVariable Long userId) {
        libraryService.payDues(userId);
        return new ResponseEntity<>("Dues paid successfully", HttpStatus.OK);

    }
}