package ru.gb.spring.homework.sem3.api.dto;

import lombok.Data;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.model.Reader;

import java.time.LocalDate;

@Data
public class IssueResponse {

    private Long id;
    private Book book;
    private Reader reader;
    private LocalDate issuedAt;
    private LocalDate returnedAt;
}
