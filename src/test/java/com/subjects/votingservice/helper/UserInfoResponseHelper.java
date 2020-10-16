package com.subjects.votingservice.helper;

import com.subjects.votingservice.integration.dto.UserInfoResponseDto;

/**
 * User info response data transfer object helper class.
 */
public final class UserInfoResponseHelper {

    /**
     * Builds a new instance of user info response data transfer object.
     *
     * @param status status
     * @return new instance of {@link UserInfoResponseDto}
     */
    public static UserInfoResponseDto buildUserInfoResponseDto(UserInfoResponseDto.StatusEnum status) {
        final UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto();
        userInfoResponseDto.setStatus(status);
        return userInfoResponseDto;
    }
}
