package com.subjects.votingservice.helper;

import com.subjects.votingservice.model.VotingSession;
import com.subjects.votingservice.shared.dto.session.VotingSessionRequestDto;
import com.subjects.votingservice.shared.dto.session.VotingSessionResponseDto;
import com.subjects.votingservice.shared.dto.session.VotingSessionResultDto;

import java.time.LocalDateTime;

import static com.subjects.votingservice.helper.SubjectHelper.*;

/**
 * Voting Session helper class.
 */
public final class VotingSessionHelper {

    public static final LocalDateTime EXPIRATION_DATE = LocalDateTime.now();
    public static final VotingSessionResponseDto.Status STATUS = VotingSessionResponseDto.Status.OPEN;
    public static final int SESSION_LIST_ELEMENTS_NUMBER = 1;

    /**
     * Builds a new instance of voting session.
     *
     * @return new instance of {@link VotingSession}
     */
    public static VotingSession buildVotingSession() {
        final VotingSession votingSession = new VotingSession();
        votingSession.setExpirationDate(EXPIRATION_DATE);
        votingSession.setSubject(buildSubject());
        return votingSession;
    }

    /**
     * Builds a new instance of voting session result data transfer object.
     *
     * @return new instance of {@link VotingSessionResultDto}
     */
    public static VotingSessionResultDto buildVotingSessionResultDto() {
        return VotingSessionResultDto.builder()
            .session(buildVotingSessionResponseDto())
            .resultDto(
                VotingSessionResultDto.ResultDto.builder()
                    .build())
            .build();
    }

    /**
     * Builds a new instance of voting session request data transfer object.
     *
     * @return new instance of {@link VotingSessionRequestDto}
     */
    public static VotingSessionRequestDto buildVotingSessionRequestDto() {
        final VotingSessionRequestDto votingSessionRequestDto = new VotingSessionRequestDto();
        votingSessionRequestDto.setSubjectCode(CODE);
        votingSessionRequestDto.setExpirationDate(EXPIRATION_DATE);
        return votingSessionRequestDto;
    }

    /**
     * Builds a new instance of voting session response data transfer object.
     *
     * @return new instance of {@link VotingSessionResponseDto}
     */
    public static VotingSessionResponseDto buildVotingSessionResponseDto() {
        final VotingSessionResponseDto votingSessionResponseDto = new VotingSessionResponseDto();
        votingSessionResponseDto.setStatus(STATUS);
        votingSessionResponseDto.setExpirationDate(EXPIRATION_DATE);
        votingSessionResponseDto.setSubject(buildSubjectDto());
        return votingSessionResponseDto;
    }
}
