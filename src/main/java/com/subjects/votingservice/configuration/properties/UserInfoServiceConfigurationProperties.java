package com.subjects.votingservice.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

/**
 * Configuration class for user info service properties.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "user-info-service")
public class UserInfoServiceConfigurationProperties {

    @NotBlank(message = "Attribute URL is required")
    private String url;
}
