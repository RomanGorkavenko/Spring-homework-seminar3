package ru.gb.spring.homework.sem3.api.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.homework.sem3.aop.annotations.Timer;
import ru.gb.spring.homework.sem3.api.controllers.IssuerController;
import ru.gb.spring.homework.sem3.api.dto.IssueRequest;
import ru.gb.spring.homework.sem3.service.exception.MaxAllowedBooksException;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.service.IssuerService;

@Slf4j
@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
@Tag(name = "Выдача книг", description = "Issue API")
public class IssuerControllerImpl implements IssuerController {

    private final IssuerService service;

    @Override
    @PostMapping
    public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
        log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());
        Issue issue = service.issue(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(issue);
    }

    /**
     * Задание для 3 семинара
     * 2.1 В сервис IssueService добавить проверку, что у пользователя на руках нет книг.
     * Если есть - не выдавать книгу (статус ответа - 409 Conflict)
     */
    @ExceptionHandler(MaxAllowedBooksException.class)
    public ResponseEntity<String> maxAllowedBooksException(MaxAllowedBooksException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @Timer
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Issue> findById(@PathVariable("id") Long id) {
        Issue issue = service.findById(id);
        return ResponseEntity.ok(issue);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssueReturnedAt(@PathVariable("id") Long id) {
        Issue issue = service.updateIssueReturnedAt(id);
        return ResponseEntity.ok(issue);
    }
}
