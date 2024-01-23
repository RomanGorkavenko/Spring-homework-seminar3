package ru.gb.spring.homework.sem3.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
public class Issue {

    private static Long sequence = 1L;

    private final Long id;
    private final Long bookId;
    private final Long readerId;

    /**
     * 3.1* В Issue поле timestamp разбить на 2: issued_at, returned_at - дата выдачи и дата возврата
     */
    private final LocalDateTime issuedAt;
    private LocalDateTime returnedAt;

    public Issue(Long bookId, Long readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.id = sequence++;
        this.issuedAt = LocalDateTime.now();
        this.returnedAt = null;
    }
}
