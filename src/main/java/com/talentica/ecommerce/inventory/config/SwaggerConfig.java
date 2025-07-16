package com.talentica.ecommerce.inventory.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("ecommerce-api")
                .pathsToMatch("/api/**")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info()
                        .title("E-Commerce API")
                        .description("API documentation for E-Commerce application")
                        .version("1.0")))
                .build();
    }
}