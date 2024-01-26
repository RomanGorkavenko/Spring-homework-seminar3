package ru.gb.spring.homework.sem3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.spring.homework.sem3.model.Reader;

/**
 * Задание для 5 семинара.
 * 1.3 Заменить самописные репозитории на JPA-репозитории
 */
@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
