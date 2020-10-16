package com.subjects.votingservice.shared.dto.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.subjects.votingservice.shared.dto.subject.SubjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Voting session response data transfer object.
 */
@Schema(description = "Voting session response data transfer object.")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
public class VotingSessionResponseDto {

    @NotNull(message = "Status is required.")
    @Schema(required = true, description = "Status")
    private Status status;

    @Valid
    @NotNull(message = "Subject is required.")
    @Schema(required = true, description = "Subject")
    private SubjectDto subject;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(pattern = "yyyy-MM-dd HH:mm:ss", description = "Expiration date.")
    private LocalDateTime expirationDate;

    /**
     * Status enumerator.
     */
    public enum Status {
        OPEN, CLOSED
    }
}
