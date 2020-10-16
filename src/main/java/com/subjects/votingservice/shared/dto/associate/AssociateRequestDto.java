package com.subjects.votingservice.shared.dto.associate;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Associate request data transfer object.
 */
@Schema(description = "Associate request data transfer object.")
@Data
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssociateRequestDto {

    @NotBlank(message = "First name is required.")
    @Schema(required = true, description = "First name")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Schema(required = true, description = "Last name")
    private String lastName;

    @NotBlank(message = "CPF is required.")
    @Pattern(regexp = "^-?\\d{11}$", message = "Attribute CPF must have 11 digits.")
    @Schema(required = true, description = "CPF")
    private String cpf;
}
