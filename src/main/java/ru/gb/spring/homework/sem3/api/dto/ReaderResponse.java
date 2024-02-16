package ru.gb.spring.homework.sem3.api.dto;

import lombok.Data;
import ru.gb.spring.homework.sem3.model.Issue;

import java.util.Set;

@Data
public class ReaderResponse {

    private Long id;
    private String name;
    private Set<Issue> issues;
}
