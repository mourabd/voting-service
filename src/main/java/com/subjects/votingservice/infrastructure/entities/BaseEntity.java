package com.subjects.votingservice.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * Base entity.
 */
@Data
public abstract class BaseEntity {

    @Id
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDateTime;

    /**
     * Class constructor.
     */
    public BaseEntity() {
        setCreationDateTime(LocalDateTime.now());
    }
}
