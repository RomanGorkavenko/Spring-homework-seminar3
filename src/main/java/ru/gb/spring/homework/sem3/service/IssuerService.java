package ru.gb.spring.homework.sem3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.spring.homework.sem3.api.IssueRequest;
import ru.gb.spring.homework.sem3.exceptions.MaxAllowedBooksException;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.repository.BookRepository;
import ru.gb.spring.homework.sem3.repository.IssueRepository;
import ru.gb.spring.homework.sem3.repository.ReaderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class IssuerService {

    /**
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

        bookRepository.getById(request.getBookId())
                .orElseThrow(() ->
                        new NoSuchElementException("Не найдена книга с идентификатором \"" + bookId + "\""));
        readerRepository.getById(request.getReaderId())
                .orElseThrow(() ->
                        new NoSuchElementException("Не найден читатель с идентификатором \"" + readerId + "\""));

        // region 2.1 В сервис IssueService добавить проверку, что у пользователя на руках нет книг.
        // Если есть - не выдавать книгу (статус ответа - 409 Conflict)
        List<Issue> readerHasBook = issueRepository.getIssuesByReader(request.getReaderId()).stream()
                // Замечание: возвращенные книги НЕ нужно учитывать при 2.1
                .filter(it -> it.getReturnedAt() == null)
                .toList();

        if (readerHasBook.size() == maxAllowedBooks) {
            throw new MaxAllowedBooksException("Достигнут лимит книг читателем с идентификатором \"" + readerId + "\"");
        }
        // endregion

        Issue issue = new Issue(request.getBookId(), request.getReaderId());
        issueRepository.save(issue);

        return issue;
    }

    public Issue getIssueById(Long id) {
        return issueRepository.getById(id).orElseThrow(() ->
                new NoSuchElementException("Не найдена выдача книги с идентификатором \"" + id + "\""));
    }

    public Issue updateIssueReturnedAt(Long id) {
        Issue issue = issueRepository.getById(id).orElseThrow(() ->
                new NoSuchElementException("Не найдена выдача книги с идентификатором \"" + id + "\""));
        issue.setReturnedAt(LocalDateTime.now());
        return issue;
    }
}
