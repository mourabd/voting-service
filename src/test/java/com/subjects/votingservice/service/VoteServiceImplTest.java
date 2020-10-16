package com.subjects.votingservice.service;

import com.subjects.votingservice.configuration.properties.KafkaConfigurationProperties;
import com.subjects.votingservice.exception.AssociateAlreadyVotedException;
import com.subjects.votingservice.exception.AssociateUnableToVoteException;
import com.subjects.votingservice.exception.NotFoundException;
import com.subjects.votingservice.exception.SessionExpiredException;
import com.subjects.votingservice.integration.UserInfoService;
import com.subjects.votingservice.mapping.VoteMapper;
import com.subjects.votingservice.mapping.VotingSessionMapper;
import com.subjects.votingservice.model.Vote;
import com.subjects.votingservice.model.VotingSession;
import com.subjects.votingservice.repository.AssociateRepository;
import com.subjects.votingservice.repository.VoteRepository;
import com.subjects.votingservice.repository.VotingSessionRepository;
import com.subjects.votingservice.service.impl.VoteServiceImpl;
import com.subjects.votingservice.shared.dto.session.VotingSessionResponseDto;
import com.subjects.votingservice.shared.dto.session.VotingSessionResultDto;
import com.subjects.votingservice.shared.dto.vote.VoteRequestDto;
import com.subjects.votingservice.shared.dto.vote.VoteResponseDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static com.subjects.votingservice.helper.AssociateHelper.CPF;
import static com.subjects.votingservice.helper.AssociateHelper.buildAssociate;
import static com.subjects.votingservice.helper.SubjectHelper.CODE;
import static com.subjects.votingservice.helper.UserInfoResponseHelper.buildUserInfoResponseDto;
import static com.subjects.votingservice.helper.VoteHelper.*;
import static com.subjects.votingservice.helper.VotingSessionHelper.buildVotingSession;
import static com.subjects.votingservice.helper.VotingSessionHelper.buildVotingSessionResponseDto;
import static com.subjects.votingservice.integration.dto.UserInfoResponseDto.StatusEnum.ABLE_TO_VOTE;
import static com.subjects.votingservice.integration.dto.UserInfoResponseDto.StatusEnum.UNABLE_TO_VOTE;

/**
 * Vote service implementation test.
 */
@RunWith(MockitoJUnitRunner.class)
public class VoteServiceImplTest {

    private static final int MINUTES = 30;

    private transient MockMvc mockMvc;

    @Mock
    private transient UserInfoService userInfoService;

    @Mock
    private transient VoteRepository voteRepository;

    @Mock
    private transient VotingSessionRepository votingSessionRepository;

    @Mock
    private transient AssociateRepository associateRepository;

    @Mock
    private transient VoteMapper voteMapper;

    @Mock
    private transient VotingSessionMapper votingSessionMapper;

    @Mock
    private transient KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private transient KafkaConfigurationProperties kafkaConfigurationProperties;

    @InjectMocks
    private transient VoteServiceImpl voteServiceImpl;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(voteServiceImpl).build();
    }

    /**
     * Save should throw associate unable to vote exception when user is not able to vote.
     */
    @Test(expected = AssociateUnableToVoteException.class)
    public void saveShouldThrowAssociateUnableToVoteExceptionWhenUserIsNotAbleToVote() {
        final VoteRequestDto voteRequestDto = buildVoteRequestDto();
        Mockito.when(userInfoService.getUserInfo(CPF)).thenReturn(buildUserInfoResponseDto(UNABLE_TO_VOTE));
        voteServiceImpl.save(voteRequestDto);

    }

    /**
     * Save should throw associate already voted exception when associate has voted already.
     */
    @Test(expected = AssociateAlreadyVotedException.class)
    public void saveShouldThrowAssociateAlreadyVotedExceptionWhenAssociateHasVotedAlready() {
        final VoteRequestDto voteRequestDto = buildVoteRequestDto();
        Mockito.when(userInfoService.getUserInfo(CPF)).thenReturn(buildUserInfoResponseDto(ABLE_TO_VOTE));
        Mockito.when(voteRepository.existsByAssociateCpfAndSessionSubjectCode(voteRequestDto.getCpf(), voteRequestDto.getSubjectCode())).thenReturn(true);
        voteServiceImpl.save(voteRequestDto);
    }

    /**
     * Save should throw not found exception when voting session subject is not found.
     */
    @Test(expected = NotFoundException.class)
    public void saveShouldThrowNotFoundExceptionWhenVotingSessionSubjectIsNotFound() {
        final VoteRequestDto voteRequestDto = buildVoteRequestDto();
        Mockito.when(userInfoService.getUserInfo(CPF)).thenReturn(buildUserInfoResponseDto(ABLE_TO_VOTE));
        Mockito.when(voteRepository.existsByAssociateCpfAndSessionSubjectCode(voteRequestDto.getCpf(), voteRequestDto.getSubjectCode())).thenReturn(false);
        Mockito.when(votingSessionRepository.findOneBySubjectCode(voteRequestDto.getSubjectCode())).thenThrow(NotFoundException.class);
        voteServiceImpl.save(voteRequestDto);
    }

    /**
     * Save should throw session expired exception when session has expired.
     */
    @Test(expected = SessionExpiredException.class)
    public void saveShouldThrowSessionExpiredExceptionWhenVotingSessionHasExpired() {
        final VoteRequestDto voteRequestDto = buildVoteRequestDto();
        Mockito.when(userInfoService.getUserInfo(CPF)).thenReturn(buildUserInfoResponseDto(ABLE_TO_VOTE));
        Mockito.when(voteRepository.existsByAssociateCpfAndSessionSubjectCode(voteRequestDto.getCpf(), voteRequestDto.getSubjectCode())).thenReturn(false);
        Mockito.when(votingSessionRepository.findOneBySubjectCode(voteRequestDto.getSubjectCode())).thenReturn(Optional.ofNullable(buildVotingSession()));
        voteServiceImpl.save(voteRequestDto);
    }

    /**
     * Save should throw not found exception when associate is not found.
     */
    @Test(expected = NotFoundException.class)
    public void saveShouldThrowNotFoundExceptionWhenAssociateIsNotFound() {
        final VoteRequestDto voteRequestDto = buildVoteRequestDto();
        final VotingSession votingSession = buildVotingSession();
        votingSession.setExpirationDate(LocalDateTime.now().plusMinutes(MINUTES));
        Mockito.when(userInfoService.getUserInfo(CPF)).thenReturn(buildUserInfoResponseDto(ABLE_TO_VOTE));
        Mockito.when(voteRepository.existsByAssociateCpfAndSessionSubjectCode(voteRequestDto.getCpf(), voteRequestDto.getSubjectCode())).thenReturn(false);
        Mockito.when(votingSessionRepository.findOneBySubjectCode(voteRequestDto.getSubjectCode())).thenReturn(Optional.ofNullable(votingSession));
        Mockito.when(associateRepository.findOneByCpf(voteRequestDto.getCpf())).thenThrow(NotFoundException.class);
        voteServiceImpl.save(voteRequestDto);
    }

    /**
     * Save should return vote response data transfer object when vote is saved.
     */
    @Test
    public void saveShouldReturnVoteResponseDtoWhenVoteIsSaved() {
        final VoteRequestDto voteRequestDto = buildVoteRequestDto();
        final VotingSession votingSession = buildVotingSession();
        final Vote vote = buildVote();

        votingSession.setExpirationDate(LocalDateTime.now().plusMinutes(MINUTES));
        Mockito.when(userInfoService.getUserInfo(CPF)).thenReturn(buildUserInfoResponseDto(ABLE_TO_VOTE));
        Mockito.when(voteRepository.existsByAssociateCpfAndSessionSubjectCode(voteRequestDto.getCpf(), voteRequestDto.getSubjectCode())).thenReturn(false);
        Mockito.when(voteMapper.voteRequestDtoToVote(voteRequestDto)).thenReturn(vote);
        Mockito.when(votingSessionRepository.findOneBySubjectCode(voteRequestDto.getSubjectCode())).thenReturn(Optional.ofNullable(votingSession));
        Mockito.when(associateRepository.findOneByCpf(voteRequestDto.getCpf())).thenReturn(Optional.ofNullable(buildAssociate()));
        Mockito.when(voteRepository.save(vote)).thenReturn(vote);
        Mockito.when(voteMapper.voteToVoteResponseDto(vote)).thenReturn(buildVoteResponseDto());

        final VoteResponseDto voteResponseDto = voteServiceImpl.save(voteRequestDto);

        Assert.assertEquals(voteRequestDto.getCpf(), voteResponseDto.getAssociate().getCpf());
        Assert.assertEquals(voteRequestDto.getSubjectCode(), voteResponseDto.getSession().getSubject().getCode());
    }

    /**
     * Find voting session results by subject code should throw not found exception when voting session is not found.
     */
    @Test(expected = NotFoundException.class)
    public void findVotingSessionResultsBySubjectCodeShouldThrowNotFoundExceptionVotingSessionIsNotFound() {
        Mockito.when(votingSessionRepository.findOneBySubjectCode(CODE)).thenThrow(NotFoundException.class);
        voteServiceImpl.findVotingSessionResultsBySubjectCode(CODE);
    }

    /**
     * Find voting session results by subject code should return voting session result data transfer object when voting session is found.
     */
    @Test
    public void findVotingSessionResultsBySubjectCodeShouldReturnVotingSessionResultDtoWhenVotingSessionIsFound() {
        final VotingSession votingSession = buildVotingSession();
        Mockito.when(votingSessionRepository.findOneBySubjectCode(CODE)).thenReturn(Optional.ofNullable(votingSession));
        Mockito.when(voteRepository.findBySessionSubjectCode(CODE)).thenReturn(Arrays.asList(buildVote()));
        Mockito.when(votingSessionMapper.votingSessionToVotingSessionResponseDto(votingSession)).thenReturn(buildVotingSessionResponseDto());
        final VotingSessionResultDto votingSessionResultDto = voteServiceImpl.findVotingSessionResultsBySubjectCode(CODE);
        Assert.assertNotNull(votingSessionResultDto);
    }

    /**
     * Find voting session results by subject code should return voting session result data transfer object when voting session is found and publish kafka event.
     */
    @Test
    public void findVotingSessionResultsBySubjectCodeShouldReturnVotingSessionResultDtoWhenVotingSessionIsFoundAndPublishKafkaEvent() {
        final VotingSession votingSession = buildVotingSession();
        final VotingSessionResponseDto votingSessionResponseDto = buildVotingSessionResponseDto();
        Mockito.when(votingSessionRepository.findOneBySubjectCode(CODE)).thenReturn(Optional.ofNullable(votingSession));
        Mockito.when(voteRepository.findBySessionSubjectCode(CODE)).thenReturn(Arrays.asList(buildVote()));
        votingSessionResponseDto.setStatus(VotingSessionResponseDto.Status.CLOSED);
        Mockito.when(votingSessionMapper.votingSessionToVotingSessionResponseDto(votingSession)).thenReturn(votingSessionResponseDto);
        Mockito.when(kafkaConfigurationProperties.isEnabled()).thenReturn(true);
        final VotingSessionResultDto votingSessionResultDto = voteServiceImpl.findVotingSessionResultsBySubjectCode(CODE);
        Assert.assertNotNull(votingSessionResultDto);
    }
}
