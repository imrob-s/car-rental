package dev.imrob.carrental.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
               .info(new io.swagger.v3.oas.models.info.Info()
                       .title("REST API - Car Rental")
                       .version("v1")
                       .description("API para gerenciamento de locação de carros")
                       .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENCE-2.0"))
                       .contact(new Contact().name("Robson Silva").url("https://www.linkedin.com/in/robsilva1/")
                               .email("orobsilva@gmail.com"))
               );
    }
}
