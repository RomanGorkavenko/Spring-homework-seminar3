package ru.gb.spring.homework.sem3.api.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.model.dto.BookRequest;

import java.util.List;

public interface BookController {

    /**
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Получить все книги", description = "Выдает список всех книг")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Успешно", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Book.class)))
    })})
    List<Book> findAll();

    /**
     * Задание для 3 семинара
     * 1.1 GET /book/{id} - получить описание книги.
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Найти книгу по ее идентификатору.", description = "Ищет книгу по ее идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга найдена", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))
            }),
            @ApiResponse(responseCode = "404", description = "Не найдена книга", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Не найдена книга с идентификатором \"ID\""))
            })
    })
    ResponseEntity<Book> findById(@Parameter(name = "id", description = "Идентификатор книги",
                                 examples = {@ExampleObject(name = "Книга №1", value = "1",
                                 description = "Найти книгу с идентификатором \"1\""),
                                 @ExampleObject(name = "Книга №2", value = "2",
                                 description = "Найти книгу  с идентификатором \"2\"")}
                                 ) Long id);

    /**
     * Задание для 3 семинара
     * 1.1 DELETE /book/{id} - удалить книгу.
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Удалить книгу по его идентификатору.", description = "Удаляет книгу по его идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга удалена", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))
            }),
            @ApiResponse(responseCode = "404", description = "Не найдена книга", content = {
                    @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Не найдена книга с идентификатором \"ID\""))
            })
    })
    ResponseEntity<Book> delete(@Parameter(name = "id", description = "Идентификатор книги",
                               examples = {@ExampleObject(name = "Книга №1", value = "1",
                               description = "Удалить книгу с идентификатором \"1\""),
                               @ExampleObject(name = "Книга №2", value = "2",
                               description = "Удалить книгу  с идентификатором \"2\"")}
                               ) Long id);

    /**
     * Задание для 3 семинара
     * 1.1 POST /book - создать книгу.
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Добавить книгу", description = "Добавляет книгу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Книга добавлена", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
            })
    })
    ResponseEntity<Book> add(BookRequest request);
}
