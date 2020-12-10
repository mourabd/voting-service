package com.subjects.votingservice.domain.exception;

/**
 * Not found exception.
 */
public class VotingSessionNotFoundException extends NotFoundException {

    static final long serialVersionUID = -7034897190745766939L;

    /**
     * Class constructor.
     */
    public VotingSessionNotFoundException() {
        super("Voting session not found");
    }
}
