package com.subjects.votingservice.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Vote entity.
 */
@Data
@Document
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@CompoundIndex(name = "associate_session_index", def = "{'associate.id' : 1, 'session.id': 1}", unique = true)
@Validated
public class Vote extends BaseEntity {

    @Valid
    @NotNull(message = "Associate is required.")
    private Associate associate;

    @Valid
    @NotNull(message = "Voting session is required.")
    private VotingSession session;

    @NotNull(message = "Option is required.")
    private Boolean option;
}
