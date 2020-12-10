package com.subjects.votingservice.infrastructure.integration;

import com.subjects.votingservice.configuration.properties.UserInfoServiceConfigurationProperties;
import com.subjects.votingservice.infrastructure.integration.dto.UserInfoResponseDto;
import com.subjects.votingservice.infrastructure.integration.impl.UserInfoServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.subjects.votingservice.helper.AssociateHelper.CPF;
import static com.subjects.votingservice.helper.UserInfoResponseHelper.buildUserInfoResponseDto;
import static com.subjects.votingservice.infrastructure.integration.dto.UserInfoResponseDto.StatusEnum.ABLE_TO_VOTE;

/**
 * User info service implementation test.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserInfoServiceImplTest {

    private static final String URL = "endpoint_url";

    @Mock
    private transient UserInfoServiceConfigurationProperties userInfoServiceConfigurationProperties;

    @Mock
    private transient RestTemplate restTemplate;

    @InjectMocks
    private transient UserInfoServiceImpl userInfoServiceImpl;

    /**
     * Get user info should return user info response data transfer object when user is found.
     */
    @Test
    public void getUserInfoShouldReturnUserInfoResponseDtoWhenUserIsFound() {

        final UserInfoResponseDto expectedUserInfoResponseDto = buildUserInfoResponseDto(ABLE_TO_VOTE);
        final HttpEntity httpEntity = new HttpEntity<>(createHeaders());
        final ResponseEntity<UserInfoResponseDto> response = new ResponseEntity<>(expectedUserInfoResponseDto, HttpStatus.OK);

        Mockito.when(userInfoServiceConfigurationProperties.getUrl()).thenReturn(URL);
        Mockito.when(restTemplate.exchange(
            URL.concat("/cpf"),
            HttpMethod.GET,
            httpEntity,
            UserInfoResponseDto.class)).thenReturn(response);

        final UserInfoResponseDto userInfoResponseDto = userInfoServiceImpl.getUserInfo(CPF);
        Assert.assertEquals(expectedUserInfoResponseDto, userInfoResponseDto);
    }

    private HttpHeaders createHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
