package ru.gb.spring.homework.sem3.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "Запрос на добавление читателя")
public class ReaderRequest {

    @Schema(title = "Имя читателя", example = "Billy")
    private String name;
}
