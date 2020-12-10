package com.subjects.votingservice.api.dto.session;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.VotingSessionHelper.buildVotingSessionRequestDto;

/**
 * Voting session request data transfer object test.
 */
public class VotingSessionRequestDtoTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient VotingSessionRequestDto votingSessionRequestDto;
    private transient Set<ConstraintViolation<VotingSessionRequestDto>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        votingSessionRequestDto = buildVotingSessionRequestDto();
    }

    /**
     * Voting session request data transfer object should not have blank subject code attribute.
     */
    @Test
    public void votingSessionRequestDtoShouldNotHaveBlankSubjectCodeAttribute() {
        votingSessionRequestDto.setSubjectCode("");
        violations = validator.validate(votingSessionRequestDto);
        Assert.assertFalse(violations.isEmpty());
    }
}
