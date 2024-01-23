package ru.gb.spring.homework.sem3.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.service.BookService;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping
    public List<Book> getBookAll() {
        return service.getBookAll();
    }

    /**
     * 1.1 GET /book/{id} - получить описание книги.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book book;
        try {
            book = service.getBookById(id);
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    /**
     * 1.1 DELETE /book/{id} - удалить книгу.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable("id") Long id) {
        Book book;
        try {
            book = service.deleteBookById(id);
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    /**
     * 1.1 POST /book - создать книгу.
     */
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addBook(request));
    }
}
