package com.subjects.votingservice.api.dto.session;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Voting session result data transfer object.
 */
@Schema(description = "Voting session result data transfer object.")
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VotingSessionResultDto {

    @Valid
    @NotNull(message = "Session is required.")
    @Schema(required = true, description = "Voting session")
    private VotingSessionResponseDto session;

    @NotNull(message = "Result is required.")
    @Schema(required = true, description = "Result")
    private ResultDto resultDto;

    /**
     * Result data transfer object.
     */
    @Data
    @Builder
    public static class ResultDto {

        @Schema(required = true, description = "Number of YES votes.")
        private long yes;

        @Schema(required = true, description = "Number of NO votes.")
        private long no;
    }
}
