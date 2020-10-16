package com.subjects.votingservice.shared.dto.associate;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * Associate response data transfer object.
 */
@Schema(description = "Associate response data transfer object.")
@Data
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssociateResponseDto {

    @NotBlank(message = "CPF is required.")
    @Schema(required = true, description = "CPF")
    private String cpf;

    @NotBlank(message = "First name is required.")
    @Schema(required = true, description = "First name")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Schema(required = true, description = "Last name")
    private String lastName;
}
