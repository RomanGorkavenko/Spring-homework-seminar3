package ru.gb.spring.homework.sem3.api.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.repository.BookRepository;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class BookControllerTest extends JUnitSpringBootTestBase{

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    BookRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitBookResponse {
        Long id;
        private String name;
    }

    @Data
    static class JUnitBookRequest {
        private String name;
    }

    @Test
    void testFindAll() {
        repository.saveAll(List.of(
                new Book("first"),
                new Book("second")
        ));

        List<Book> expected = repository.findAll();

        List<JUnitBookResponse> actual = webTestClient.get()
                .uri("/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitBookResponse>>() {
                })
                .returnResult()
                .getResponseBody();

        assertNotNull(actual);
        log.warn("ex{} ac{}", expected.size(), actual.size());
        assertEquals(expected.size(), actual.size());

        for (JUnitBookResponse book: actual) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), book.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), book.getName()));
            assertTrue(found);
        }
    }

    @Test
    void testFindByIdNotFound() {
        repository.save(new Book("Not Found"));

        Long idMax = jdbcTemplate.queryForObject("SELECT MAX(id) FROM books", Long.class);

        assertNotNull(idMax);
        webTestClient.get()
                .uri("/book/" + (idMax + 1))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindByIdSuccess() {
        Book bookExpected = repository.save(new Book("Success"));

        JUnitBookResponse bookActual = webTestClient.get()
                .uri("/book/" + bookExpected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBookResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(bookActual);
        assertEquals(bookExpected.getId(), bookActual.getId());
        assertEquals(bookExpected.getName(), bookActual.getName());
    }

    @Test
    void testDelete() {
        Book delete = repository.save(new Book("Delete"));

        JUnitBookResponse response = webTestClient.delete()
                .uri("book/" + delete.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBookResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals(delete.getId(), response.getId());
        assertEquals(delete.getName(), response.getName());
        assertFalse(repository.findById(response.getId()).isPresent());
    }

    @Test
    void testAdd() {
        JUnitBookRequest request = new JUnitBookRequest();
        request.setName("requestAdd");

        JUnitBookResponse response = webTestClient.post()
                .uri("/book")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitBookResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertNotNull(response.getId());

        assertEquals(request.getName(), response.getName());
        assertTrue(repository.findById(response.getId()).isPresent());
    }
}