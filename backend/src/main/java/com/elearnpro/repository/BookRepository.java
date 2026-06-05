package com.elearnpro.repository;

import com.elearnpro.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGratuitTrue();
    List<Book> findByDomaineContainingIgnoreCase(String domaine);
    List<Book> findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrDomaineContainingIgnoreCase(String titre, String description, String domaine);
}
