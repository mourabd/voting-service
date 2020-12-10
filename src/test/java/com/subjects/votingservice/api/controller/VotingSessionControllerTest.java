package com.subjects.votingservice.api.controller;

import com.subjects.votingservice.domain.service.VoteService;
import com.subjects.votingservice.domain.service.VotingSessionService;
import com.subjects.votingservice.api.dto.session.VotingSessionRequestDto;
import com.subjects.votingservice.api.dto.session.VotingSessionResponseDto;
import com.subjects.votingservice.api.dto.session.VotingSessionResultDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.subjects.votingservice.helper.SubjectHelper.CODE;
import static com.subjects.votingservice.helper.VotingSessionHelper.*;

/**
 * Voting session controller test.
 */
@RunWith(MockitoJUnitRunner.class)
public class VotingSessionControllerTest {

    private transient MockMvc mockMvc;

    @Mock
    private transient VotingSessionService votingSessionService;

    @Mock
    private transient VoteService voteService;

    @InjectMocks
    private transient VotingSessionController votingSessionController;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(votingSessionController).build();
    }

    /**
     * Find by subject code should return ok status when voting session is found.
     */
    @Test
    public void findBySubjectCodeShouldReturnOkStatusWhenVotingSessionIsFound() {
        Mockito.when(votingSessionService.findBySubjectCode(CODE)).thenReturn(buildVotingSessionResponseDto());
        final VotingSessionResponseDto votingSessionResponseDto = votingSessionController.findBySubjectCode(CODE);

        Assert.assertEquals(STATUS, votingSessionResponseDto.getStatus());
        Assert.assertEquals(EXPIRATION_DATE, votingSessionResponseDto.getExpirationDate());
    }

    /**
     * Find all should return ok status when voting session list is returned.
     */
    @Test
    public void findAllShouldReturnOkStatusWhenVotingSessionListIsReturned() {
        Mockito.when(votingSessionService.findAll()).thenReturn(Arrays.asList(buildVotingSessionResponseDto()));
        final List<VotingSessionResponseDto> votingSessionResponseDtoList = votingSessionController.findAll();
        Assert.assertEquals(SESSION_LIST_ELEMENTS_NUMBER, votingSessionResponseDtoList.size());
    }

    /**
     * Save session should return ok status when voting session is saved.
     */
    @Test
    public void saveSessionShouldReturnOkStatusWhenVotingSessionIsSaved() {
        final VotingSessionRequestDto votingSessionRequestDto = buildVotingSessionRequestDto();
        Mockito.when(votingSessionService.save(votingSessionRequestDto)).thenReturn(buildVotingSessionResponseDto());
        final VotingSessionResponseDto votingSessionResponseDto = votingSessionController.saveSession(votingSessionRequestDto);
        Assert.assertEquals(votingSessionRequestDto.getExpirationDate(), votingSessionResponseDto.getExpirationDate());
    }

    /**
     * Find voting session results should return ok status when voting session result is found.
     */
    @Test
    public void findVotingSessionResultsShouldReturnOkStatusWhenVotingSessionResultIsFound() {
        Mockito.when(voteService.findVotingSessionResultsBySubjectCode(CODE)).thenReturn(buildVotingSessionResultDto());
        final VotingSessionResultDto votingSessionResultDto = votingSessionController.findVotingSessionResults(CODE);
        Assert.assertNotNull(votingSessionResultDto);
    }
}
