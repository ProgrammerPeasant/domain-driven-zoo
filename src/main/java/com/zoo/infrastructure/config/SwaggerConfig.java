package com.zoo.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI zooManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Zoo Management API")
                        .description("API для управления зоопарком, разработанная по принципам DDD и Clean Architecture")
                        .version("1.0.0"));
    }
}