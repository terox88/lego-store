package pl.morawski.lego_store.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI legoStoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lego Store API")
                        .description("\"REST API for managing Lego sets, stock levels, pricing and availability. \"\n" +
                                "                                + \"Includes filtering, sorting, JWT authentication and request metrics.\"")
                        .version("1.0.0"));
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}