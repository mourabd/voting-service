package com.subjects.votingservice.api.controller;

import com.subjects.votingservice.domain.service.VoteService;
import com.subjects.votingservice.api.dto.RestErrorResponseDto;
import com.subjects.votingservice.api.dto.vote.VoteRequestDto;
import com.subjects.votingservice.api.dto.vote.VoteResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.subjects.votingservice.api.constants.HttpConstants.*;
import static org.springdoc.core.Constants.POST_METHOD;

/**
 * Vote controller.
 */
@Tag(name = "Vote Controller")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/voting-service/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    private final VoteService voteService;

    /**
     * Saves a vote.
     *
     * @param voteRequestDto {@link VoteRequestDto} vote request data transfer object
     * @return {@link VoteResponseDto} vote response data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = POST_METHOD, summary = "Registers an associate vote for a given voting session.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Vote request data transfer object",
        required = true,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VoteRequestDto.class))
    )
    @ApiResponse(
        responseCode = SUCCESS_REQUEST_CODE,
        description = SUCCESS_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VoteResponseDto.class))
    )
    @ApiResponse(
        responseCode = BAD_REQUEST_CODE,
        description = BAD_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @PostMapping(value = "/vote")
    public VoteResponseDto saveVote(@Valid @RequestBody VoteRequestDto voteRequestDto) {
        log.info("Vote request data transfer object {}", voteRequestDto);
        final VoteResponseDto voteResponseDto = voteService.save(voteRequestDto);
        log.info("Vote response data transfer object {}", voteResponseDto);
        return voteResponseDto;
    }
}
