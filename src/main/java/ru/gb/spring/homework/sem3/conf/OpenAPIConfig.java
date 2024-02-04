package ru.gb.spring.homework.sem3.conf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server serverDev = new Server();
        serverDev.setUrl(devUrl);
        serverDev.setDescription("URL-адрес сервера в среде разработки");

        Contact contact = new Contact();
        contact.setName("Roman Gorkavenko");
        contact.setEmail("roman@gorkavenko.ru");

        Info info = new Info()
                .title("Библиотека API")
                .version("1.0")
                .contact(contact)
                .description("Этот API предоставляет endpoints для управления библиотекой.");

        return new OpenAPI().info(info).servers(List.of(serverDev));
    }
}
