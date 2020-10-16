package com.subjects.votingservice.configuration;

import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Configuration class for Mongo.
 */
@RequiredArgsConstructor
@Configuration
public class MongoConfiguration {

    private final MongoClient mongoClient;

    /**
     * Mongo template bean definition.
     *
     * @param databaseName database property name
     * @return new instance of {@link MongoTemplate}
     */
    @Bean
    public MongoTemplate mongoTemplate(@Value("${mongo.database.name}") String databaseName) {
        return new MongoTemplate(mongoClient, databaseName);
    }
}
