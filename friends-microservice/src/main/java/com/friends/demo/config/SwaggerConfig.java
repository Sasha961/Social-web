package com.friends.demo.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

        @OpenAPIDefinition(
        info = @Info(
                title = "Microservice friend",
                description = "Сервис друзей", version = "0.0.1-SNAPSHOT"
        )
)
       public class SwaggerConfig {
}
