package ru.gb.spring.homework.sem3.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Задание для 5 семинара.
 * Запись о факте выдачи книги (в БД)
 * 1.2 Для книги, читателя и факта выдачи описать JPA-сущности.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_id", referencedColumnName = "id")
    private Reader reader;

    /**
     * 3.1* В Issue поле timestamp разбить на 2: issued_at, returned_at - дата выдачи и дата возврата
     */
    private LocalDateTime issuedAt;
    private LocalDateTime returnedAt;

    public Issue(Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
        this.issuedAt = LocalDateTime.now();
        this.returnedAt = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(id, issue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", bookId=" + book +
                ", readerId=" + reader +
                '}';
    }
}