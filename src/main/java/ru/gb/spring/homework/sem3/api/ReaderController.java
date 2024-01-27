package ru.gb.spring.homework.sem3.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.homework.sem3.service.IssuesByReaderException;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.service.ReaderService;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService service;

    @GetMapping
    public List<Reader> findAll() {
        return service.findAll();
    }

    /**
     * Задание для 3 семинара
     * 1.2 GET /reader/{id} - получить описание читателя.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reader> findById(@PathVariable("id") Long id) {
        Reader reader;
        try {
            reader = service.findById(id);
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reader);
    }

    /**
     * Задание для 3 семинара
     * 1.2 DELETE /reader/{id} - удалить читателя.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Задание для 3 семинара
     * 1.2 POST /reader - создать читателя.
     */
    @PostMapping
    public ResponseEntity<Reader> add(@RequestBody ReaderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(request));
    }

    /**
     * Задание для 3 семинара
     * 2.2 В сервис читателя добавить ручку GET /reader/{id}/issue - вернуть список всех выдачей для данного читателя.
     */
    @GetMapping("/{id}/issues")
    public ResponseEntity<Set<Issue>> getIssuesByReader(@PathVariable("id") Long id) {
        Set<Issue> issues;
        try {
            issues = service.getIssuesByReader(id);
        } catch (IssuesByReaderException | NoSuchElementException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(issues);
    }
}
