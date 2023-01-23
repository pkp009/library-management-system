package com.project.librarymanagementsystem.controller;

import com.project.librarymanagementsystem.exception.NotFoundException;
import com.project.librarymanagementsystem.models.Book;
import com.project.librarymanagementsystem.models.Ledger;
import com.project.librarymanagementsystem.models.Request;
import com.project.librarymanagementsystem.services.LibraryServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryControllerTest {
    @Mock
    private LibraryServices libraryService;

    @InjectMocks
    private LibraryController libraryController;

    @Test
    public void testGetAllBooks() {
        List<Book> books = List.of(new Book(), new Book());
        when(libraryService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>> response = libraryController.getAllBooks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    @Test
    public void testFindBookById() {
        Book book = new Book();
        when(libraryService.findBookById(1L)).thenReturn(book);

        ResponseEntity<Book> response = libraryController.findBookById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    public void testCheckOutBook() {
        Request request = new Request();
        request.setBookId(1L);
        request.setUserId(1L);
        Ledger ledger = new Ledger();
        when(libraryService.checkOutBook(1L, 1L)).thenReturn(ledger);

        ResponseEntity<Ledger> response = libraryController.checkOutBook(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ledger, response.getBody());
    }

    @Test
    public void testReturnBook_Success() {
        Request request = new Request();
        request.setBookId(1L);
        request.setUserId(1L);

        Map<String, String> response = Map.of("status", "success");
        when(libraryService.returnBook(request.getBookId(), request.getUserId())).thenReturn(response);
        ResponseEntity<Map<String, String>> result = libraryController.returnBook(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testReturnBook_Failure() {
        Request request = new Request();
        request.setBookId(1L);
        request.setUserId(1L);

        when(libraryService.returnBook(request.getBookId(), request.getUserId())).thenThrow(NotFoundException.class);

        try {
            libraryController.returnBook(request);
            fail();
        } catch (NotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCalculateFine() {
        Request request = new Request();
        request.setBookId(1L);
        request.setUserId(1L);
        Map<String, Integer> expected = Map.of("duesToBePaid", 10);
        when(libraryService.calculateFine(1L, 1L)).thenReturn(10);
        ResponseEntity<Map<String, Integer>> response = libraryController.calculateFine(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(libraryService).calculateFine(1L, 1L);
    }

}