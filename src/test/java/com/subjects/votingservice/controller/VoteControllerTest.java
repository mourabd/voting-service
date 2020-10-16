package com.subjects.votingservice.controller;

import com.subjects.votingservice.service.VoteService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.subjects.votingservice.helper.VoteHelper.buildVoteRequestDto;
import static com.subjects.votingservice.helper.VoteHelper.buildVoteResponseDto;

/**
 * Vote controller test.
 */
@RunWith(MockitoJUnitRunner.class)
public class VoteControllerTest {

    private transient MockMvc mockMvc;

    @Mock
    private transient VoteService voteService;

    @InjectMocks
    private transient VoteController voteController;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(voteController).build();
    }

    /**
     * Save vote should return ok status when vote is saved.
     */
    @Test
    public void saveVoteShouldReturnOkStatusWhenVoteIsSaved() {
        final VoteRequestDto voteRequestDto = buildVoteRequestDto();
        Mockito.when(voteService.save(voteRequestDto)).thenReturn(buildVoteResponseDto());
        final VoteResponseDto voteResponseDto = voteController.saveVote(voteRequestDto);
        Assert.assertNotNull(voteResponseDto);
    }
}
