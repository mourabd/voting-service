package com.subjects.votingservice.domain.service;

import com.subjects.votingservice.domain.exception.InvalidDateTimeException;
import com.subjects.votingservice.domain.exception.SessionAlreadyOpenException;
import com.subjects.votingservice.domain.exception.SubjectNotFoundException;
import com.subjects.votingservice.domain.exception.VotingSessionNotFoundException;
import com.subjects.votingservice.api.mapping.VotingSessionMapper;
import com.subjects.votingservice.infrastructure.entities.Subject;
import com.subjects.votingservice.infrastructure.entities.VotingSession;
import com.subjects.votingservice.infrastructure.repository.SubjectRepository;
import com.subjects.votingservice.infrastructure.repository.VotingSessionRepository;
import com.subjects.votingservice.domain.service.impl.VotingSessionServiceImpl;
import com.subjects.votingservice.api.dto.session.VotingSessionRequestDto;
import com.subjects.votingservice.api.dto.session.VotingSessionResponseDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.subjects.votingservice.helper.SubjectHelper.CODE;
import static com.subjects.votingservice.helper.SubjectHelper.buildSubject;
import static com.subjects.votingservice.helper.VotingSessionHelper.*;

/**
 * Voting session service implementation test.
 */
@RunWith(MockitoJUnitRunner.class)
public class VotingSessionServiceImplTest {

    private transient MockMvc mockMvc;

    @Mock
    private transient VotingSessionRepository votingSessionRepository;

    @Mock
    private transient SubjectRepository subjectRepository;

    @Mock
    private transient VotingSessionMapper votingSessionMapper;

    @InjectMocks
    private transient VotingSessionServiceImpl votingSessionServiceImpl;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(votingSessionServiceImpl).build();
    }


    /**
     * Save should return voting session response data transfer object when session is saved.
     */
    @Test
    public void saveShouldReturnVotingSessionResponseDtoWhenSessionIsSaved() {
        final Subject subject = buildSubject();
        final VotingSession votingSession = buildVotingSession();
        final VotingSessionRequestDto votingSessionRequestDto = buildVotingSessionRequestDto();

        Mockito.when(votingSessionRepository.existsBySubjectCode(votingSessionRequestDto.getSubjectCode())).thenReturn(false);
        Mockito.when(votingSessionMapper.votingSessionRequestDtoToVotingSession(votingSessionRequestDto)).thenReturn(votingSession);
        Mockito.when(subjectRepository.findOneByCode(votingSessionRequestDto.getSubjectCode())).thenReturn(Optional.ofNullable(subject));
        Mockito.when(votingSessionRepository.save(votingSession)).thenReturn(votingSession);
        Mockito.when(votingSessionMapper.votingSessionToVotingSessionResponseDto(votingSession)).thenReturn(buildVotingSessionResponseDto());

        final VotingSessionResponseDto votingSessionResponseDto = votingSessionServiceImpl.save(votingSessionRequestDto);

        Assert.assertEquals(votingSessionRequestDto.getExpirationDate(), votingSessionResponseDto.getExpirationDate());
        Assert.assertEquals(votingSessionRequestDto.getSubjectCode(), votingSessionResponseDto.getSubject().getCode());
    }

    /**
     * Save should throw session already open exception when session is already open.
     */
    @Test(expected = SessionAlreadyOpenException.class)
    public void saveShouldThrowSessionAlreadyOpenExceptionWhenSessionIsOpen() {
        final VotingSessionRequestDto votingSessionRequestDto = buildVotingSessionRequestDto();
        Mockito.when(votingSessionRepository.existsBySubjectCode(votingSessionRequestDto.getSubjectCode())).thenReturn(true);
        votingSessionServiceImpl.save(votingSessionRequestDto);
    }

    /**
     * Save should throw subject not found exception when session subject is not found.
     */
    @Test(expected = SubjectNotFoundException.class)
    public void saveShouldThrowSubjectNotFoundExceptionWhenSessionSubjectIsNotFound() {
        final VotingSessionRequestDto votingSessionRequestDto = buildVotingSessionRequestDto();
        Mockito.when(votingSessionRepository.existsBySubjectCode(votingSessionRequestDto.getSubjectCode())).thenReturn(false);
        Mockito.when(votingSessionMapper.votingSessionRequestDtoToVotingSession(votingSessionRequestDto)).thenReturn(buildVotingSession());
        Mockito.when(subjectRepository.findOneByCode(votingSessionRequestDto.getSubjectCode())).thenThrow(SubjectNotFoundException.class);
        votingSessionServiceImpl.save(votingSessionRequestDto);
    }

    /**
     * Save should throw invalid date time exception when session expiration date is not valid.
     */
    @Test(expected = InvalidDateTimeException.class)
    public void saveShouldThrowInvalidDateTimeExceptionWhenSessionExpirationDateIsNotValid() {
        final VotingSessionRequestDto votingSessionRequestDto = buildVotingSessionRequestDto();
        Mockito.when(votingSessionRepository.existsBySubjectCode(votingSessionRequestDto.getSubjectCode())).thenReturn(false);
        Mockito.when(votingSessionMapper.votingSessionRequestDtoToVotingSession(votingSessionRequestDto)).thenThrow(InvalidDateTimeException.class);
        votingSessionServiceImpl.save(votingSessionRequestDto);
    }

    /**
     * Find by subject code voting session response data transfer object when voting session is found.
     */
    @Test
    public void findBySubjectCodeShouldReturnVotingSessionResponseDtoWhenVotingSessionIsFound() {
        final VotingSession votingSession = buildVotingSession();
        Mockito.when(votingSessionRepository.findOneBySubjectCode(CODE)).thenReturn(Optional.ofNullable(votingSession));
        Mockito.when(votingSessionMapper.votingSessionToVotingSessionResponseDto(votingSession)).thenReturn(buildVotingSessionResponseDto());
        final VotingSessionResponseDto votingSessionResponseDto = votingSessionServiceImpl.findBySubjectCode(CODE);

        Assert.assertEquals(EXPIRATION_DATE, votingSessionResponseDto.getExpirationDate());
        Assert.assertEquals(STATUS, votingSessionResponseDto.getStatus());
    }

    /**
     * Find by subject code should throw voting session not found exception when voting session is not found.
     */
    @Test(expected = VotingSessionNotFoundException.class)
    public void findBySubjectCodeShouldThrowVotingSessionNotFoundExceptionWhenVotingSessionIsNotFound() {
        Mockito.when(votingSessionRepository.findOneBySubjectCode(CODE)).thenThrow(VotingSessionNotFoundException.class);
        votingSessionServiceImpl.findBySubjectCode(CODE);
    }

    /**
     * Find all should return voting session response data transfer object list when any session is found.
     */
    @Test
    public void findAllShouldReturnVotingSessionResponseDtoListWhenAnySessionIsFound() {
        final List<VotingSession> votingSessions = Arrays.asList(buildVotingSession());
        Mockito.when(votingSessionRepository.findAll(Sort.by(Sort.Direction.DESC, "expirationDate"))).thenReturn(votingSessions);
        Mockito.when(votingSessionMapper.votingSessionListToVotingSessionResponseDtoList(votingSessions)).thenReturn(Arrays.asList(buildVotingSessionResponseDto()));
        List<VotingSessionResponseDto> votingSessionResponseDtoList = votingSessionServiceImpl.findAll();
        Assert.assertEquals(SESSION_LIST_ELEMENTS_NUMBER, votingSessionResponseDtoList.size());
    }
}
