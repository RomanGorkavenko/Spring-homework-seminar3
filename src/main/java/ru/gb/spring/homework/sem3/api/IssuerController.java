package ru.gb.spring.homework.sem3.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ru.gb.spring.homework.sem3.service.MaxAllowedBooksException;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.service.IssuerService;

@Slf4j
@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
@Tag(name = "Выдача книг", description = "Issue API")
public class IssuerController {

    private final IssuerService service;

    /**
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Выдать книгу читателю", description = "Делает запись о выдачи книги читателю.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Выдача прошла успешно, запись создана", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Issue.class))
            }),
            @ApiResponse(responseCode = "404", description = "Не найден читатель или книга", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Не найден читатель с идентификатором \"ID\"\n" +
                                    "Не найдена книга с идентификатором \"ID\""))
            }),
            @ApiResponse(responseCode = "409", description = "Достигнут лимит книг читателем", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Достигнут лимит книг читателем с идентификатором \"ID\""))
            })
    })
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

    /**
     * Задание для 3 семинара
     * 1.3 В контроллере IssueController добавить ресурс
     * GET /issue/{id} - получить описание факта выдачи.
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Получить выдачу книги по ее идентификатору",
            description = "Получаем выдачу книги читателю по ее идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Нашли выдачу по идентификатору", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Issue.class))
            }),
            @ApiResponse(responseCode = "404", description = "Выдача не найдена", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Не найдена выдача книги с идентификатором \"ID\""))
            })
    })
    @GetMapping("/{id}")
    public ResponseEntity<Issue> findById(@PathVariable("id")
                                          @Parameter(name = "id", description = "Идентификатор выдачи",
                                                  examples = {@ExampleObject(name = "Первая выдача", value = "1",
                                                      description = "Найти выдачу книги с идентификатором \"1\""),
                                                      @ExampleObject(name = "Вторая выдача", value = "2",
                                                      description = "Найти выдачу книги с идентификатором \"2\"")}
                                                    ) Long id) {
        Issue issue = service.findById(id);
        return ResponseEntity.ok(issue);
    }

    /**
     * Задание для 3 семинара
     * 3.2* К ресурс POST /issue добавить запрос PUT /issue/{issueId},
     * который закрывает факт выдачи. (т.е. проставляет returned_at в Issue).
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Запись даты возврата книги",
            description = "Делаем запись о возврата книги читателю по ее идентификатору, обновляем дату возврата книги.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Дата возврата книги по идентификатору успешно обновлена",
                    content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Issue.class))
            }),
            @ApiResponse(responseCode = "404", description = "Выдача не найдена", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Не найдена выдача книги с идентификатором \"ID\""))
            })
    })
    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssueReturnedAt(
            @PathVariable("id") @Parameter(name = "id", description = "Идентификатор выдачи",
                    examples = {@ExampleObject(name = "Первая выдача", value = "1",
                    description = "Обновить дату возврата книги с идентификатором \"1\""),
                    @ExampleObject(name = "Вторая выдача", value = "2",
                    description = "Обновить дату возврата книги с идентификатором \"2\"")}
            ) Long id) {
        Issue issue = service.updateIssueReturnedAt(id);
        return ResponseEntity.ok(issue);
    }

}
