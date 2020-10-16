package com.subjects.votingservice.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

/**
 * Configuration class for kafka properties.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaConfigurationProperties {

    @NotBlank(message = "Topic is required")
    private String topic;

    private boolean enabled;
}
