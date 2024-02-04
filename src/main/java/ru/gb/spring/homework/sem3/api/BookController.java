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
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.service.BookService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Tag(name = "Книги", description = "Book API")
public class BookController {

    private final BookService service;

    /**
     * Задание для 6 семинара
     * Описать все контроллеры, endpoints и возвращаемые тела с помощью аннотаций OpenAPI 3
     */
    @Operation(summary = "Получить все книги", description = "Выдает список всех книг")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Успешно", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Book.class)))
    })})
    @GetMapping
    public List<Book> findAll() {
        return service.findAll();
    }

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
    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id")
                                         @Parameter(name = "id", description = "Идентификатор книги",
                                         examples = {@ExampleObject(name = "Книга №1", value = "1",
                                         description = "Найти книгу с идентификатором \"1\""),
                                         @ExampleObject(name = "Книга №2", value = "2",
                                         description = "Найти книгу  с идентификатором \"2\"")}
                                         ) Long id) {
        Book book = service.findById(id);
        return ResponseEntity.ok(book);
    }

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
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable("id")
                                       @Parameter(name = "id", description = "Идентификатор книги",
                                       examples = {@ExampleObject(name = "Книга №1", value = "1",
                                       description = "Удалить книгу с идентификатором \"1\""),
                                       @ExampleObject(name = "Книга №2", value = "2",
                                       description = "Удалить книгу  с идентификатором \"2\"")}
                                       ) Long id) {
        Book book = service.delete(id);
        return ResponseEntity.ok(book);
    }

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
    @PostMapping
    public ResponseEntity<Book> add(@RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(request));
    }
}
