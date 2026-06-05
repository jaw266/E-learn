package com.elearnpro.controller;

import com.elearnpro.dto.BookRequest;
import com.elearnpro.model.Book;
import com.elearnpro.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> all(@RequestParam(required = false) String domaine, @RequestParam(required = false) String keyword) {
        return bookRepository.findAll().stream()
                .filter(b -> domaine == null || domaine.isBlank() || b.getDomaine().toLowerCase().contains(domaine.toLowerCase()))
                .filter(b -> keyword == null || keyword.isBlank()
                        || b.getTitre().toLowerCase().contains(keyword.toLowerCase())
                        || b.getDescription().toLowerCase().contains(keyword.toLowerCase())
                        || b.getAuteur().toLowerCase().contains(keyword.toLowerCase()))
                .sorted(Comparator.comparing(Book::getDomaine).thenComparing(Book::getTitre))
                .toList();
    }

    @GetMapping("/free")
    public List<Book> freeBooks() {
        return bookRepository.findByGratuitTrue();
    }

    @PostMapping
    public Book create(@RequestBody BookRequest request) {
        return bookRepository.save(toBook(new Book(), request));
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody BookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow();
        return bookRepository.save(toBook(book, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    private Book toBook(Book book, BookRequest request) {
        book.setTitre(request.titre());
        book.setAuteur(request.auteur());
        book.setDomaine(request.domaine());
        book.setNiveau(request.niveau());
        book.setDescription(request.description());
        book.setLien(request.lien());
        book.setCouverture(request.couverture());
        book.setGratuit(request.gratuit());
        return book;
    }
}
