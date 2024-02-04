package ru.gb.spring.homework.sem3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

/**
 * Задание для 5 семинара.
 * 1.2 Для книги, читателя и факта выдачи описать JPA-сущности.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "readers")
@Schema(title = "Читатель")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "Идентификатор читателя", example = "1")
    private Long id;

    @Schema(title = "Имя читателя", example = "John")
    private String name;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    @OneToMany(mappedBy = "reader")
    private Set<Issue> issues;

    public Reader(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return Objects.equals(id, reader.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
