package com.subjects.votingservice.configuration.handler;

import com.subjects.votingservice.exception.AssociateAlreadyRegisteredException;
import com.subjects.votingservice.exception.AssociateAlreadyVotedException;
import com.subjects.votingservice.exception.AssociateUnableToVoteException;
import com.subjects.votingservice.exception.InvalidDateTimeException;
import com.subjects.votingservice.exception.NotFoundException;
import com.subjects.votingservice.exception.SessionAlreadyOpenException;
import com.subjects.votingservice.exception.SessionExpiredException;
import com.subjects.votingservice.shared.dto.RestErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String STANDARD_LOG_ERROR = "HTTP Status: {} - Error Message: {}";

    /**
     * Handles method argument not valid exception.
     *
     * @param exception method argument not valid exception
     * @param headers   http headers
     * @param status    http status
     * @param request   web request
     * @return response entity containing error response
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        final String invalidRequest = "Invalid Request";
        final List<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
            .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
        log.error(STANDARD_LOG_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, invalidRequest + " " + errors);
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(HttpStatus.BAD_REQUEST, invalidRequest, errors);
        return handleExceptionInternal(exception, restErrorResponseDto, headers, restErrorResponseDto.getStatus(), request);
    }

    /**
     * Handles http media type not supported exception.
     *
     * @param exception http media type not supported exception
     * @param headers   http headers
     * @param status    http status
     * @param request   web request
     * @return response entity containing error response
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {

        final String message = "Supported content types: ".concat(MediaType.toString(exception.getSupportedMediaTypes()));
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(HttpStatus.BAD_REQUEST, message);
        log.error(STANDARD_LOG_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, message);
        return handleExceptionInternal(exception, restErrorResponseDto, headers, restErrorResponseDto.getStatus(), request);
    }

    /**
     * Handles http request method not supported exception.
     *
     * @param exception http request method not supported exception
     * @param headers   http headers
     * @param status    http status
     * @param request   web request
     * @return response entity containing error response
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        final StringBuilder builder = new StringBuilder()
            .append(exception.getMethod())
            .append(" method is not supported for this request.")
            .append(" Supported methods are ");
        exception.getSupportedHttpMethods().forEach(allowedMethod -> builder.append(allowedMethod + " "));
        final String errorMessage = builder.toString();
        log.error(STANDARD_LOG_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(HttpStatus.METHOD_NOT_ALLOWED, errorMessage);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }

    /**
     * Handles associate already registered exception.
     *
     * @param exception associate already registered exception
     * @return response entity containing error response
     */
    @ExceptionHandler(AssociateAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleAssociateAlreadyRegistered(Exception exception) {
        final String associateAlreadyRegistered = "Associate already registered";
        log.error(STANDARD_LOG_ERROR, HttpStatus.BAD_REQUEST, associateAlreadyRegistered);
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(HttpStatus.BAD_REQUEST, associateAlreadyRegistered);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }

    /**
     * Handles associate already voted exception.
     *
     * @param exception associate already voted exception
     * @return response entity containing error response
     */
    @ExceptionHandler(AssociateAlreadyVotedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleAssociateAlreadyVoted(Exception exception) {
        final String associateAlreadyVoted = "Associate already voted";
        log.error(STANDARD_LOG_ERROR, HttpStatus.BAD_REQUEST, associateAlreadyVoted);
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(HttpStatus.BAD_REQUEST, associateAlreadyVoted);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }

    /**
     * Handles associate unable to vote exception.
     *
     * @param exception associate unable to vote exception
     * @return response entity containing error response
     */
    @ExceptionHandler(AssociateUnableToVoteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleAssociateUnableToVote(Exception exception) {
        final String associateUnableToVote = "Associate unable to vote";
        log.error(STANDARD_LOG_ERROR, HttpStatus.BAD_REQUEST, associateUnableToVote);
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(HttpStatus.BAD_REQUEST, associateUnableToVote);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }

    /**
     * Handles invalid date time exception.
     *
     * @param exception invalid date time exception
     * @return response entity containing error response
     */
    @ExceptionHandler(InvalidDateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleInvalidDateTime(Exception exception) {
        final String invalidDateTime = "Invalid date time";
        log.error(STANDARD_LOG_ERROR, HttpStatus.BAD_REQUEST, invalidDateTime);
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(HttpStatus.BAD_REQUEST, invalidDateTime);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }

    /**
     * Handles session already open exception.
     *
     * @param exception session already open exception
     * @return response entity containing error response
     */
    @ExceptionHandler(SessionAlreadyOpenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleSessionAlreadyOpen(Exception exception) {
        final String sessionAlreadyOpen = "Session already open";
        log.error(STANDARD_LOG_ERROR, HttpStatus.BAD_REQUEST, sessionAlreadyOpen);
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(HttpStatus.BAD_REQUEST, sessionAlreadyOpen);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }

    /**
     * Handles session expired exception.
     *
     * @param exception session expired exception
     * @return response entity containing error response
     */
    @ExceptionHandler(SessionExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleSessionExpired(Exception exception) {
        final String sessionExpired = "Voting session is expired";
        log.error(STANDARD_LOG_ERROR, HttpStatus.BAD_REQUEST, sessionExpired);
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(HttpStatus.BAD_REQUEST, sessionExpired);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }

    /**
     * Handles not found exception.
     *
     * @param exception not found exception
     * @return response entity containing error response
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorResponseDto> handleNotFound(Exception exception) {
        final String notFound = "Resource not found";
        log.error(STANDARD_LOG_ERROR, HttpStatus.NOT_FOUND, notFound);
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(HttpStatus.NOT_FOUND, notFound);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }
}
