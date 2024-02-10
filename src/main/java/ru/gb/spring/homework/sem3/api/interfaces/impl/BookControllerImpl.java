package ru.gb.spring.homework.sem3.api.interfaces.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.homework.sem3.api.interfaces.BookController;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.model.dto.BookRequest;
import ru.gb.spring.homework.sem3.service.BookService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Tag(name = "Книги", description = "Book API")
public class BookControllerImpl implements BookController {

    private final BookService service;

    @Override
    @GetMapping
    public List<Book> findAll() {
        return service.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") Long id) {
        Book book = service.findById(id);
        return ResponseEntity.ok(book);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable("id") Long id) {
        Book book = service.delete(id);
        return ResponseEntity.ok(book);
    }

    @Override
    @PostMapping
    public ResponseEntity<Book> add(@RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(request));
    }
}
