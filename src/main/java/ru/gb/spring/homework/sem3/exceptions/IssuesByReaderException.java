package ru.gb.spring.homework.sem3.exceptions;

public class IssuesByReaderException extends RuntimeException{
    public IssuesByReaderException() {
    }

    public IssuesByReaderException(String message) {
        super(message);
    }
}
