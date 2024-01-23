package ru.gb.spring.homework.sem3.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.spring.homework.sem3.model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReaderRepository {

    private final List<Reader> readers;

    public ReaderRepository() {
        this.readers = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        readers.addAll(List.of(
                new Reader("Игорь"),
                new Reader("Боб")
        ));
    }

    public Optional<Reader> getById(Long id) {
        return readers.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    public void deleteById(Reader reader) {
        readers.remove(reader);
    }

    public List<Reader> getAll() {
        return List.copyOf(readers);
    }

    public void add(Reader reader) {
        readers.add(reader);
    }
}
