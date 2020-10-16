package com.subjects.votingservice.service.impl;

import com.subjects.votingservice.exception.NotFoundException;
import com.subjects.votingservice.exception.SessionAlreadyOpenException;
import com.subjects.votingservice.mapping.VotingSessionMapper;
import com.subjects.votingservice.model.Subject;
import com.subjects.votingservice.model.VotingSession;
import com.subjects.votingservice.repository.SubjectRepository;
import com.subjects.votingservice.repository.VotingSessionRepository;
import com.subjects.votingservice.service.VotingSessionService;
import com.subjects.votingservice.shared.dto.session.VotingSessionRequestDto;
import com.subjects.votingservice.shared.dto.session.VotingSessionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of voting session service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VotingSessionServiceImpl implements VotingSessionService {

    private final VotingSessionRepository votingSessionRepository;
    private final SubjectRepository subjectRepository;
    private final VotingSessionMapper votingSessionMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public VotingSessionResponseDto save(VotingSessionRequestDto votingSessionRequestDto) {
        if (isSessionStarted(votingSessionRequestDto.getSubjectCode())) {
            log.error("Session already started");
            throw new SessionAlreadyOpenException();
        }

        log.info("Saving session from voting session request data transfer object {}", votingSessionRequestDto);
        final VotingSession votingSession = votingSessionMapper.votingSessionRequestDtoToVotingSession(votingSessionRequestDto);
        final Subject subject = subjectRepository.findOneByCode(votingSessionRequestDto.getSubjectCode()).orElseThrow(NotFoundException::new);
        votingSession.setSubject(subject);
        final VotingSessionResponseDto savedVotingSessionResponseDto = votingSessionMapper.votingSessionToVotingSessionResponseDto(votingSessionRepository.save(votingSession));
        log.info("Voting session {} was saved.", votingSession);
        return savedVotingSessionResponseDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VotingSessionResponseDto findBySubjectCode(String subjectCode) {
        log.info("Searching session by subject code {}", subjectCode);
        final VotingSession votingSession = votingSessionRepository.findOneBySubjectCode(subjectCode).orElseThrow(NotFoundException::new);
        final VotingSessionResponseDto votingSessionResponseDto = votingSessionMapper.votingSessionToVotingSessionResponseDto(votingSession);
        log.info("Session {} was found.", votingSession);
        return votingSessionResponseDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VotingSessionResponseDto> findAll() {
        log.info("Retrieving all voting sessions");
        final List<VotingSession> votingSessions = votingSessionRepository.findAll(Sort.by(Sort.Direction.DESC, "expirationDate"));
        final List<VotingSessionResponseDto> votingSessionResponseDtos = votingSessionMapper.votingSessionListToVotingSessionResponseDtoList(votingSessions);
        log.info("Number of voting sessions retrieved: {}", votingSessions.size());
        return votingSessionResponseDtos;
    }

    private boolean isSessionStarted(String subjectCode) {
        return votingSessionRepository.existsBySubjectCode(subjectCode);
    }
}
