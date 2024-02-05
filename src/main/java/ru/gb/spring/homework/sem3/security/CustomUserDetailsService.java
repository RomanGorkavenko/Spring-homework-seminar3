package ru.gb.spring.homework.sem3.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gb.spring.homework.sem3.model.User;
import ru.gb.spring.homework.sem3.service.UserService;

/**
 * Задание для 7 семинара
 * 2.2* Реализовать UserDetailsService, который предоставляет данные из БД (таблицы User и Role)
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.findByLogin(username);
        return new UserDetailsImpl(user);
    }
}
