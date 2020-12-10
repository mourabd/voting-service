package com.subjects.votingservice.domain.service.impl;

import com.subjects.votingservice.configuration.properties.KafkaConfigurationProperties;
import com.subjects.votingservice.domain.exception.AssociateAlreadyVotedException;
import com.subjects.votingservice.domain.exception.AssociateNotFoundException;
import com.subjects.votingservice.domain.exception.AssociateUnableToVoteException;
import com.subjects.votingservice.domain.exception.SessionExpiredException;
import com.subjects.votingservice.domain.exception.VotingSessionNotFoundException;
import com.subjects.votingservice.infrastructure.integration.UserInfoService;
import com.subjects.votingservice.infrastructure.integration.dto.UserInfoResponseDto;
import com.subjects.votingservice.api.mapping.VoteMapper;
import com.subjects.votingservice.api.mapping.VotingSessionMapper;
import com.subjects.votingservice.infrastructure.entities.Associate;
import com.subjects.votingservice.infrastructure.entities.Vote;
import com.subjects.votingservice.infrastructure.entities.VotingSession;
import com.subjects.votingservice.infrastructure.repository.AssociateRepository;
import com.subjects.votingservice.infrastructure.repository.VoteRepository;
import com.subjects.votingservice.infrastructure.repository.VotingSessionRepository;
import com.subjects.votingservice.domain.service.VoteService;
import com.subjects.votingservice.api.dto.session.VotingSessionResultDto;
import com.subjects.votingservice.api.dto.vote.VoteRequestDto;
import com.subjects.votingservice.api.dto.vote.VoteResponseDto;
import com.subjects.votingservice.infrastructure.event.VotingSessionResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.subjects.votingservice.infrastructure.integration.dto.UserInfoResponseDto.StatusEnum.ABLE_TO_VOTE;
import static com.subjects.votingservice.api.dto.session.VotingSessionResponseDto.Status.CLOSED;

/**
 * Implementation of vote service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class VoteServiceImpl implements VoteService {

    private final UserInfoService userInfoService;
    private final VoteRepository voteRepository;
    private final VotingSessionRepository votingSessionRepository;
    private final AssociateRepository associateRepository;

    private final VoteMapper voteMapper;
    private final VotingSessionMapper votingSessionMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaConfigurationProperties kafkaConfigurationProperties;

    /**
     * {@inheritDoc}
     */
    @Override
    public VoteResponseDto save(VoteRequestDto voteRequestDto) {
        final Associate associate = getValidatedAssociate(voteRequestDto);
        final VotingSession votingSession = getValidatedVotingSession(voteRequestDto);
        final Vote vote = new Vote(associate, votingSession, voteRequestDto.getOption());
        log.info("Saving vote from vote request data transfer object {}", voteRequestDto);
        final VoteResponseDto savedVoteResponseDto = voteMapper.voteToVoteResponseDto(voteRepository.save(vote));
        log.info("Vote {} was saved.", vote);
        return savedVoteResponseDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VotingSessionResultDto findVotingSessionResultsBySubjectCode(String subjectCode) {
        log.info("Searching voting session result by subject code {}", subjectCode);
        final VotingSession votingSession = votingSessionRepository.findOneBySubjectCode(subjectCode).orElseThrow(VotingSessionNotFoundException::new);
        final List<Vote> votes = voteRepository.findBySessionSubjectCode(subjectCode);
        final VotingSessionResultDto votingSessionResultDto = buildVotingSessionResultDto(votingSession, votes);

        if (kafkaConfigurationProperties.isEnabled()
            && !votingSession.isNotified() && CLOSED.equals(votingSessionResultDto.getSession().getStatus())) {
            publishVotingSessionResults(votingSessionResultDto);
            votingSession.setNotified(true);
            votingSessionRepository.save(votingSession);
        }

        log.info("Voting session result {} was found.", votingSessionResultDto);
        return votingSessionResultDto;
    }

    private VotingSessionResultDto buildVotingSessionResultDto(VotingSession votingSession, List<Vote> votes) {
        final Map<Boolean, Long> countingVotesMap = votes.stream()
            .map(Vote::getOption)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return VotingSessionResultDto.builder()
            .session(votingSessionMapper.votingSessionToVotingSessionResponseDto(votingSession))
            .resultDto(VotingSessionResultDto.ResultDto.builder()
                .yes(Optional.ofNullable(countingVotesMap.get(Boolean.TRUE)).orElse(0L))
                .no(Optional.ofNullable(countingVotesMap.get(Boolean.FALSE)).orElse(0L))
                .build())
            .build();
    }

    private Associate getValidatedAssociate(VoteRequestDto voteRequestDto) {

        final Associate associate = associateRepository.findOneByCpf(voteRequestDto.getCpf()).orElseThrow(AssociateNotFoundException::new);

        if (hasAssociateAlreadyVoted(voteRequestDto)) {
            log.error("Associate already voted");
            throw new AssociateAlreadyVotedException();
        }

        if (!isAssociateAbleToVote(voteRequestDto.getCpf())) {
            log.error("Associate with cpf {} is unable to vote", voteRequestDto.getCpf());
            throw new AssociateUnableToVoteException();
        }

        return associate;
    }

    private VotingSession getValidatedVotingSession(VoteRequestDto voteRequestDto) {
        final VotingSession votingSession = votingSessionRepository.findOneBySubjectCode(voteRequestDto.getSubjectCode()).orElseThrow(VotingSessionNotFoundException::new);
        if (isVotingSessionActive(votingSession.getExpirationDate())) {
            log.error("Voting session has expired.");
            throw new SessionExpiredException();
        }
        return votingSession;
    }

    private boolean isAssociateAbleToVote(String cpf) {
        try {
            final UserInfoResponseDto userInfoResponseDto = userInfoService.getUserInfo(cpf);
            return ABLE_TO_VOTE.equals(userInfoResponseDto.getStatus());
        } catch (ResourceAccessException exception) {
            log.error("Get User Info service is taking too long to respond. Enabling associate to vote.");
            return true;
        }
    }

    private boolean hasAssociateAlreadyVoted(VoteRequestDto voteRequestDto) {
        return voteRepository.existsByAssociateCpfAndSessionSubjectCode(voteRequestDto.getCpf(), voteRequestDto.getSubjectCode());
    }

    private boolean isVotingSessionActive(LocalDateTime expirationDate) {
        return expirationDate.isBefore(LocalDateTime.now());
    }

    private void publishVotingSessionResults(VotingSessionResultDto votingSessionResultDto) {
        final VotingSessionResultEvent votingSessionResultEvent = new VotingSessionResultEvent(votingSessionResultDto);
        log.info("Publishing voting session results event {}", votingSessionResultEvent);
        kafkaTemplate.send(kafkaConfigurationProperties.getTopic(), votingSessionResultEvent.toString());
    }
}
