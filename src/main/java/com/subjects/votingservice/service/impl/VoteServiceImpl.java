package com.subjects.votingservice.service.impl;

import com.subjects.votingservice.configuration.properties.KafkaConfigurationProperties;
import com.subjects.votingservice.exception.AssociateAlreadyVotedException;
import com.subjects.votingservice.exception.AssociateUnableToVoteException;
import com.subjects.votingservice.exception.NotFoundException;
import com.subjects.votingservice.exception.SessionExpiredException;
import com.subjects.votingservice.integration.UserInfoService;
import com.subjects.votingservice.integration.dto.UserInfoResponseDto;
import com.subjects.votingservice.mapping.VoteMapper;
import com.subjects.votingservice.mapping.VotingSessionMapper;
import com.subjects.votingservice.model.Associate;
import com.subjects.votingservice.model.Vote;
import com.subjects.votingservice.model.VotingSession;
import com.subjects.votingservice.repository.AssociateRepository;
import com.subjects.votingservice.repository.VoteRepository;
import com.subjects.votingservice.repository.VotingSessionRepository;
import com.subjects.votingservice.service.VoteService;
import com.subjects.votingservice.shared.dto.session.VotingSessionResultDto;
import com.subjects.votingservice.shared.dto.vote.VoteRequestDto;
import com.subjects.votingservice.shared.dto.vote.VoteResponseDto;
import com.subjects.votingservice.shared.event.VotingSessionResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.subjects.votingservice.integration.dto.UserInfoResponseDto.StatusEnum.ABLE_TO_VOTE;
import static com.subjects.votingservice.shared.dto.session.VotingSessionResponseDto.Status.CLOSED;

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
        final Vote vote = buildVote(voteRequestDto);
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
        final VotingSession votingSession = votingSessionRepository.findOneBySubjectCode(subjectCode).orElseThrow(NotFoundException::new);
        final List<Vote> votes = voteRepository.findBySessionSubjectCode(subjectCode);
        final Map<Boolean, Long> countingVotesMap = votes.stream()
            .map(Vote::getOption)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        final VotingSessionResultDto votingSessionResultDto = VotingSessionResultDto.builder()
            .session(votingSessionMapper.votingSessionToVotingSessionResponseDto(votingSession))
            .resultDto(VotingSessionResultDto.ResultDto.builder()
                .yes(Optional.ofNullable(countingVotesMap.get(Boolean.TRUE)).orElse(0L))
                .no(Optional.ofNullable(countingVotesMap.get(Boolean.FALSE)).orElse(0L))
                .build())
            .build();

        if (kafkaConfigurationProperties.isEnabled()
            && !votingSession.isNotified() && CLOSED.equals(votingSessionResultDto.getSession().getStatus())) {
            publishVotingSessionResults(votingSessionResultDto);
            votingSession.setNotified(true);
            votingSessionRepository.save(votingSession);
        }

        log.info("Voting session result {} was found.", votingSessionResultDto);
        return votingSessionResultDto;
    }

    private Vote buildVote(VoteRequestDto voteRequestDto) {
        validateAssociateVote(voteRequestDto);
        log.info("Building vote entity from vote request data transfer object {}", voteRequestDto);
        final Vote vote = voteMapper.voteRequestDtoToVote(voteRequestDto);
        final VotingSession votingSession = votingSessionRepository.findOneBySubjectCode(voteRequestDto.getSubjectCode()).orElseThrow(NotFoundException::new);
        if (isVotingSessionActive(votingSession.getExpirationDate())) {
            log.error("Voting session has expired.");
            throw new SessionExpiredException();
        }
        final Associate associate = associateRepository.findOneByCpf(voteRequestDto.getCpf()).orElseThrow(NotFoundException::new);
        vote.setAssociate(associate);
        vote.setSession(votingSession);
        log.info("Entity vote was built {}", vote);
        return vote;
    }

    private void validateAssociateVote(VoteRequestDto voteRequestDto) {
        if (!isAssociateAbleToVote(voteRequestDto.getCpf())) {
            log.error("Associate with cpf {} is unable to vote", voteRequestDto.getCpf());
            throw new AssociateUnableToVoteException();
        }

        if (hasAssociateAlreadyVoted(voteRequestDto)) {
            log.error("Associate already voted");
            throw new AssociateAlreadyVotedException();
        }
    }

    private boolean isAssociateAbleToVote(String cpf) {
        final UserInfoResponseDto userInfoResponseDto = userInfoService.getUserInfo(cpf);
        return ABLE_TO_VOTE.equals(userInfoResponseDto.getStatus());
    }

    private boolean hasAssociateAlreadyVoted(VoteRequestDto voteRequestDto) {
        final String cpf = voteRequestDto.getCpf();
        final String subjectCode = voteRequestDto.getSubjectCode();
        return voteRepository.existsByAssociateCpfAndSessionSubjectCode(cpf, subjectCode);
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
