package ru.gb.spring.homework.sem3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.spring.homework.sem3.aop.annotations.Timer;
import ru.gb.spring.homework.sem3.api.dto.IssueRequest;
import ru.gb.spring.homework.sem3.conf.IssueProperties;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.repository.BookRepository;
import ru.gb.spring.homework.sem3.repository.IssueRepository;
import ru.gb.spring.homework.sem3.repository.ReaderRepository;
import ru.gb.spring.homework.sem3.service.exception.MaxAllowedBooksException;


import java.time.LocalDate;
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
//    @Value("${application.issue.max-allowed-books:1}")
//    private int maxAllowedBooks;

    private final IssueProperties issueProperties;
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
        if (readerHasBook.size() == issueProperties.getMaxAllowedBooks()) {
            throw new MaxAllowedBooksException("Достигнут лимит книг читателем с идентификатором \"" + readerId + "\"");
        }

        Issue issue = new Issue(book, reader);
        issueRepository.save(issue);

        return issue;
    }

    @Timer
    public Issue findById(Long id) {
        return issueRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Не найдена выдача книги с идентификатором \"" + id + "\""));
    }

    public Issue updateIssueReturnedAt(Long id) {
        Issue issue = issueRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Не найдена выдача книги с идентификатором \"" + id + "\""));
        issue.setReturnedAt(LocalDate.now());
        issueRepository.save(issue);
        return issue;
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    @Timer
    public List<Book> findBookByReaderAndReturnedAtNull(Reader reader) {
        return issueRepository.findBookByReaderAndReturnedAtNull(reader);
    }

    public List<Issue> findIssueByBookIdAndReaderIdAndReturnedAtNull(Long bookId, Long readerId) {
        return issueRepository.findIssueByBook_IdAndReader_IdAndReturnedAtNull(bookId, readerId);
    }
}
