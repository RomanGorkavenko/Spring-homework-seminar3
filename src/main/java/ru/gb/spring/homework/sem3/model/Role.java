package ru.gb.spring.homework.sem3.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

/**
 * Задание для 7 семинара
 * 2.1* Реализовать таблицы User(id, name, password) и Role(id, name), связанные многие ко многим
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
@Schema(title = "Роль")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "Идентификатор роли", example = "1")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @Schema(title = "Название роли", example = "USER")
    private String name;

    public Role(String name) {
        this.name = name;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany
    @JoinTable(name = "user_role", //schema = "todolist", catalog = "postgres",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    //@JsonManagedReference // добавил для того что бы не было рекурсии
    private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
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
