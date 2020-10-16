package com.subjects.votingservice.shared.dto.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Voting session request data transfer object.
 */
@Schema(description = "Voting session request data transfer object.")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VotingSessionRequestDto {

    @NotBlank(message = "Subject code is required.")
    @Schema(required = true, description = "Subject code")
    private String subjectCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(pattern = "yyyy-MM-dd HH:mm:ss", description = "Expiration date")
    private LocalDateTime expirationDate;
}