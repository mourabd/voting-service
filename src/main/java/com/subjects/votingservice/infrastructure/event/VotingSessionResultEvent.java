package com.subjects.votingservice.infrastructure.event;

import com.subjects.votingservice.api.dto.session.VotingSessionResultDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Voting session results event.
 */
@Data
public class VotingSessionResultEvent {

    @NotBlank(message = "Version is required.")
    private final transient String version;

    @NotNull(message = "Voting session result is required.")
    private final transient VotingSessionResultDto votingSessionResult;

    /**
     * Class constructor.
     *
     * @param votingSessionResultDto voting session result data transfer object.
     */
    public VotingSessionResultEvent(VotingSessionResultDto votingSessionResultDto) {
        this.version = UUID.randomUUID().toString();
        this.votingSessionResult = votingSessionResultDto;
    }
}
