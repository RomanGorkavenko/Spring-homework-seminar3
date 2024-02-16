package ru.gb.spring.homework.sem3.api.mappers;

import org.mapstruct.Mapper;
import ru.gb.spring.homework.sem3.api.dto.BookResponse;
import ru.gb.spring.homework.sem3.model.Book;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    List<BookResponse> toDto(List<Book> books);

    BookResponse toDto(Book book);

    Book toEntity(BookResponse bookResponse);
}
