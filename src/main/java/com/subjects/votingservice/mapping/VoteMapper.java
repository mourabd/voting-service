package com.subjects.votingservice.mapping;

import com.subjects.votingservice.model.Vote;
import com.subjects.votingservice.shared.dto.vote.VoteRequestDto;
import com.subjects.votingservice.shared.dto.vote.VoteResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Vote mapper.
 */
@Mapper
public interface VoteMapper {

    /**
     * Maps vote entity from vote request data transfer object.
     *
     * @param voteRequestDto vote request data transfer object
     * @return {@link Vote} vote entity
     */
    @Mapping(source = "cpf", target = "associate.cpf")
    @Mapping(source = "subjectCode", target = "session.subject.code")
    Vote voteRequestDtoToVote(VoteRequestDto voteRequestDto);

    /**
     * Maps vote response data transfer object from vote entity.
     *
     * @param vote vote entity
     * @return {@link VoteResponseDto} vote response data transfer object
     */
    VoteResponseDto voteToVoteResponseDto(Vote vote);
}
