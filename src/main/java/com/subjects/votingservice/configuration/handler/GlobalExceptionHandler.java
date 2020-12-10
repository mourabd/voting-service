package com.subjects.votingservice.configuration.handler;

import com.subjects.votingservice.domain.exception.AssociateAlreadyRegisteredException;
import com.subjects.votingservice.domain.exception.AssociateAlreadyVotedException;
import com.subjects.votingservice.domain.exception.AssociateUnableToVoteException;
import com.subjects.votingservice.domain.exception.InvalidDateTimeException;
import com.subjects.votingservice.domain.exception.NotFoundException;
import com.subjects.votingservice.domain.exception.SessionAlreadyOpenException;
import com.subjects.votingservice.domain.exception.SessionExpiredException;
import com.subjects.votingservice.domain.exception.SubjectCodeAlreadyRegisteredException;
import com.subjects.votingservice.api.dto.RestErrorResponseDto;
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
        log.error(STANDARD_LOG_ERROR, HttpStatus.BAD_REQUEST, invalidRequest + " " + errors);
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
        log.error(STANDARD_LOG_ERROR, HttpStatus.UNSUPPORTED_MEDIA_TYPE, message);
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
        log.error(STANDARD_LOG_ERROR, HttpStatus.METHOD_NOT_ALLOWED, errorMessage);
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(HttpStatus.METHOD_NOT_ALLOWED, errorMessage);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }

    /**
     * Handles associate already registered exception.
     *
     * @return response entity containing error response
     */
    @ExceptionHandler(AssociateAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleAssociateAlreadyRegistered() {
        return buildRestErrorResponseEntity(HttpStatus.BAD_REQUEST, "Associate already registered");
    }

    /**
     * Handles subject code already registered exception.
     *
     * @return response entity containing error response
     */
    @ExceptionHandler(SubjectCodeAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleAssociateSubjectCodeAlreadyRegistered() {
        return buildRestErrorResponseEntity(HttpStatus.BAD_REQUEST, "Subject code already registered");
    }

    /**
     * Handles associate already voted exception.
     *
     * @return response entity containing error response
     */
    @ExceptionHandler(AssociateAlreadyVotedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleAssociateAlreadyVoted() {
        return buildRestErrorResponseEntity(HttpStatus.BAD_REQUEST, "Associate already voted");
    }

    /**
     * Handles associate unable to vote exception.
     *
     * @return response entity containing error response
     */
    @ExceptionHandler(AssociateUnableToVoteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleAssociateUnableToVote() {
        return buildRestErrorResponseEntity(HttpStatus.BAD_REQUEST, "Associate unable to vote");
    }

    /**
     * Handles invalid date time exception.
     *
     * @return response entity containing error response
     */
    @ExceptionHandler(InvalidDateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleInvalidDateTime() {
        return buildRestErrorResponseEntity(HttpStatus.BAD_REQUEST, "Invalid date time");
    }

    /**
     * Handles session already open exception.
     *
     * @return response entity containing error response
     */
    @ExceptionHandler(SessionAlreadyOpenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleSessionAlreadyOpen() {
        return buildRestErrorResponseEntity(HttpStatus.BAD_REQUEST, "Session cannot be open more than once");
    }

    /**
     * Handles session expired exception.
     *
     * @return response entity containing error response
     */
    @ExceptionHandler(SessionExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleSessionExpired() {
        return buildRestErrorResponseEntity(HttpStatus.BAD_REQUEST, "Voting session is expired");
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
        return buildRestErrorResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    private ResponseEntity<RestErrorResponseDto> buildRestErrorResponseEntity(HttpStatus httpStatus, String errorMessage) {
        log.error(STANDARD_LOG_ERROR, httpStatus, errorMessage);
        final RestErrorResponseDto restErrorResponseDto = new RestErrorResponseDto(httpStatus, errorMessage);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }
}
