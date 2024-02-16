package ru.gb.spring.homework.sem3.api.mappers;

import org.mapstruct.Mapper;
import ru.gb.spring.homework.sem3.api.dto.IssueResponse;
import ru.gb.spring.homework.sem3.api.dto.ReaderResponse;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ReaderMapper {

    List<ReaderResponse> toDto(List<Reader> readers);

    ReaderResponse toDto(Reader reader);

    Set<IssueResponse> toDto(Set<Issue> issues);

    Reader toEntity (ReaderResponse response);
}
