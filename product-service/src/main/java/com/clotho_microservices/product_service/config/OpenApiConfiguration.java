package com.clotho_microservices.product_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI productSrviceOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Product Service API")
                        .version("1.0.0")
                        .description("API documentation for the Product Service")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new io.swagger.v3.oas.models.ExternalDocumentation()
                        .description("Find more info here")
                        .url("https://product-srvice.example.com/docs")
                );
    }
}

