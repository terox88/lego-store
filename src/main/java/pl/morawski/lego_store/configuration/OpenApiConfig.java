package pl.morawski.lego_store.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI legoStoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lego Store API")
                        .description("REST API for managing Lego sets")
                        .version("1.0.0"));
    }
}