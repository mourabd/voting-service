package com.subjects.votingservice.shared.dto.vote;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Vote request data transfer object.
 */
@Schema(description = "Vote request data transfer object.")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteRequestDto {

    @NotBlank(message = "CPF is required.")
    @Schema(required = true, description = "CPF")
    private String cpf;

    @NotBlank(message = "Subject code is required.")
    @Schema(required = true, description = "Subject code")
    private String subjectCode;

    @NotNull(message = "Option is required.")
    @Schema(required = true, description = "Option")
    private Boolean option;
}
