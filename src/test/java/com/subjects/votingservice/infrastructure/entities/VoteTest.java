package com.subjects.votingservice.infrastructure.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.VoteHelper.buildVote;

/**
 * Vote test.
 */
public class VoteTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient Vote vote;
    private transient Set<ConstraintViolation<Vote>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        vote = buildVote();
    }

    /**
     * Vote should not have null associate attribute.
     */
    @Test
    public void voteShouldNotHaveNullAssociateAttribute() {
        vote.setAssociate(null);
        violations = validator.validate(vote);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Vote should not have null session attribute.
     */
    @Test
    public void voteShouldNotHaveNullSessionAttribute() {
        vote.setSession(null);
        violations = validator.validate(vote);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Vote should not have null option attribute.
     */
    @Test
    public void voteShouldNotHaveNullOptionAttribute() {
        vote.setOption(null);
        violations = validator.validate(vote);
        Assert.assertFalse(violations.isEmpty());
    }
}
