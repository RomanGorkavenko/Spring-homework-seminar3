package ru.gb.spring.homework.sem3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.spring.homework.sem3.model.Role;
import ru.gb.spring.homework.sem3.repository.RoleRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public List<Role> findAll() {
        return repository.findAll();
    }

    public Role findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Не найдена роль с именем \"" + name + "\""));
    }
}
