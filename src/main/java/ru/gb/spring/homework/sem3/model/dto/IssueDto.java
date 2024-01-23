package ru.gb.spring.homework.sem3.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
public class IssueDto {

    private final Long id;
    private final Long bookId;
    private final String bookName;
    private final Long readerId;
    private final String readerName;

    private final LocalDateTime issuedAt;
    private final LocalDateTime returnedAt;
}
