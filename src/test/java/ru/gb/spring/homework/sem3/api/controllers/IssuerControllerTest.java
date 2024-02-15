package ru.gb.spring.homework.sem3.api.controllers;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.repository.BookRepository;
import ru.gb.spring.homework.sem3.repository.IssueRepository;
import ru.gb.spring.homework.sem3.repository.ReaderRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class IssuerControllerTest extends JUnitSpringBootTestBase{

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitIssueResponse {
        private Long id;
        private Book book;
        private Reader reader;
        private LocalDateTime issuedAt;
        private LocalDateTime returnedAt;
    }

    @Data
    static class JUnitIssueRequest {
        private Long bookId;
        private Long readerId;
    }

    @Test
    void testIssueBook() {
        Book book = bookRepository.save(new Book("first book"));
        Reader reader = readerRepository.save(new Reader("first reader"));

        JUnitIssueRequest request = new JUnitIssueRequest();
        request.setBookId(book.getId());
        request.setReaderId(reader.getId());

        JUnitIssueResponse response = webTestClient.post()
                .uri("/issue")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitIssueResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(request.getBookId(), response.getBook().getId());
        assertEquals(request.getReaderId(), response.getReader().getId());
        assertTrue(issueRepository.findById(response.getId()).isPresent());
    }

    @Test
    void testIssueBookConflict() {
        Book book1 = bookRepository.save(new Book("fifth book"));
        Book book2 = bookRepository.save(new Book("sixth book"));
        Book book3 = bookRepository.save(new Book("seventh book"));
        Reader reader = readerRepository.save(new Reader("fifth reader"));
        issueRepository.save(new Issue(book1, reader));
        issueRepository.save(new Issue(book2, reader));

        JUnitIssueRequest request = new JUnitIssueRequest();
        request.setBookId(book3.getId());
        request.setReaderId(reader.getId());

        webTestClient.post()
                .uri("/issue")
                .bodyValue(request)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void testFindByIdSuccess() {
        Book book = bookRepository.save(new Book("second book"));
        Reader reader = readerRepository.save(new Reader("second reader"));
        Issue issueExpected = issueRepository.save(new Issue(book, reader));

        JUnitIssueResponse issueActual = webTestClient.get()
                .uri("/issue/" + issueExpected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssueResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(issueActual);
        assertNotNull(issueActual.getId());
        assertEquals(issueExpected.getBook(), issueActual.getBook());
        assertEquals(issueExpected.getReader(), issueActual.getReader());
        assertTrue(issueRepository.findById(issueActual.getId()).isPresent());
    }

    @Test
    void testFindByIdNotFound() {
        Book book = bookRepository.save(new Book("third book"));
        Reader reader = readerRepository.save(new Reader("third reader"));
        issueRepository.save(new Issue(book, reader));

        Long idMax = jdbcTemplate.queryForObject("SELECT MAX(id) FROM issues", Long.class);

        assertNotNull(idMax);
        webTestClient.get()
                .uri("/issue/" + (idMax + 1))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateIssueReturnedAt() {
        Book book = bookRepository.save(new Book("fourth book"));
        Reader reader = readerRepository.save(new Reader("fourth reader"));
        Issue expected = issueRepository.save(new Issue(book, reader));

        JUnitIssueResponse actual = webTestClient.put()
                .uri("/issue/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssueResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(actual);
        assertTrue(issueRepository.findById(actual.getId()).isPresent());
        assertTrue(() -> expected.getReturnedAt() == null);
        assertNotNull(actual.getReturnedAt());
    }
}