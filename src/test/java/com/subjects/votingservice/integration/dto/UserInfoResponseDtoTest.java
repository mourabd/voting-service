package com.subjects.votingservice.integration.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.subjects.votingservice.helper.UserInfoResponseHelper.buildUserInfoResponseDto;
import static com.subjects.votingservice.integration.dto.UserInfoResponseDto.StatusEnum.ABLE_TO_VOTE;

/**
 * User info response data transfer object test.
 */
public class UserInfoResponseDtoTest {

    private final transient ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final transient Validator validator = validatorFactory.getValidator();

    private transient UserInfoResponseDto userInfoResponseDto;
    private transient Set<ConstraintViolation<UserInfoResponseDto>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        userInfoResponseDto = buildUserInfoResponseDto(ABLE_TO_VOTE);
    }

    /**
     * User info response data transfer object should not have null status attribute.
     */
    @Test
    public void userInfoResponseDtoShouldNotHaveNullStatusAttribute() {
        userInfoResponseDto.setStatus(null);
        violations = validator.validate(userInfoResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }
}
