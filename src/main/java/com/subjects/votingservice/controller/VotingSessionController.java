package com.subjects.votingservice.controller;

import com.subjects.votingservice.service.VoteService;
import com.subjects.votingservice.service.VotingSessionService;
import com.subjects.votingservice.shared.dto.RestErrorResponseDto;
import com.subjects.votingservice.shared.dto.session.VotingSessionRequestDto;
import com.subjects.votingservice.shared.dto.session.VotingSessionResponseDto;
import com.subjects.votingservice.shared.dto.session.VotingSessionResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.subjects.votingservice.util.Constants.*;
import static org.springdoc.core.Constants.GET_METHOD;
import static org.springdoc.core.Constants.POST_METHOD;

/**
 * Voting session controller.
 */
@Tag(name = "Voting Session Controller")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/voting-service/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class VotingSessionController {

    private static final String SUBJECT_CODE = "subjectCode";
    private static final String VOTING_SESSION_RESPONSE_DTO_INFO_LOG = "Voting session response data transfer object {}";

    private final VotingSessionService votingSessionService;
    private final VoteService voteService;

    /**
     * Searches voting session by subject code.
     *
     * @param subjectCode to be used to search voting session
     * @return {@link VotingSessionResponseDto} voting session response data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = GET_METHOD, summary = "Searches voting session by subject code")
    @Parameter(in = ParameterIn.PATH, name = SUBJECT_CODE, required = true, example = "SUBJECT-CODE")
    @ApiResponse(
        responseCode = SUCCESS_REQUEST_CODE,
        description = SUCCESS_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VotingSessionResponseDto.class))
    )
    @ApiResponse(
        responseCode = BAD_REQUEST_CODE,
        description = BAD_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @ApiResponse(
        responseCode = NOT_FOUND_REQUEST_CODE,
        description = NOT_FOUND_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @GetMapping(value = "/session/{subjectCode}")
    public VotingSessionResponseDto findBySubjectCode(@NotBlank @PathVariable(SUBJECT_CODE) final String subjectCode) {
        log.info("Searching voting session by subject code {}", subjectCode);
        final VotingSessionResponseDto votingSessionResponseDto = votingSessionService.findBySubjectCode(subjectCode);
        log.info(VOTING_SESSION_RESPONSE_DTO_INFO_LOG, votingSessionResponseDto);
        return votingSessionResponseDto;
    }

    /**
     * Searches voting session result by subject code.
     *
     * @param subjectCode to be used to search voting session
     * @return {@link VotingSessionResultDto} voting session result data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = GET_METHOD, summary = "Searches voting session result by subject code")
    @Parameter(in = ParameterIn.PATH, name = SUBJECT_CODE, required = true, example = "SUBJECT-CODE")
    @ApiResponse(
        responseCode = SUCCESS_REQUEST_CODE,
        description = SUCCESS_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VotingSessionResultDto.class))
    )
    @ApiResponse(
        responseCode = BAD_REQUEST_CODE,
        description = BAD_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @ApiResponse(
        responseCode = NOT_FOUND_REQUEST_CODE,
        description = NOT_FOUND_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @GetMapping(value = "/session/result/{subjectCode}")
    public VotingSessionResultDto findVotingSessionResults(@NotBlank @PathVariable(SUBJECT_CODE) final String subjectCode) {
        log.info("Searching voting session result by subject code {}", subjectCode);
        final VotingSessionResultDto votingSessionResultDto = voteService.findVotingSessionResultsBySubjectCode(subjectCode);
        log.info("Voting session result data transfer object {}", votingSessionResultDto);
        return votingSessionResultDto;
    }

    /**
     * Retrieves all voting sessions.
     *
     * @return {@link List} of {@link VotingSessionResponseDto} voting session response data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = GET_METHOD, summary = "Retrieves all voting sessions")
    @ApiResponse(
        responseCode = SUCCESS_REQUEST_CODE,
        description = SUCCESS_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = VotingSessionResponseDto.class)))
    )
    @ApiResponse(
        responseCode = BAD_REQUEST_CODE,
        description = BAD_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @GetMapping(value = "/session")
    public List<VotingSessionResponseDto> findAll() {
        log.info("Retrieving all voting sessions");
        final List<VotingSessionResponseDto> votingSessionResponseDtoList = votingSessionService.findAll();
        log.info("Voting session response data transfer object list {}", votingSessionResponseDtoList);
        return votingSessionResponseDtoList;
    }

    /**
     * Saves a new session for voting.
     *
     * @param votingSessionRequestDto {@link VotingSessionRequestDto} voting session request data transfer object
     * @return {@link VotingSessionResponseDto} voting session response data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = POST_METHOD, summary = "Opens a new voting session for associates register their votes for given subjects.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Voting session request data transfer object",
        required = true,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VotingSessionRequestDto.class))
    )
    @ApiResponse(
        responseCode = SUCCESS_REQUEST_CODE,
        description = SUCCESS_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VotingSessionResponseDto.class))
    )
    @ApiResponse(
        responseCode = BAD_REQUEST_CODE,
        description = BAD_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @PostMapping(value = "/session")
    public VotingSessionResponseDto saveSession(@Valid @RequestBody VotingSessionRequestDto votingSessionRequestDto) {
        log.info("Voting session request data transfer object {}", votingSessionRequestDto);
        final VotingSessionResponseDto votingSessionResponseDto = votingSessionService.save(votingSessionRequestDto);
        log.info(VOTING_SESSION_RESPONSE_DTO_INFO_LOG, votingSessionResponseDto);
        return votingSessionResponseDto;
    }
}
