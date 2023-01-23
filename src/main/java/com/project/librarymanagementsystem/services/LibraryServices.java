package com.project.librarymanagementsystem.services;

import com.project.librarymanagementsystem.exception.LimitExceededException;
import com.project.librarymanagementsystem.exception.NotFoundException;
import com.project.librarymanagementsystem.exception.PendingDuesException;
import com.project.librarymanagementsystem.models.Book;
import com.project.librarymanagementsystem.models.Ledger;
import com.project.librarymanagementsystem.models.Members;
import com.project.librarymanagementsystem.repository.BookRepository;
import com.project.librarymanagementsystem.repository.LedgerRepository;
import com.project.librarymanagementsystem.repository.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class LibraryServices {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MembersRepository membersRepository;

    @Autowired
    private LedgerRepository ledgerRepository;

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void addMember(Members members) {
        membersRepository.save(members);
    }

    public Ledger checkOutBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new NotFoundException(String.format("Book with ID %d not found", bookId));
        }
        if (book.getNoOfCopies() == 0) {
            throw new LimitExceededException("All copies of this book is already checked out");
        }
        Members members = membersRepository.findById(userId).orElse(null);
        if (members == null) {
            throw new NotFoundException(String.format("Member with ID %d not found", userId));
        }
        if (members.getNoOfBooksIssued() >= 2) {
            throw new LimitExceededException("Max no. of books already checked out. Please return before checking new book");
        }
        Ledger ledger = new Ledger();
        if (members.getDues() > 0) {
            throw new PendingDuesException(String.format("Please clear your due amount of %d before checkout", members.getDues()));
        } else {
            ledger.setBookId(bookId);
            ledger.setUserId(userId);
            ledger.setCheckOutDate(LocalDate.now());
            ledger.setExpectedReturnDate(ledger.getCheckOutDate().plusWeeks(2));
            book.setNoOfCopies(book.getNoOfCopies() - 1);
            members.setNoOfBooksIssued(members.getNoOfBooksIssued() + 1);
            membersRepository.save(members);
            bookRepository.save(book);
            ledgerRepository.save(ledger);
        }
        return ledger;
    }

    public Map<String, String> returnBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        Members member = membersRepository.findById(userId).orElse(null);
        List<Ledger> ledgers = ledgerRepository.findAllById(Arrays.asList(userId));
        Ledger ledger = ledgers.stream().filter(c -> c.getBookId() == bookId).findFirst().get();
        if (ledger == null) {
            throw new NotFoundException(String.format("No entry found in the ledger with bookID %d and userId %d", bookId, userId));
        }
        int dues = calculateFine(bookId, userId);

        member.setDues(member.getDues() + dues);
        member.setNoOfBooksIssued(member.getNoOfBooksIssued() - 1);
        book.setNoOfCopies(book.getNoOfCopies() + 1);
        ledger.setReturnDate(LocalDate.now());
        ledger.setDues(dues);

        membersRepository.save(member);
        bookRepository.save(book);
        ledgerRepository.save(ledger);
        return Map.of("message", "Book returned successfully", "duesToBePaid", String.valueOf(dues));
    }

    public int calculateFine(Long bookId, Long userId) {
        List<Ledger> ledgers = ledgerRepository.findAllById(Arrays.asList(userId));
        Ledger ledger = ledgers.stream().filter(c -> c.getBookId() == bookId).findFirst().get();
        if (ledger == null) {
            throw new NotFoundException(String.format("No entry found in the ledger with bookID %d and userId %d", bookId, userId));
        }
        long daysIssued = ChronoUnit.DAYS.between(ledger.getCheckOutDate(), LocalDate.now());
        int dues = 0;

        if (daysIssued > 14) {
            dues = (int) ((daysIssued - 14) * 50);
        }

        return dues;
    }

    public void payDues(Long userId) {
        Members members = membersRepository.findById(userId).orElse(null);
        if (members == null) {
            throw new NotFoundException(String.format("Member with ID %d not found", userId));
        }
        members.setDues(0);
        membersRepository.save(members);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(String.format("Book with ID %d not found ", bookId)));
        return book;
    }
}