package ru.gb.spring.homework.sem3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.spring.homework.sem3.annotations.RecoverException;
import ru.gb.spring.homework.sem3.api.ReaderRequest;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.repository.ReaderRepository;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository repository;

    public List<Reader> findAll() {
        return repository.findAll();
    }

    public Reader findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\""));
    }

    public void delete(Long id) {
        Reader reader = findById(id);
        repository.delete(reader);
    }

    public Reader add(ReaderRequest request) {
        Reader reader = new Reader(request.getName());
        repository.save(reader);
        return reader;
    }

    @RecoverException(noRecoverFor = {MaxAllowedBooksException.class})
    public Set<Issue> getIssuesByReader(Long id) {
        Reader reader = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\""));
        Set<Issue> issuesByReader = reader.getIssues();
        if (issuesByReader.isEmpty()) {
            throw new IssuesByReaderException("Не найдены выдачи книг у читателя с идентификатором \"" + id + "\"");
        }
        return issuesByReader;
    }

    public Reader findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Не найден читатель с именем \"" + name + "\""));
    }

    /**
     *
     */
    @RecoverException(noRecoverFor = {})
    public int returnedPrimitive() {
        throw new NoSuchElementException("returnedPrimitive_int");
    }

    @RecoverException(noRecoverFor = {IllegalArgumentException.class})
    public boolean returnedPrimitive2() {
        throw new NoSuchElementException("returnedPrimitive_boolean");
    }
}
