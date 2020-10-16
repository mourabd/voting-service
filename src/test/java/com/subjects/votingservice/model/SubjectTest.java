package com.subjects.votingservice.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.SubjectHelper.buildSubject;

/**
 * Subject test.
 */
public class SubjectTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient Subject subject;
    private transient Set<ConstraintViolation<Subject>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        subject = buildSubject();
    }

    /**
     * Subject should not have blank title attribute.
     */
    @Test
    public void subjectShouldNotHaveBlankTitleAttribute() {
        subject.setTitle("");
        violations = validator.validate(subject);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Subject should not have blank description attribute.
     */
    @Test
    public void subjectShouldNotHaveBlankDescriptionAttribute() {
        subject.setDescription(null);
        violations = validator.validate(subject);
        Assert.assertFalse(violations.isEmpty());
    }
}
