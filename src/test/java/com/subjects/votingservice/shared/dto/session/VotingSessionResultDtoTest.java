package com.subjects.votingservice.shared.dto.session;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.VotingSessionHelper.buildVotingSessionResultDto;

/**
 * Voting session result data transfer object test.
 */
public class VotingSessionResultDtoTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient VotingSessionResultDto votingSessionResultDto;
    private transient Set<ConstraintViolation<VotingSessionResultDto>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        votingSessionResultDto = buildVotingSessionResultDto();
    }

    /**
     * Voting session result data transfer object should not have null voting session response data transfer object attribute.
     */
    @Test
    public void votingSessionResultDtoShouldNotHaveNullSessionAttribute() {
        votingSessionResultDto.setSession(null);
        violations = validator.validate(votingSessionResultDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Voting session result data transfer object should not have null result data transfer object attribute.
     */
    @Test
    public void votingSessionResultDtoShouldNotHaveNullResultAttribute() {
        votingSessionResultDto.setResultDto(null);
        violations = validator.validate(votingSessionResultDto);
        Assert.assertFalse(violations.isEmpty());
    }
}
