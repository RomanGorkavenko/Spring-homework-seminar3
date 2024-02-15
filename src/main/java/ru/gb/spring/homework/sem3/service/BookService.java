package ru.gb.spring.homework.sem3.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;
import ru.gb.spring.homework.sem3.aop.annotations.Loggable;
import ru.gb.spring.homework.sem3.api.dto.BookRequest;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    @Loggable(level = Level.WARN)
    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найдена книга с идентификатором \"" + id + "\""));
    }

    public Book delete(Long id) {
        Book book = findById(id);
        repository.delete(book);
        return book;
    }

    public Book add(BookRequest request) {
        Book book = new Book(request.getName());
        repository.save(book);
        return book;
    }
}
