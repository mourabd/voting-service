package com.subjects.votingservice.exception;

/**
 * Not found exception.
 */
public class AssociateNotFoundException extends NotFoundException {

    static final long serialVersionUID = -7034897190745766939L;

    /**
     * Class constructor.
     */
    public AssociateNotFoundException() {
        super("Associate not found");
    }
}
