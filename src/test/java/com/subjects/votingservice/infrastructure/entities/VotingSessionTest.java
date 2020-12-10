package com.subjects.votingservice.infrastructure.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.VotingSessionHelper.buildVotingSession;

/**
 * Voting Session test.
 */
public class VotingSessionTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient VotingSession votingSession;
    private transient Set<ConstraintViolation<VotingSession>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        votingSession = buildVotingSession();
    }

    /**
     * Voting session should not have null subject attribute.
     */
    @Test
    public void votingSessionShouldNotHaveNullSubjectAttribute() {
        votingSession.setSubject(null);
        violations = validator.validate(votingSession);
        Assert.assertFalse(violations.isEmpty());
    }
}
