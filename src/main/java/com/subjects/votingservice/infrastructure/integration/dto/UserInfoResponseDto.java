package com.subjects.votingservice.infrastructure.integration.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * User info response data transfer object.
 */
@NoArgsConstructor
@Data
public class UserInfoResponseDto {

    @NotNull(message = "Status is required.")
    private StatusEnum status;

    /**
     * Status enumerator.
     */
    public enum StatusEnum {
        ABLE_TO_VOTE, UNABLE_TO_VOTE
    }
}
