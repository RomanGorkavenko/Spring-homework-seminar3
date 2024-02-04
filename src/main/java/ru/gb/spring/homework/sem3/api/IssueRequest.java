package ru.gb.spring.homework.sem3.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Запрос на выдачу
 */
@Data
@Schema(title = "Запрос на добавление записи о выдачи книги")
public class IssueRequest {

    /**
     * Идентификатор читателя
     */
    @Schema(title = "Идентификатор читателя", example = "2")
    private Long readerId;

    /**
     * Идентификатор книги
     */
    @Schema(title = "Идентификатор книги", example = "1")
    private Long bookId;

}
