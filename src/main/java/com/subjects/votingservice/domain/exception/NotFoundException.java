package com.subjects.votingservice.domain.exception;

/**
 * Not found exception.
 */
public abstract class NotFoundException extends RuntimeException {

    static final long serialVersionUID = -7034897190745766939L;

    /**
     * Class constructor.
     *
     * @param message error message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
