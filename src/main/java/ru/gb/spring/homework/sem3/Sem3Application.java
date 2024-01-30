package ru.gb.spring.homework.sem3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.gb.spring.homework.sem3.conf.IssueProperties;

@SpringBootApplication
@EnableConfigurationProperties(IssueProperties.class)
public class Sem3Application {

    public static void main(String[] args) {
        SpringApplication.run(Sem3Application.class, args);
    }

}
