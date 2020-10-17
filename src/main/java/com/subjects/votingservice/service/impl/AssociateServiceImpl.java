package com.subjects.votingservice.service.impl;

import com.subjects.votingservice.exception.AssociateAlreadyRegisteredException;
import com.subjects.votingservice.exception.AssociateNotFoundException;
import com.subjects.votingservice.mapping.AssociateMapper;
import com.subjects.votingservice.model.Associate;
import com.subjects.votingservice.repository.AssociateRepository;
import com.subjects.votingservice.service.AssociateService;
import com.subjects.votingservice.shared.dto.associate.AssociateRequestDto;
import com.subjects.votingservice.shared.dto.associate.AssociateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of associate service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AssociateServiceImpl implements AssociateService {

    private final AssociateRepository associateRepository;
    private final AssociateMapper associateMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public AssociateResponseDto save(AssociateRequestDto associateRequestDto) {
        if (isCpfAlreadyRegistered(associateRequestDto.getCpf())) {
            log.error("CPF already registered at database.");
            throw new AssociateAlreadyRegisteredException();
        }
        log.info("Saving associate from associate request data transfer object {}", associateRequestDto);
        final Associate associate = associateMapper.associateRequestDtoToAssociate(associateRequestDto);
        final AssociateResponseDto associateResponseDto = associateMapper.associateToAssociateResponseDto(associateRepository.save(associate));
        log.info("Associate {} was saved.", associate);
        return associateResponseDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssociateResponseDto findByCpf(String cpf) {
        log.info("Searching associate by cpf {}", cpf);
        final Associate associate = associateRepository.findOneByCpf(cpf).orElseThrow(AssociateNotFoundException::new);
        final AssociateResponseDto associateResponseDto = associateMapper.associateToAssociateResponseDto(associate);
        log.info("Associate {} was found.", associate);
        return associateResponseDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AssociateResponseDto> findAll() {
        log.info("Retrieving all associates");
        final List<Associate> associates = associateRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName", "lastName"));
        final List<AssociateResponseDto> associateResponseDtoList = associateMapper.associatesToAssociateResponseDtoList(associates);
        log.info("Number of associates retrieved: {}", associates.size());
        return associateResponseDtoList;
    }

    private boolean isCpfAlreadyRegistered(String cpf) {
        return associateRepository.existsByCpf(cpf);
    }
}
