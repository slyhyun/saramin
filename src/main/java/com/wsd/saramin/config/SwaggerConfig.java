package com.wsd.saramin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Saramin Application API")
                        .version("1.0.0")
                        .description("Saramin 지원 API 문서입니다.")
                        .contact(new Contact()
                                .name("Saramin Support")
                                .email("support@saramin.com")))
                .servers(List.of(
                        new Server().url("http://localhost:80").description("로컬 환경"),
                        new Server().url("https://113.198.66.75:18209").description("외부 환경")
                ));
    }
}
