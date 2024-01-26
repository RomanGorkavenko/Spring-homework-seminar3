package ru.gb.spring.homework.sem3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.spring.homework.sem3.api.IssueRequest;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.repository.BookRepository;
import ru.gb.spring.homework.sem3.repository.IssueRepository;
import ru.gb.spring.homework.sem3.repository.ReaderRepository;


import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class IssuerService {

    /**
     * Задание для 3 семинара
     * 3.3** Пункт 2.1 расширить параметром, сколько книг может быть на руках у пользователя.
     * Должно задаваться в конфигурации (параметр application.issue.max-allowed-books).
     * Если параметр не задан - то использовать значение 1.
     */
    @Value("${application.issue.max-allowed-books:1}")
    private int maxAllowedBooks;

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    public Issue issue(IssueRequest request) {

        final Long readerId = request.getReaderId();
        final Long bookId = request.getBookId();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new NoSuchElementException("Не найдена книга с идентификатором \"" + bookId + "\""));
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() ->
                        new NoSuchElementException("Не найден читатель с идентификатором \"" + readerId + "\""));

        // 2.1 В сервис IssueService добавить проверку, что у пользователя на руках нет книг.
        // Если есть - не выдавать книгу (статус ответа - 409 Conflict)
        // Замечание: возвращенные книги НЕ нужно учитывать при 2.1
        List<Issue> readerHasBook = issueRepository.findByReaderAndReturnedAtNull(reader);
        if (readerHasBook.size() == maxAllowedBooks) {
            throw new MaxAllowedBooksException("Достигнут лимит книг читателем с идентификатором \"" + readerId + "\"");
        }

        Issue issue = new Issue(book, reader);
        issueRepository.save(issue);

        return issue;
    }

    public Issue findById(Long id) {
        return issueRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Не найдена выдача книги с идентификатором \"" + id + "\""));
    }

    public Issue updateIssueReturnedAt(Long id) {
        Issue issue = issueRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Не найдена выдача книги с идентификатором \"" + id + "\""));
        issue.setReturnedAt(LocalDateTime.now());
        issueRepository.save(issue);
        return issue;
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    public List<Book> findBookByReaderAndReturnedAtNull(Reader reader) {
        return issueRepository.findBookByReaderAndReturnedAtNull(reader);
    }

}
