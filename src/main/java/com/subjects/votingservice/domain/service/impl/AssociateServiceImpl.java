package com.subjects.votingservice.domain.service.impl;

import com.subjects.votingservice.domain.businessobjects.associate.AssociateBo;
import com.subjects.votingservice.domain.exception.AssociateAlreadyRegisteredException;
import com.subjects.votingservice.domain.exception.AssociateNotFoundException;
import com.subjects.votingservice.domain.mapping.associate.AssociateMapper;
import com.subjects.votingservice.domain.service.AssociateService;
import com.subjects.votingservice.infrastructure.entities.Associate;
import com.subjects.votingservice.infrastructure.repository.AssociateRepository;
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

    private final AssociateMapper associateMapper;
    private final AssociateRepository associateRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public AssociateBo save(AssociateBo associateBo) {
        if (isCpfAlreadyRegistered(associateBo.getCpf())) {
            log.error("CPF already registered at database.");
            throw new AssociateAlreadyRegisteredException();
        }
        log.info("Saving associate from associate business object {}", associateBo);
        final Associate associate = associateMapper.map(associateBo);
        return associateMapper.map(associateRepository.save(associate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssociateBo findByCpf(String cpf) {
        log.info("Searching associate by cpf {}", cpf);
        final Associate associate = associateRepository.findOneByCpf(cpf).orElseThrow(AssociateNotFoundException::new);
        return associateMapper.map(associate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AssociateBo> findAll() {
        log.info("Retrieving all associates");
        final List<Associate> associates = associateRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName", "lastName"));
        return associateMapper.map(associates);
    }

    private boolean isCpfAlreadyRegistered(String cpf) {
        return associateRepository.existsByCpf(cpf);
    }
}
