package ru.gb.spring.homework.sem3.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.spring.homework.sem3.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {

    private final List<Book> books;

    public BookRepository() {
        this.books = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        books.addAll(List.of(
                new Book("Война и мир"),
                new Book("Мертвые души"),
                new Book("Чистый код")
        ));
    }

    public Optional<Book> getById(Long id) {
        return books.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    public void deleteById(Book book) {
        books.remove(book);
    }

    public List<Book> getAll() {
        return List.copyOf(books);
    }

    public void add(Book book) {
        books.add(book);
    }
}
