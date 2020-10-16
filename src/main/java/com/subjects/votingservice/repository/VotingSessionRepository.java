package com.subjects.votingservice.repository;

import com.subjects.votingservice.model.VotingSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Voting session repository.
 */
@Repository
public interface VotingSessionRepository extends MongoRepository<VotingSession, String> {

    /**
     * Finds voting session by subject code.
     *
     * @param subjectCode subject code
     * @return {@link Optional} of {@link VotingSession}
     */
    Optional<VotingSession> findOneBySubjectCode(String subjectCode);

    /**
     * Checks if voting session exists by subject code.
     *
     * @param subjectCode subject code
     * @return {@code true} if voting session is found by subject code, {@code false} otherwise
     */
    boolean existsBySubjectCode(String subjectCode);
}
