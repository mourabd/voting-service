package com.subjects.votingservice.mapping;

import com.subjects.votingservice.model.Vote;
import com.subjects.votingservice.shared.dto.vote.VoteResponseDto;
import org.mapstruct.Mapper;

/**
 * Vote mapper.
 */
@Mapper
public interface VoteMapper {

    /**
     * Maps vote response data transfer object from vote entity.
     *
     * @param vote vote entity
     * @return {@link VoteResponseDto} vote response data transfer object
     */
    VoteResponseDto voteToVoteResponseDto(Vote vote);
}
