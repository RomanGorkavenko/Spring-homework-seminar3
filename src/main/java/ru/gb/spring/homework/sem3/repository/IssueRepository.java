package ru.gb.spring.homework.sem3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;

import java.util.List;

/**
 * Задание для 5 семинара.
 * 1.3 Заменить самописные репозитории на JPA-репозитории
 */
@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByReaderAndReturnedAtNull(Reader reader);

    @Query("""
            SELECT i.book
            FROM Issue i
            WHERE i.reader = :reader
            AND i.returnedAt IS NULL
            """)
    List<Book> findBookByReaderAndReturnedAtNull(@Param("reader") Reader reader);
}
