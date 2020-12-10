package com.subjects.votingservice.mapping;

import com.subjects.votingservice.domain.exception.InvalidDateTimeException;
import com.subjects.votingservice.infrastructure.entities.VotingSession;
import com.subjects.votingservice.api.dto.session.VotingSessionRequestDto;
import com.subjects.votingservice.api.dto.session.VotingSessionResponseDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Voting Session mapper.
 */
@Mapper
public interface VotingSessionMapper {

    /**
     * Maps voting session entity from voting session request data transfer object.
     *
     * @param votingSessionRequestDto voting session request data transfer object
     * @return {@link VotingSession} voting session
     */
    @Mapping(source = "subjectCode", target = "subject.code")
    VotingSession votingSessionRequestDtoToVotingSession(VotingSessionRequestDto votingSessionRequestDto);

    /**
     * Maps voting session response data transfer object from voting session entity.
     *
     * @param votingSession voting session entity
     * @return {@link VotingSessionResponseDto} voting session response data transfer object
     */
    VotingSessionResponseDto votingSessionToVotingSessionResponseDto(VotingSession votingSession);

    /**
     * Maps voting session response data transfer object list from voting session entity list.
     *
     * @param votingSessionList voting session entity list
     * @return {@link List} of {@link VotingSessionResponseDto} voting session response data transfer object
     */
    List<VotingSessionResponseDto> votingSessionListToVotingSessionResponseDtoList(List<VotingSession> votingSessionList);

    /**
     * Validates expiration date.
     *
     * @param votingSessionRequestDto voting session request data transfer object
     * @param votingSession           voting session entity
     * @return {@link VotingSession} voting session entity
     */
    @AfterMapping
    default VotingSession validateExpirationDate(VotingSessionRequestDto votingSessionRequestDto,
                                                 @MappingTarget VotingSession votingSession) {

        final LocalDateTime expirationDate = votingSession.getExpirationDate();
        if (Objects.nonNull(expirationDate) && expirationDate.isBefore(LocalDateTime.now())) {
            throw new InvalidDateTimeException();
        }

        if (Objects.isNull(expirationDate)) {
            votingSession.setExpirationDate(LocalDateTime.now().plusMinutes(1));
        }

        return votingSession;
    }

    /**
     * Updates voting session response data transfer object status.
     *
     * @param votingSession            voting session entity
     * @param votingSessionResponseDto voting session response data transfer object
     * @return {@link VotingSessionResponseDto} voting session response data transfer object
     */
    @AfterMapping
    default VotingSessionResponseDto updateStatus(VotingSession votingSession,
                                                  @MappingTarget VotingSessionResponseDto votingSessionResponseDto) {
        final VotingSessionResponseDto.Status status =
            votingSession.getExpirationDate().isAfter(LocalDateTime.now())
                ? VotingSessionResponseDto.Status.OPEN
                : VotingSessionResponseDto.Status.CLOSED;
        votingSessionResponseDto.setStatus(status);
        return votingSessionResponseDto;
    }
}
