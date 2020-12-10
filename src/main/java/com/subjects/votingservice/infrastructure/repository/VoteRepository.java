package com.subjects.votingservice.infrastructure.repository;

import com.subjects.votingservice.infrastructure.entities.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Vote repository.
 */
@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

    /**
     * Checks if associate has voted in given voting session.
     *
     * @param cpf         associate cpf
     * @param subjectCode subject code
     * @return {@code true} if vote is found by associate cpf and session subject code, {@code false} otherwise
     */
    boolean existsByAssociateCpfAndSessionSubjectCode(String cpf, String subjectCode);

    /**
     * Finds vote list by session subject code.
     *
     * @param subjectCode subject code
     * @return {@link List} of {@link Vote}
     */
    List<Vote> findBySessionSubjectCode(String subjectCode);
}
