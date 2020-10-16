package com.subjects.votingservice.repository;

import com.subjects.votingservice.model.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Subject repository.
 */
@Repository
public interface SubjectRepository extends MongoRepository<Subject, String> {

    /**
     * Finds subject by code.
     *
     * @param code code
     * @return {@link Optional} of {@link Subject}
     */
    Optional<Subject> findOneByCode(String code);
}
