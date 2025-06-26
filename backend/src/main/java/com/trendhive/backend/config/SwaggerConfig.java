package com.trendhive.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TrendHive API 문서")
                        .description("TrendHive 백엔드 API 명세서입니다.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Younggyun Lee")
                                .email("younggyun12@hotmail.com")
                        )
                );
    }
}