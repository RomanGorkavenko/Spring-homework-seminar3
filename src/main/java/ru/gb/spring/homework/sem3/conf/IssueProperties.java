package ru.gb.spring.homework.sem3.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("application.issue")
public class IssueProperties {

    private int maxAllowedBooks;
}
