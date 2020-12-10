package com.subjects.votingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * SpringBoot application class.
 */
@EnableMongoRepositories(basePackages = "com.subjects.votingservice.infrastructure.repository")
@SpringBootApplication
public class VotingServiceApplication {

    /**
     * Application main method.
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(VotingServiceApplication.class, args);
    }
}
