package com.subjects.votingservice.infrastructure.integration;

import com.subjects.votingservice.infrastructure.integration.dto.UserInfoResponseDto;

/**
 * Interface for user info service.
 */
public interface UserInfoService {

    /**
     * Gets user information to check its status to vote.
     *
     * @param cpf to be used to search user info
     * @return {@link UserInfoResponseDto} user info response data transfer object
     */
    UserInfoResponseDto getUserInfo(String cpf);
}
