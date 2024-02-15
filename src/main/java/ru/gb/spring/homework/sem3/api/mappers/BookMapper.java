package ru.gb.spring.homework.sem3.api.mappers;

import org.mapstruct.Mapper;
import ru.gb.spring.homework.sem3.api.dto.BookDto;
import ru.gb.spring.homework.sem3.model.Book;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    List<BookDto> toDto(List<Book> books);

    BookDto toDto(Book book);

    Book toEntity(BookDto dto);
}
