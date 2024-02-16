package ru.gb.spring.homework.sem3.api.mappers;

import org.mapstruct.Mapper;
import ru.gb.spring.homework.sem3.api.dto.IssueResponse;
import ru.gb.spring.homework.sem3.model.Issue;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IssueMapper {

    List<IssueResponse> toDto(List<Issue> issues);

    IssueResponse toDto(Issue issue);

    Issue toEntity(IssueResponse issueResponse);
}
