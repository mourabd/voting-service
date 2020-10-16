package com.subjects.votingservice.service;

import com.subjects.votingservice.shared.dto.session.VotingSessionResultDto;
import com.subjects.votingservice.shared.dto.vote.VoteRequestDto;
import com.subjects.votingservice.shared.dto.vote.VoteResponseDto;

/**
 * Vote service interface.
 */
public interface VoteService {

    /**
     * Saves a vote.
     *
     * @param voteRequestDto {@link VoteRequestDto} vote request data transfer object
     * @return {@link VoteResponseDto} vote response data transfer object
     */
    VoteResponseDto save(VoteRequestDto voteRequestDto);

    /**
     * Searches voting session result by subject code.
     *
     * @param subjectCode to be used to search voting session
     * @return {@link VotingSessionResultDto} voting session result data transfer object
     */
    VotingSessionResultDto findVotingSessionResultsBySubjectCode(String subjectCode);
}
