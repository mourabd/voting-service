package com.subjects.votingservice.shared.dto.vote;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.subjects.votingservice.shared.dto.associate.AssociateResponseDto;
import com.subjects.votingservice.shared.dto.session.VotingSessionResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Vote response data transfer object.
 */
@Schema(description = "Vote response data transfer object.")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteResponseDto {

    @Valid
    @NotNull(message = "Associate is required.")
    @Schema(required = true, description = "Associate")
    private AssociateResponseDto associate;

    @Valid
    @NotNull(message = "Voting session is required.")
    @Schema(required = true, description = "Voting session")
    private VotingSessionResponseDto session;
}
