package ru.gb.spring.homework.sem3.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
@Tag(name = "Читатели", description = "Reader Api")
public class ReaderController {

    private final ReaderService service;

    /**
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Получить всех читателей", description = "Выдает список всех читателей")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Успешно", content = {
            @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Reader.class)))
    })})
    @GetMapping
    public List<Reader> findAll() {
        return service.findAll();
    }

    /**
     * Задание для 3 семинара
     * 1.2 GET /reader/{id} - получить описание читателя.
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Найти читателя по его идентификатору.", description = "Ищет читателя по его идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Читатель найден", content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Reader.class))
            }),
            @ApiResponse(responseCode = "404", description = "Не найден читатель", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Не найден читатель с идентификатором \"ID\""))
            })
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reader> findById(@PathVariable("id")
                                           @Parameter(name = "id", description = "Идентификатор читателя",
                                           examples = {@ExampleObject(name = "Читатель №1", value = "1",
                                           description = "Найти читателя с идентификатором \"1\""),
                                           @ExampleObject(name = "Читатель №2", value = "2",
                                           description = "Найти читателя с идентификатором \"2\"")}
                                           ) Long id) {
        Reader reader = service.findById(id);
        return ResponseEntity.ok(reader);
    }

    /**
     * Задание для 3 семинара
     * 1.2 DELETE /reader/{id} - удалить читателя.
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Удалить читателя по его идентификатору.", description = "Удаляет читателя по его идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Читатель удален", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Читатель с идентификатором \"ID\" успешно удален."))
            }),
            @ApiResponse(responseCode = "404", description = "Не найден читатель", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Не найден читатель с идентификатором \"ID\""))
            })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id")
                                         @Parameter(name = "id", description = "Идентификатор читателя",
                                         examples = {@ExampleObject(name = "Читатель №1", value = "1",
                                         description = "Удалить читателя с идентификатором \"1\""),
                                         @ExampleObject(name = "Читатель №2", value = "2",
                                         description = "Удалить читателя с идентификатором \"2\"")}
                                         ) Long id) {
        service.delete(id);
        return new ResponseEntity<>("Читатель с идентификатором \"" + id + "\" успешно удален.", HttpStatus.OK);
    }

    /**
     * Задание для 3 семинара
     * 1.2 POST /reader - создать читателя.
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Добавить читателя", description = "Добавляет читателя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Читатель добавлен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Reader.class))
            })
    })
    @PostMapping
    public ResponseEntity<Reader> add(@RequestBody ReaderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(request));
    }

    /**
     * Задание для 3 семинара
     * 2.2 В сервис читателя добавить ручку GET /reader/{id}/issue - вернуть список всех выдачей для данного читателя.
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Показать список выдачи книг читателю по его идентификатору",
            description = "Выдает список выдачи книг читателю по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Issue.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Не найдены выдачи книг у читателя", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Не найден читатель с идентификатором \"ID\"\n" +
                                    "Не найдены выдачи книг у читателя с идентификатором \"ID\""))
            })
    })
    @GetMapping("/{id}/issues")
    public ResponseEntity<Set<Issue>> getIssuesByReader(@PathVariable("id")
                                        @Parameter(name = "id", description = "Идентификатор читателя",
                                        examples = {@ExampleObject(name = "Читатель №1", value = "1",
                                        description = "Показать список выдачи книг у читателя с идентификатором \"1\""),
                                        @ExampleObject(name = "Читатель №2", value = "2",
                                        description = "Показать список выдачи книг у читателя с идентификатором \"2\"")}
                                        ) Long id) {
        Set<Issue> issues = service.getIssuesByReader(id);
        return ResponseEntity.status(HttpStatus.OK).body(issues);
    }

    @ExceptionHandler(IssuesByReaderException.class)
    public ResponseEntity<String> issuesByReaderException(IssuesByReaderException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
