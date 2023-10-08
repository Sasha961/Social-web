package ru.skillbox.group39.socialnetwork.notifications.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
		security = {@SecurityRequirement(name = "BearerToken")},
		info = @Info(
				title = "Notifications micro service",
				description = "Сервис уведомлений",
				version = "0.0.1-SNAPSHOT"))

public class SwaggerConfig {
	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
		return openApi -> openApi.getComponents()
				.addSecuritySchemes("BearerToken",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));
	}
}
