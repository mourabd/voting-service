package com.subjects.votingservice.shared.dto.subject;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Subject data transfer object.
 */
@Schema(description = "Subject data transfer object.")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectDto {

    @Schema(description = "Code")
    private String code;

    @NotBlank(message = "Title is required.")
    @Schema(required = true, description = "Title")
    private String title;

    @NotBlank(message = "Description is required.")
    @Schema(required = true, description = "Description")
    private String description;
}
