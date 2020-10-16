package com.subjects.votingservice.repository;

import com.subjects.votingservice.model.Associate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Associate repository.
 */
@Repository
public interface AssociateRepository extends MongoRepository<Associate, String> {

    /**
     * Checks if associate is found by cpf.
     *
     * @param cpf cpf
     * @return {@code true} if associate is found by cpf, {@code false} otherwise
     */
    boolean existsByCpf(String cpf);

    /**
     * Finds associate by cpf.
     *
     * @param cpf cpf
     * @return {@link Optional} of {@link Associate}
     */
    Optional<Associate> findOneByCpf(String cpf);
}
