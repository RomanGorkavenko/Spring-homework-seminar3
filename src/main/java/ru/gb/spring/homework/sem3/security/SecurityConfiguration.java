package ru.gb.spring.homework.sem3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Задание для 7 семинара
 * 1. Для ресурсов, возвращающих HTML-страницы, реализовать авторизацию через login-форму.
 * Остальные /api ресурсы, возвращающие JSON, закрывать не нужно!
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.loginPage("/ui/login").permitAll().defaultSuccessUrl("/ui/books"))
//                .formLogin(Customizer.withDefaults())
                .exceptionHandling(accessDenied -> accessDenied.accessDeniedPage("/ui/403"))
                .logout(form -> form.logoutSuccessUrl("/ui/login").permitAll())
                .build();
    }

}
