package com.subjects.votingservice.infrastructure.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.subjects.votingservice.helper.VotingSessionHelper.buildVotingSessionResultDto;

/**
 * Voting session result event test.
 */
public class VotingSessionResultEventTest {

    private transient VotingSessionResultEvent votingSessionResultEvent;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        votingSessionResultEvent = buildVotingSessionResultEvent();
    }

    /**
     * Voting session result event should not have blank version attribute.
     */
    @Test
    public void votingSessionResultEventShouldNotHaveBlankVersionAttribute() {
        Assert.assertNotNull(votingSessionResultEvent.getVersion());
    }

    private VotingSessionResultEvent buildVotingSessionResultEvent() {
        return new VotingSessionResultEvent(buildVotingSessionResultDto());
    }
}
