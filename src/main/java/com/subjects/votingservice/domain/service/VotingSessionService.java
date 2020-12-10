package com.subjects.votingservice.domain.service;

import com.subjects.votingservice.api.dto.session.VotingSessionRequestDto;
import com.subjects.votingservice.api.dto.session.VotingSessionResponseDto;

import java.util.List;

/**
 * Voting session service interface.
 */
public interface VotingSessionService {

    /**
     * Saves a new session for voting.
     *
     * @param votingSessionRequestDto {@link VotingSessionRequestDto} voting session request data transfer object
     * @return {@link VotingSessionResponseDto} voting session response data transfer object
     */
    VotingSessionResponseDto save(VotingSessionRequestDto votingSessionRequestDto);

    /**
     * Searches voting session by subject code.
     *
     * @param subjectCode to be used to search voting session
     * @return {@link VotingSessionResponseDto} voting session response data transfer object
     */
    VotingSessionResponseDto findBySubjectCode(String subjectCode);

    /**
     * Retrieves all voting sessions.
     *
     * @return {@link List} of {@link VotingSessionResponseDto} voting session response data transfer object
     */
    List<VotingSessionResponseDto> findAll();
}
