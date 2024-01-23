package ru.gb.spring.homework.sem3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.spring.homework.sem3.api.BookRequest;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public List<Book> getBooksAll() {
        return repository.getAll();
    }

    public Book getBookById(Long id) {
        return repository.getById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найдена книга с идентификатором \"" + id + "\""));
    }

    public Book deleteBookById(Long id) {
        Book book = getBookById(id);
        repository.deleteById(book);
        return book;
    }

    public Book addBook(BookRequest request) {
        Book book = new Book(request.getName());
        repository.add(book);
        return book;
    }
}
