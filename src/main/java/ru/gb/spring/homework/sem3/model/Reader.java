package ru.gb.spring.homework.sem3.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Reader {

    private static Long sequence = 1L;

    private final Long id;
    private final String name;

    public Reader(String name) {
        this(sequence++, name);
    }
}
