package com.subjects.votingservice.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Rest error response data transfer object.
 */
@Schema(description = "Default rest error response.")
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestErrorResponseDto {

    @Schema(required = true, description = "HTTP status for this error")
    private HttpStatus status;

    @NotBlank
    @Schema(required = true, description = "Error message")
    private String message;

    @Schema(description = "List of errors")
    private List<String> errors;

    /**
     * Class constructor.
     *
     * @param status  status
     * @param message message
     */
    public RestErrorResponseDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
