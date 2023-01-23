package com.project.librarymanagementsystem.repository;

import com.project.librarymanagementsystem.models.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembersRepository extends JpaRepository<Members, Long> {
    List<Members> findById(long userId);
}
