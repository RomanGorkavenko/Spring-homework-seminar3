package ru.gb.spring.homework.sem3.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Задание для 5 семинара.
 * 1.2 Для книги, читателя и факта выдачи описать JPA-сущности.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "books")
@Schema(title = "Книга")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "Идентификатор книги", example = "1")
    private Long id;

    @Schema(title = "Название книги", example = "Head First Java")
    private String name;

    public Book(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
