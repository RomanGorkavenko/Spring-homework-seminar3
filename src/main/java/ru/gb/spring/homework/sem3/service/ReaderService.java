package ru.gb.spring.homework.sem3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.spring.homework.sem3.api.ReaderRequest;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.repository.IssueRepository;
import ru.gb.spring.homework.sem3.repository.ReaderRepository;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    public List<Reader> getReadersAll() {
        return readerRepository.getAll();
    }

    public Reader getReaderById(Long id) {
        return readerRepository.getById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\""));
    }

    public void deleteReaderById(Long id) {
        Reader reader = getReaderById(id);
        readerRepository.deleteById(reader);
    }

    public Reader addReader(ReaderRequest request) {
        Reader reader = new Reader(request.getName());
        readerRepository.add(reader);
        return reader;
    }

    public List<Issue> getIssuesByReader(Long id) {
        readerRepository.getById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\""));
        List<Issue> issuesByReader = issueRepository.getIssuesByReader(id);
        if (issuesByReader.isEmpty()) {
            throw new IssuesByReaderException("Не найдены выдачи у читатель с идентификатором \"" + id + "\"");
        }
        return issuesByReader;
    }
}
