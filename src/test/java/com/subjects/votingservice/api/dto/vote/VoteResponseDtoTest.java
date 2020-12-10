package com.subjects.votingservice.api.dto.vote;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.VoteHelper.buildVoteResponseDto;

/**
 * Vote response data transfer object test.
 */
public class VoteResponseDtoTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient VoteResponseDto voteResponseDto;
    private transient Set<ConstraintViolation<VoteResponseDto>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        voteResponseDto = buildVoteResponseDto();
    }

    /**
     * Vote response data transfer object should not have null associate response data transfer object attribute.
     */
    @Test
    public void voteResponseDtoShouldNotHaveNullAssociateResponseDtoAttribute() {
        voteResponseDto.setAssociate(null);
        violations = validator.validate(voteResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Vote response data transfer object should not have null voting session response data transfer object attribute.
     */
    @Test
    public void voteResponseDtoShouldNotHaveNullVotingSessionResponseDtoAttribute() {
        voteResponseDto.setSession(null);
        violations = validator.validate(voteResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }
}
