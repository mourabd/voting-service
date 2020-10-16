package com.subjects.votingservice.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration class for swagger documentation.
 */
@Configuration
@PropertySource("classpath:swagger.properties")
public class SwaggerConfiguration {

    /**
     * Open API bean definition.
     *
     * @param title       title property
     * @param description description property
     * @param version     version property
     * @return new instance of {@link OpenAPI}
     */
    @Bean
    public OpenAPI openApi(@Value("${swagger.api.title}") String title,
                           @Value("${swagger.api.description}") String description,
                           @Value("${swagger.api.version}") String version) {

        return new OpenAPI().info(
            new Info()
                .title(title)
                .description(description)
                .version(version));
    }
}
