package ru.gb.spring.homework.sem3.exceptions;

public class MaxAllowedBooksException extends RuntimeException {

    public MaxAllowedBooksException() {
    }

    public MaxAllowedBooksException(String message) {
        super(message);
    }
}
