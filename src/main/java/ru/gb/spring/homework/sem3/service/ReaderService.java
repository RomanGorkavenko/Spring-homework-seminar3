package ru.gb.spring.homework.sem3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.spring.homework.sem3.api.ReaderRequest;
import ru.gb.spring.homework.sem3.exceptions.IssuesByReaderException;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.repository.IssueRepository;
import ru.gb.spring.homework.sem3.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository repository;
    private final IssueRepository issueRepository;

    public List<Reader> getBookAll() {
        return repository.getAll();
    }

    public Reader getReaderById(Long id) {
        return repository.getById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\""));
    }

    public void deleteReaderById(Long id) {
        Reader reader = getReaderById(id);
        repository.deleteById(reader);
    }

    public Reader addReader(ReaderRequest request) {
        Reader reader = new Reader(request.getName());
        repository.add(reader);
        return reader;
    }

    public List<Issue> getIssuesByReader(Long id) {
        repository.getById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\""));
        List<Issue> issuesByReader = issueRepository.getIssuesByReader(id);
        if (issuesByReader.isEmpty()) {
            throw new IssuesByReaderException("Не найдены выдачи у читатель с идентификатором \"" + id + "\"");
        }
        return issuesByReader;
    }
}
