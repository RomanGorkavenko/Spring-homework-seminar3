package ru.gb.spring.homework.sem3.api.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.repository.BookRepository;
import ru.gb.spring.homework.sem3.repository.IssueRepository;
import ru.gb.spring.homework.sem3.repository.ReaderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ReaderControllerTest extends JUnitSpringBootTestBase {

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitReaderRequest {
        private String name;
    }

    @Data
    static class JUnitReaderResponse {
        private Long id;
        private String name;
        private Set<Issue> issues;
    }

    @Data
    static class JUnitIssueResponse {
        private Long id;
        private Book book;
        private Reader reader;
        private LocalDateTime issuedAt;
        private LocalDateTime returnedAt;
    }

    @Test
    void testFindAll() {
        readerRepository.saveAll(List.of(
                new Reader("first"),
                new Reader("second")
        ));

        List<Reader> excepted = readerRepository.findAll();

        List<JUnitReaderResponse> actual = webTestClient.get()
                .uri("/reader")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitReaderResponse>>() {
                })
                .returnResult().getResponseBody();

        assertNotNull(actual);
        assertEquals(excepted.size(), actual.size());

        for (JUnitReaderResponse reader : actual) {
            boolean found = excepted.stream()
                    .filter(it -> Objects.equals(it.getId(), reader.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), reader.getName()));
            assertTrue(found);
        }

    }

    @Test
    void testDelete() {
        Reader delete = readerRepository.save(new Reader("Delete"));

        String response = webTestClient.delete()
                .uri("reader/" + delete.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertEquals(response, "Читатель с идентификатором \"" + delete.getId() + "\" успешно удален.");
        assertFalse(readerRepository.findById(delete.getId()).isPresent());
    }

    @Test
    void testAdd() {
        JUnitReaderRequest request = new JUnitReaderRequest();
        request.setName("seventh");

        JUnitReaderResponse response = webTestClient.post()
                .uri("/reader")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitReaderResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(request.getName(), response.getName());
        assertTrue(readerRepository.findById(response.getId()).isPresent());
    }

    @Test
    void testGetIssuesByReader() {
        Reader reader = readerRepository.save(new Reader("third"));
        Book book1 = bookRepository.save(new Book("first"));
        Book book2 = bookRepository.save(new Book("second"));
        Issue issue1 = issueRepository.save(new Issue(book1, reader));
        Issue issue2 = issueRepository.save(new Issue(book2, reader));
        reader.setIssues(Set.of(issue1, issue2));

        Set<Issue> expected = reader.getIssues();

        Set<JUnitIssueResponse> actual = webTestClient.get()
                .uri("/reader/" + reader.getId() + "/issues")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Set<JUnitIssueResponse>>() {
                })
                .returnResult().getResponseBody();

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (JUnitIssueResponse issue: actual) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), issue.getId()))
                    .filter(it -> Objects.equals(it.getBook(), issue.getBook()))
                    .anyMatch(it -> Objects.equals(it.getReader(), issue.getReader()));
            assertTrue(found);
        }
    }

    @Test
    void testGetIssuesByReaderNotFound() {
        Reader reader = readerRepository.save(new Reader("fourth"));

        webTestClient.get()
                .uri("/reader/" + reader.getId() + "/issues")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindByIdSuccess() {
        Reader readerExpected = readerRepository.save(new Reader("fifth"));

        JUnitReaderResponse readerActual = webTestClient.get()
                .uri("/reader/" + readerExpected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitReaderResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(readerActual);
        assertNotNull(readerActual.getId());
        assertEquals(readerExpected.getName(), readerActual.getName());
        assertTrue(readerRepository.findById(readerActual.getId()).isPresent());
    }

    @Test
    void testFindByIdNotFound() {
        readerRepository.save(new Reader("sixth"));

        Long maxId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM readers", Long.class);

        assertNotNull(maxId);
        webTestClient.get()
                .uri("/reader/" + (maxId + 1))
                .exchange()
                .expectStatus().isNotFound();
    }
}