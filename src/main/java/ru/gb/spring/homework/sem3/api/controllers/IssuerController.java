package ru.gb.spring.homework.sem3.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.api.dto.IssueRequest;

public interface IssuerController {

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
    ResponseEntity<Issue> issueBook(IssueRequest request);

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
    ResponseEntity<Issue> findById(@Parameter(name = "id", description = "Идентификатор выдачи",
                                  examples = {@ExampleObject(name = "Первая выдача", value = "1",
                                  description = "Найти выдачу книги с идентификатором \"1\""),
                                  @ExampleObject(name = "Вторая выдача", value = "2",
                                  description = "Найти выдачу книги с идентификатором \"2\"")}
                                  ) Long id);

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
    ResponseEntity<Issue> updateIssueReturnedAt(@Parameter(name = "id", description = "Идентификатор выдачи",
                                                examples = {@ExampleObject(name = "Первая выдача", value = "1",
                                                description = "Обновить дату возврата книги с идентификатором \"1\""),
                                                @ExampleObject(name = "Вторая выдача", value = "2",
                                                description = "Обновить дату возврата книги с идентификатором \"2\"")}
                                                ) Long id);
}
