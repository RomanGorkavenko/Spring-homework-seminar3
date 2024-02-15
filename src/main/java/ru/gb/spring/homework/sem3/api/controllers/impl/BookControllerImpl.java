package ru.gb.spring.homework.sem3.api.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.homework.sem3.api.controllers.BookController;
import ru.gb.spring.homework.sem3.api.dto.BookDto;
import ru.gb.spring.homework.sem3.api.mappers.BookMapper;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.api.dto.BookRequest;
import ru.gb.spring.homework.sem3.service.BookService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Tag(name = "Книги", description = "Book API")
public class BookControllerImpl implements BookController {

    private final BookService service;

    private final BookMapper mapper;

    @Override
    @GetMapping
    public List<BookDto> findAll() {
        List<Book> books = service.findAll();
        return mapper.toDto(books);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable("id") Long id) {
        Book book = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(book));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> delete(@PathVariable("id") Long id) {
        Book book = service.delete(id);
        return ResponseEntity.ok(mapper.toDto(book));
    }

    @Override
    @PostMapping
    public ResponseEntity<BookDto> add(@RequestBody BookRequest request) {
        Book book = service.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(book));
    }
}
