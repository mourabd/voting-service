package com.subjects.votingservice.configuration.handler;

import com.subjects.votingservice.domain.exception.AssociateUnableToVoteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;

/**
 * Handler for rest template errors.
 */
@Slf4j
@Component
public final class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series() == CLIENT_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            log.error("User info was not found or CPF is invalid.");
            throw new AssociateUnableToVoteException();
        }
    }
}
