package ru.gb.spring.homework.sem3.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.homework.sem3.service.MaxAllowedBooksException;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.service.IssuerService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssuerController {

    private final IssuerService service;

    @PostMapping
    public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
        log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());

        Issue issue;
        try {
            issue = service.issue(request);
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (MaxAllowedBooksException e) {
            log.warn(e.getMessage());

            // 2.1 В сервис IssueService добавить проверку, что у пользователя на руках нет книг.
            // Если есть - не выдавать книгу (статус ответа - 409 Conflict)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(issue);
    }

    /**
     * 1.3 В контроллере IssueController добавить ресурс
     * GET /issue/{id} - получить описание факта выдачи
     */
    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable("id") Long id) {
        Issue issue;
        try {
            issue = service.getIssueById(id);
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssueReturnedAt(@PathVariable("id") Long id) {
        Issue issue;
        try {
            issue = service.updateIssueReturnedAt(id);
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(issue);
    }

}
