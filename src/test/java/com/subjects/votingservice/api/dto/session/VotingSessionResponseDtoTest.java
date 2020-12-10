package com.subjects.votingservice.api.dto.session;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.VotingSessionHelper.buildVotingSessionResponseDto;

/**
 * Voting session response data transfer object test.
 */
public class VotingSessionResponseDtoTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient VotingSessionResponseDto votingSessionResponseDto;
    private transient Set<ConstraintViolation<VotingSessionResponseDto>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        votingSessionResponseDto = buildVotingSessionResponseDto();
    }

    /**
     * Voting session response data transfer object should not have null subject response data transfer object attribute.
     */
    @Test
    public void votingSessionResponseDtoShouldNotHaveNullSubjectDtoAttribute() {
        votingSessionResponseDto.setSubject(null);
        violations = validator.validate(votingSessionResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Voting session response data transfer object should not have null status attribute.
     */
    @Test
    public void votingSessionResponseDtoShouldNotHaveNullStatusAttribute() {
        votingSessionResponseDto.setStatus(null);
        violations = validator.validate(votingSessionResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }
}
