package app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Homework user-service API",
                version = "1.0",
                description = "Документация REST API для user-service"
        )
)
public class SwaggerConfig {
}
