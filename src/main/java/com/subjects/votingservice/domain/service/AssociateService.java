package com.subjects.votingservice.domain.service;

import com.subjects.votingservice.domain.businessobjects.associate.AssociateBo;

import java.util.List;

/**
 * Associate service interface.
 */
public interface AssociateService {

    /**
     * Saves an associate.
     *
     * @param associateBo {@link AssociateBo} associate business object
     * @return {@link AssociateBo} associate business object
     */
    AssociateBo save(AssociateBo associateBo);

    /**
     * Searches associate by its cpf.
     *
     * @param cpf to be used to search associate
     * @return {@link AssociateBo} associate business object
     */
    AssociateBo findByCpf(String cpf);

    /**
     * Retrieves all associates.
     *
     * @return {@link List} of {@link AssociateBo} associate business object
     */
    List<AssociateBo> findAll();
}
