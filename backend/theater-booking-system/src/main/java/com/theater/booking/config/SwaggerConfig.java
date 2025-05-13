package com.theater.booking.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Components components = new Components();

        components.addResponses("ok", createResponse("200 OK", "{\"status\":\"success\",\"message\":\"Retrieved successfully\"}"));
        components.addResponses("created", createResponse("201 Created", "{\"status\":\"success\",\"message\":\"Created successfully\"}"));
        components.addResponses("noContent", createResponse("204 No Content", "{\"status\":\"success\",\"message\":\"No content\"}"));
        components.addResponses("badRequest", createResponse("400 Bad Request", "{\"status\":\"error\",\"message\":\"Invalid input\"}"));
        components.addResponses("notFound", createResponse("404 Not Found", "{\"status\":\"error\",\"message\":\"Resource not found\"}"));
        components.addResponses("internalError", createResponse("500 Internal Server Error", "{\"status\":\"error\",\"message\":\"Unexpected server error\"}"));

        return new OpenAPI()
                .info(new Info()
                        .title("Theater Booking System API")
                        .version("1.0.0")
                        .description("REST API for managing theater events, customers, and bookings.")
                        .contact(new Contact()
                                .name("Martin Alejandro Estrada Saavedra")
                                .url("https://github.com/martinSaav")
                                .email("martin996@hotmail.com.ar"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                )
                .components(components);
    }

    private ApiResponse createResponse(String description, String exampleJson) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType("application/json",
                        new MediaType().addExamples("default", new Example().value(exampleJson))));
    }
}
