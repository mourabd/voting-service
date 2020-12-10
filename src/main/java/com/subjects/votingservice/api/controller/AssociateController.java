package com.subjects.votingservice.api.controller;

import com.subjects.votingservice.domain.service.AssociateService;
import com.subjects.votingservice.api.dto.RestErrorResponseDto;
import com.subjects.votingservice.api.dto.associate.AssociateRequestDto;
import com.subjects.votingservice.api.dto.associate.AssociateResponseDto;
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

import static com.subjects.votingservice.api.constants.HttpConstants.*;
import static org.springdoc.core.Constants.GET_METHOD;
import static org.springdoc.core.Constants.POST_METHOD;

/**
 * Associate controller.
 */
@Tag(name = "Associate Controller")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/voting-service/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssociateController {

    private static final String ASSOCIATE_RESPONSE_DTO_INFO_LOG = "Associate response data transfer object {}";

    private final AssociateService associateService;

    /**
     * Searches associate by its cpf.
     *
     * @param cpf to be used to search associate
     * @return {@link AssociateResponseDto} associate response data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = GET_METHOD, summary = "Searches associate by its cpf")
    @Parameter(in = ParameterIn.PATH, name = "cpf", required = true, example = "00000000000")
    @ApiResponse(
        responseCode = SUCCESS_REQUEST_CODE,
        description = SUCCESS_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssociateResponseDto.class))
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
    @GetMapping(value = "/associate/{cpf}")
    public AssociateResponseDto findByCpf(@NotBlank(message = "CPF is required") @PathVariable("cpf") final String cpf) {
        log.info("Searching associate by cpf {}", cpf);
        final AssociateResponseDto associateResponseDto = associateService.findByCpf(cpf);
        log.info(ASSOCIATE_RESPONSE_DTO_INFO_LOG, associateResponseDto);
        return associateResponseDto;
    }

    /**
     * Retrieves all associates.
     *
     * @return {@link List} of {@link AssociateResponseDto} associate response data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = GET_METHOD, summary = "Retrieves all associates")
    @ApiResponse(
        responseCode = SUCCESS_REQUEST_CODE,
        description = SUCCESS_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = AssociateResponseDto.class)))
    )
    @ApiResponse(
        responseCode = BAD_REQUEST_CODE,
        description = BAD_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @GetMapping(value = "/associate")
    public List<AssociateResponseDto> findAll() {
        log.info("Retrieving all associates");
        final List<AssociateResponseDto> associateResponseDtoList = associateService.findAll();
        log.info("Associate response data transfer object list {}", associateResponseDtoList);
        return associateResponseDtoList;
    }

    /**
     * Saves an associate.
     *
     * @param associateRequestDto {@link AssociateRequestDto} associate request data transfer object
     * @return {@link AssociateResponseDto} associate response data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = POST_METHOD, summary = "Register an associate in database")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Associate request data transfer object",
        required = true,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssociateRequestDto.class))
    )
    @ApiResponse(
        responseCode = SUCCESS_REQUEST_CODE,
        description = SUCCESS_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssociateResponseDto.class))
    )
    @ApiResponse(
        responseCode = BAD_REQUEST_CODE,
        description = BAD_REQUEST_DESCRIPTION,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @PostMapping(value = "/associate")
    public AssociateResponseDto saveAssociate(@Valid @RequestBody AssociateRequestDto associateRequestDto) {
        log.info("Associate request data transfer object {}", associateRequestDto);
        final AssociateResponseDto associateResponseDto = associateService.save(associateRequestDto);
        log.info(ASSOCIATE_RESPONSE_DTO_INFO_LOG, associateResponseDto);
        return associateResponseDto;
    }
}
