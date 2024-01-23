package ru.gb.spring.homework.sem3.repository;

import org.springframework.stereotype.Repository;
import ru.gb.spring.homework.sem3.model.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class IssueRepository {

    private final List<Issue> issues;

    public IssueRepository() {
        this.issues = new ArrayList<>();
    }

    public void save(Issue issue) {
        issues.add(issue);
    }

    public Optional<Issue> getById(Long id) {
        return issues.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    public List<Issue> getAll() {
        return List.copyOf(issues);
    }

    /**
     * По аналогии с @Query запросом в базу в репозитории,
     * чтобы получать не все Issue, а только нужную выборку.
     * Отдаем работу БД.
     * Вопрос, на сколько это правильно? Или лучше получать все Issue из БД
     * и внутри приложения работать с коллекцией.
     */
    public List<Issue> getIssuesByReader(Long readerId) {
        return issues.stream()
                .filter(it -> it.getReaderId().equals(readerId))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
