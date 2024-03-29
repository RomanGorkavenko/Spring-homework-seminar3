package ru.gb.spring.homework.sem3.api.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.homework.sem3.api.controllers.ReaderController;
import ru.gb.spring.homework.sem3.api.dto.IssueResponse;
import ru.gb.spring.homework.sem3.api.dto.ReaderRequest;
import ru.gb.spring.homework.sem3.api.dto.ReaderResponse;
import ru.gb.spring.homework.sem3.api.mappers.ReaderMapper;
import ru.gb.spring.homework.sem3.service.exception.IssuesByReaderException;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.service.ReaderService;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
@Tag(name = "Читатели", description = "Reader Api")
public class ReaderControllerImpl implements ReaderController {

    private final ReaderService service;
    private final ReaderMapper mapper;

    @Override
    @GetMapping
    public List<ReaderResponse> findAll() {
        return mapper.toDto(service.findAll());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>("Читатель с идентификатором \"" + id + "\" успешно удален.", HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<ReaderResponse> add(@RequestBody ReaderRequest request) {
        Reader reader = service.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(reader));
    }

    @Override
    @GetMapping("/{id}/issues")
    public ResponseEntity<Set<IssueResponse>> getIssuesByReader(@PathVariable("id") Long id) {
        Set<Issue> issues = service.getIssuesByReader(id);
        service.returnedPrimitive();
        service.returnedPrimitive2();
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(issues));
    }

    @ExceptionHandler(IssuesByReaderException.class)
    public ResponseEntity<String> issuesByReaderException(IssuesByReaderException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ReaderResponse> findById(@PathVariable("id") Long id) {
        Reader reader = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(reader));
    }
}
