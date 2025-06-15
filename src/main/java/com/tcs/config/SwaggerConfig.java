package com.tcs.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for setting up Swagger/OpenAPI 3 documentation.
 * This configures the API info and enables JWT Bearer authentication for secured endpoints.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI documentation.
     * - Sets the API title and version.
     * - Adds JWT Bearer authentication scheme.
     * - Applies the security requirement globally to all endpoints.
     *
     * @return the configured OpenAPI object
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("StarProtect API")      // API title shown in Swagger UI
                        .version("1.0"))              // API version shown in Swagger UI
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)   // HTTP type (not API key or OAuth2)
                                .scheme("bearer")                 // Use bearer token scheme
                                .bearerFormat("JWT")))            // Specify token format as JWT
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth")); // Apply globally
    }
}
