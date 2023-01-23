package com.project.librarymanagementsystem.repository;

import com.project.librarymanagementsystem.models.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerRepository extends JpaRepository<Ledger, Long> {
    List<Ledger> findById(long userId);

    Ledger findByBookIdAndUserId(long bookId, long userId);

}