package com.subjects.votingservice.infrastructure.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.AssociateHelper.buildAssociate;

/**
 * Associate test.
 */
public class AssociateTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient Associate associate;
    private transient Set<ConstraintViolation<Associate>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        associate = buildAssociate();
    }

    /**
     * Associate should not have blank first name attribute.
     */
    @Test
    public void associateShouldNotHaveBlankFirstNameAttribute() {
        associate.setFirstName(null);
        violations = validator.validate(associate);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Associate should not have blank last name attribute.
     */
    @Test
    public void associateShouldNotHaveBlankLastNameAttribute() {
        associate.setLastName("");
        violations = validator.validate(associate);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Associate should not have blank cpf attribute.
     */
    @Test
    public void associateShouldNotHaveBlankCpfAttribute() {
        associate.setCpf(null);
        violations = validator.validate(associate);
        Assert.assertFalse(violations.isEmpty());
    }
}
