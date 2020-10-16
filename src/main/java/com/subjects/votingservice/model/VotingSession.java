package com.subjects.votingservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Voting session entity.
 */
@Data
@Document
@EqualsAndHashCode(callSuper = true)
@Validated
public class VotingSession extends BaseEntity {

    @Valid
    @NotNull(message = "Subject is required.")
    private Subject subject;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expirationDate;

    private boolean notified;
}
