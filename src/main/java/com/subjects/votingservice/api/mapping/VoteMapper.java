package com.subjects.votingservice.api.mapping;

import com.subjects.votingservice.infrastructure.entities.Vote;
import com.subjects.votingservice.api.dto.vote.VoteResponseDto;
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
