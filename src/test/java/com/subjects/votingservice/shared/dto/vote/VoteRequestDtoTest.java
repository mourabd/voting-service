package com.subjects.votingservice.shared.dto.vote;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.VoteHelper.buildVoteRequestDto;

/**
 * Vote request data transfer object test.
 */
public class VoteRequestDtoTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient VoteRequestDto voteRequestDto;
    private transient Set<ConstraintViolation<VoteRequestDto>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        voteRequestDto = buildVoteRequestDto();
    }

    /**
     * Vote request data transfer object should not blank cpf attribute.
     */
    @Test
    public void voteRequestDtoShouldNotHaveBlankCpfAttribute() {
        voteRequestDto.setCpf("");
        violations = validator.validate(voteRequestDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Vote request data transfer object should not blank subject code attribute.
     */
    @Test
    public void voteRequestDtoShouldNotHaveBlankSubjectCodeAttribute() {
        voteRequestDto.setSubjectCode(" ");
        violations = validator.validate(voteRequestDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Vote request data transfer object should not have null option attribute.
     */
    @Test
    public void voteRequestDtoShouldNotHaveNullOptionAttribute() {
        voteRequestDto.setOption(null);
        violations = validator.validate(voteRequestDto);
        Assert.assertFalse(violations.isEmpty());
    }
}
