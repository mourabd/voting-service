package com.subjects.votingservice.helper;

import com.subjects.votingservice.infrastructure.entities.Vote;
import com.subjects.votingservice.api.dto.vote.VoteRequestDto;
import com.subjects.votingservice.api.dto.vote.VoteResponseDto;

import static com.subjects.votingservice.helper.AssociateHelper.*;
import static com.subjects.votingservice.helper.SubjectHelper.CODE;
import static com.subjects.votingservice.helper.VotingSessionHelper.buildVotingSession;
import static com.subjects.votingservice.helper.VotingSessionHelper.buildVotingSessionResponseDto;

/**
 * Vote helper class.
 */
public final class VoteHelper {

    public static final boolean OPTION = true;

    /**
     * Builds a new instance of vote.
     *
     * @return new instance of {@link Vote}
     */
    public static Vote buildVote() {
        return new Vote(buildAssociate(), buildVotingSession(), true);
    }

    /**
     * Builds a new instance of vote response data transfer object.
     *
     * @return new instance of {@link VoteResponseDto}
     */
    public static VoteResponseDto buildVoteResponseDto() {
        final VoteResponseDto voteResponseDto = new VoteResponseDto();
        voteResponseDto.setAssociate(buildAssociateResponseDto());
        voteResponseDto.setSession(buildVotingSessionResponseDto());
        return voteResponseDto;
    }

    /**
     * Builds a new instance of vote request data transfer object.
     *
     * @return new instance of {@link VoteRequestDto}
     */
    public static VoteRequestDto buildVoteRequestDto() {
        final VoteRequestDto voteRequestDto = new VoteRequestDto();
        voteRequestDto.setCpf(CPF);
        voteRequestDto.setSubjectCode(CODE);
        voteRequestDto.setOption(OPTION);
        return voteRequestDto;
    }
}
