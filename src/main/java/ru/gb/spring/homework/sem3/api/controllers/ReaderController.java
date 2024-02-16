package ru.gb.spring.homework.sem3.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import ru.gb.spring.homework.sem3.api.dto.IssueResponse;
import ru.gb.spring.homework.sem3.api.dto.ReaderResponse;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.api.dto.ReaderRequest;

import java.util.List;
import java.util.Set;

public interface ReaderController {

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
    ResponseEntity<ReaderResponse> findById(@Parameter(name = "id", description = "Идентификатор читателя",
                                   examples = {@ExampleObject(name = "Читатель №1", value = "1",
                                   description = "Найти читателя с идентификатором \"1\""),
                                   @ExampleObject(name = "Читатель №2", value = "2",
                                   description = "Найти читателя с идентификатором \"2\"")}
                                   ) Long id);

    /**
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Получить всех читателей", description = "Выдает список всех читателей")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Успешно", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Reader.class)))
    })})
    List<ReaderResponse> findAll();

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
    ResponseEntity<String> delete(@Parameter(name = "id", description = "Идентификатор читателя",
                                 examples = {@ExampleObject(name = "Читатель №1", value = "1",
                                 description = "Удалить читателя с идентификатором \"1\""),
                                 @ExampleObject(name = "Читатель №2", value = "2",
                                 description = "Удалить читателя с идентификатором \"2\"")}
                                 ) Long id);

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
    ResponseEntity<ReaderResponse> add(ReaderRequest request);

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
    ResponseEntity<Set<IssueResponse>> getIssuesByReader(@Parameter(name = "id", description = "Идентификатор читателя",
                                                examples = {@ExampleObject(name = "Читатель №1", value = "1",
                                                description = "Показать список выдачи книг у читателя с идентификатором \"1\""),
                                                @ExampleObject(name = "Читатель №2", value = "2",
                                                description = "Показать список выдачи книг у читателя с идентификатором \"2\"")}
                                                ) Long id);
}
