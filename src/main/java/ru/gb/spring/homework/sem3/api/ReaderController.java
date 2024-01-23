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

@Slf4j
@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService service;

    @GetMapping
    public List<Reader> getReadersAll() {
        return service.getReadersAll();
    }

    /**
     * 1.2 GET /reader/{id} - получить описание читателя.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable("id") Long id) {
        Reader reader;
        try {
            reader = service.getReaderById(id);
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reader);
    }

    /**
     * 1.2 DELETE /reader/{id} - удалить читателя.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReaderById(@PathVariable("id") Long id) {
        try {
            service.deleteReaderById(id);
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 1.2 POST /reader - создать читателя.
     */
    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestBody ReaderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addReader(request));
    }

    @GetMapping("/{id}/issues")
    public ResponseEntity<List<Issue>> getIssuesByReader(@PathVariable("id") Long id) {
        List<Issue> issues;
        try {
            issues = service.getIssuesByReader(id);
        } catch (IssuesByReaderException | NoSuchElementException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(issues);
    }
}
