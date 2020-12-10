package com.subjects.votingservice.api.dto.subject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.SubjectHelper.buildSubjectDto;

/**
 * Subject data transfer object test.
 */
public class SubjectDtoTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient SubjectDto subjectDto;
    private transient Set<ConstraintViolation<SubjectDto>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        subjectDto = buildSubjectDto();
    }

    /**
     * Subject data transfer object should not have blank title attribute.
     */
    @Test
    public void subjectDtoShouldNotHaveBlankTitleAttribute() {
        subjectDto.setTitle("");
        violations = validator.validate(subjectDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Subject data transfer object should not have blank description attribute.
     */
    @Test
    public void subjectDtoShouldNotHaveBlankDescriptionAttribute() {
        subjectDto.setDescription(null);
        violations = validator.validate(subjectDto);
        Assert.assertFalse(violations.isEmpty());
    }
}
