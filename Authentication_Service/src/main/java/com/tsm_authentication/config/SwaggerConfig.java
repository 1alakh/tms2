package com.tsm_authentication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
Configuration class for setting up the OpenAPI documentation
for the Authentication Microservice.
<p>
This configuration provides metadata for the API documentation
        using Swagger.
</p>
 */


@Configuration
public class SwaggerConfig {
    private static final String API_TITLE = "Authentication Microservice API Documents";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "Authentication Microservice involves Authentication and Authorization of users and other routes.";

    private static final String CONTACT_NAME = "API Support";
    private static final String CONTACT_URL = "http://www.example.com/support";
    private static final String CONTACT_EMAIL = "support@example.com";
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(API_TITLE)
                        .version(API_VERSION)
                        .description(API_DESCRIPTION)
                        .contact(new Contact()
                                .name(CONTACT_NAME)
                                .url(CONTACT_URL)
                                .email(CONTACT_EMAIL)));
    }
}
