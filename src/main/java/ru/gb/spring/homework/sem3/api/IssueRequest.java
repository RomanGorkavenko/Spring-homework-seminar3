package ru.gb.spring.homework.sem3.api;

import lombok.Data;

/**
 * Запрос на выдачу
 */
@Data
public class IssueRequest {

    /**
     * Идентификатор читателя
     */
    private Long readerId;

    /**
     * Идентификатор книги
     */
    private Long bookId;

}
