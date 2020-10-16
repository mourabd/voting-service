package com.subjects.votingservice.shared.dto.associate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.AssociateHelper.buildAssociateResponseDto;

/**
 * Associate response data transfer object test.
 */
public class AssociateResponseDtoTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient AssociateResponseDto associateResponseDto;
    private transient Set<ConstraintViolation<AssociateResponseDto>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        associateResponseDto = buildAssociateResponseDto();
    }

    /**
     * Associate response data transfer object should not have blank first name attribute.
     */
    @Test
    public void associateResponseDtoShouldNotHaveBlankFirstNameAttribute() {
        associateResponseDto.setFirstName(null);
        violations = validator.validate(associateResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Associate response data transfer object should not have blank last name attribute.
     */
    @Test
    public void associateResponseDtoShouldNotHaveBlankLastNameAttribute() {
        associateResponseDto.setLastName("");
        violations = validator.validate(associateResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Associate response data transfer object should not have blank cpf attribute.
     */
    @Test
    public void associateResponseDtoShouldNotHaveBlankCpfAttribute() {
        associateResponseDto.setCpf(null);
        violations = validator.validate(associateResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }
}
