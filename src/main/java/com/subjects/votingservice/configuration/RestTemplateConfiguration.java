package com.subjects.votingservice.configuration;

import com.subjects.votingservice.configuration.handler.RestTemplateResponseErrorHandler;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Configuration class for Rest Template.
 */
@Configuration
public class RestTemplateConfiguration extends RestTemplateAutoConfiguration {

    private static final int TIME_OUT_MILLISECONDS = 5000;

    /**
     * Rest template bean definition.
     *
     * @param builder builder
     * @return new instance of {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .setConnectTimeout(Duration.ofMillis(TIME_OUT_MILLISECONDS))
            .setReadTimeout(Duration.ofMillis(TIME_OUT_MILLISECONDS))
            .errorHandler(new RestTemplateResponseErrorHandler())
            .build();
    }
}
