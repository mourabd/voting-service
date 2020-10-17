package com.subjects.votingservice.exception;

/**
 * Not found exception.
 */
public class SubjectNotFoundException extends NotFoundException {

    static final long serialVersionUID = -7034897190745766939L;

    /**
     * Class constructor.
     */
    public SubjectNotFoundException() {
        super("Subject not found");
    }
}
