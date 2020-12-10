package com.subjects.votingservice.api.dto.associate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.AssociateHelper.buildAssociateRequestDto;

/**
 * Associate request data transfer object test.
 */
public class AssociateRequestDtoTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient AssociateRequestDto associateRequestDto;
    private transient Set<ConstraintViolation<AssociateRequestDto>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        associateRequestDto = buildAssociateRequestDto();
    }

    /**
     * Associate request data transfer object should not have blank first name attribute.
     */
    @Test
    public void associateRequestDtoShouldNotHaveBlankFirstNameAttribute() {
        associateRequestDto.setFirstName(null);
        violations = validator.validate(associateRequestDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Associate request data transfer object should not have blank last name attribute.
     */
    @Test
    public void associateRequestDtoShouldNotHaveBlankLastNameAttribute() {
        associateRequestDto.setLastName("");
        violations = validator.validate(associateRequestDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Associate request data transfer object should not have blank cpf attribute.
     */
    @Test
    public void associateRequestDtoShouldNotHaveBlankCpfAttribute() {
        associateRequestDto.setCpf(null);
        violations = validator.validate(associateRequestDto);
        Assert.assertFalse(violations.isEmpty());
    }
}
