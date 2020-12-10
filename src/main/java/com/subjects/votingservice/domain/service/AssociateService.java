package com.subjects.votingservice.domain.service;

import com.subjects.votingservice.api.dto.associate.AssociateRequestDto;
import com.subjects.votingservice.api.dto.associate.AssociateResponseDto;

import java.util.List;

/**
 * Associate service interface.
 */
public interface AssociateService {

    /**
     * Saves an associate.
     *
     * @param associateRequestDto {@link AssociateRequestDto} associate request data transfer object
     * @return {@link AssociateResponseDto} associate response data transfer object
     */
    AssociateResponseDto save(AssociateRequestDto associateRequestDto);

    /**
     * Searches associate by its cpf.
     *
     * @param cpf to be used to search associate
     * @return {@link AssociateResponseDto} associate response data transfer object
     */
    AssociateResponseDto findByCpf(String cpf);

    /**
     * Retrieves all associates.
     *
     * @return {@link List} of {@link AssociateResponseDto} associate response data transfer object
     */
    List<AssociateResponseDto> findAll();
}
