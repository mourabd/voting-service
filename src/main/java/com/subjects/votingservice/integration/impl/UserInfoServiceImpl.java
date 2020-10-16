package com.subjects.votingservice.integration.impl;

import com.subjects.votingservice.configuration.properties.UserInfoServiceConfigurationProperties;
import com.subjects.votingservice.integration.UserInfoService;
import com.subjects.votingservice.integration.dto.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation class for user info service.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoServiceConfigurationProperties userInfoServiceConfigurationProperties;
    private final RestTemplate restTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserInfoResponseDto getUserInfo(String cpf) {
        final String url = userInfoServiceConfigurationProperties.getUrl().concat("/").concat(cpf);
        log.info("User info service request {}", url);
        final UserInfoResponseDto userInfoResponseDto = restTemplate.exchange(
            url, HttpMethod.GET, new HttpEntity<>(createHeaders()), UserInfoResponseDto.class).getBody();
        log.info("User info service response {}", userInfoResponseDto);
        return userInfoResponseDto;
    }

    private HttpHeaders createHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
