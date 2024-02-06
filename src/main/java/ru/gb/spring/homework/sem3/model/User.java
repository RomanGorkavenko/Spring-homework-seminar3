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
@Table(name = "users")
@Schema(title = "Пользователь")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "Идентификатор пользователя", example = "1")
    private Long id;

    @Column(name = "login")
    @Schema(title = "Логин", example = "1")
    private String login;

    @Column(name = "password")
    @Schema(title = "Пароль", example = "1")
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    @Schema(title = "Роли", example = "1")
    private Set<Role> roles ;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", roles=" + roles +
                '}';
    }
}
