package com.subjects.votingservice.controller;

import com.subjects.votingservice.service.SubjectService;
import com.subjects.votingservice.shared.dto.RestErrorResponseDto;
import com.subjects.votingservice.shared.dto.subject.SubjectDto;
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

import static com.subjects.votingservice.constants.Constants.*;
import static org.springdoc.core.Constants.GET_METHOD;
import static org.springdoc.core.Constants.POST_METHOD;

/**
 * Subject controller.
 */
@Tag(name = "Subject Controller")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/voting-service/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubjectController {

    private static final String SUBJECT_DTO_INFO_LOG = "Subject data transfer object {}";

    private final SubjectService subjectService;

    /**
     * Searches subject by its code.
     *
     * @param code to be used to search subject
     * @return {@link SubjectDto} subject data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = GET_METHOD, summary = "Searches subject by its code")
    @Parameter(in = ParameterIn.PATH, name = "code", required = true, example = "SUBJECT-CODE")
    @ApiResponse(
        responseCode = SUCCESS_REQUEST_CODE,
        description = SUCCESS_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SubjectDto.class))
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
    @GetMapping(value = "/subject/{code}")
    public SubjectDto findByCode(@NotBlank @PathVariable("code") final String code) {
        log.info("Searching subject by code {}", code);
        final SubjectDto subjectDto = subjectService.findByCode(code);
        log.info(SUBJECT_DTO_INFO_LOG, subjectDto);
        return subjectDto;
    }

    /**
     * Retrieves all subjects.
     *
     * @return {@link List} of {@link SubjectDto} subject data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = GET_METHOD, summary = "Retrieves all subjects")
    @ApiResponse(
        responseCode = SUCCESS_REQUEST_CODE,
        description = SUCCESS_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = SubjectDto.class)))
    )
    @ApiResponse(
        responseCode = BAD_REQUEST_CODE,
        description = BAD_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @GetMapping(value = "/subject")
    public List<SubjectDto> findAll() {
        log.info("Retrieving all subjects");
        final List<SubjectDto> subjectDtoList = subjectService.findAll();
        log.info("Subject data transfer object list {}", subjectDtoList);
        return subjectDtoList;
    }

    /**
     * Saves a subject.
     *
     * @param subjectDto {@link SubjectDto} subject data transfer object
     * @return {@link SubjectDto} subject data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = POST_METHOD, summary = "Creates a new subject to be voted by associates")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Subject data transfer object",
        required = true,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SubjectDto.class))
    )
    @ApiResponse(
        responseCode = SUCCESS_REQUEST_CODE,
        description = SUCCESS_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SubjectDto.class))
    )
    @ApiResponse(
        responseCode = BAD_REQUEST_CODE,
        description = BAD_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @PostMapping(value = "/subject")
    public SubjectDto saveSubject(@Valid @RequestBody SubjectDto subjectDto) {
        log.info(SUBJECT_DTO_INFO_LOG, subjectDto);
        final SubjectDto savedSubjectDto = subjectService.save(subjectDto);
        log.info(SUBJECT_DTO_INFO_LOG, savedSubjectDto);
        return savedSubjectDto;
    }
}
