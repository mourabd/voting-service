package com.subjects.votingservice.service;

import com.subjects.votingservice.shared.dto.subject.SubjectDto;

import java.util.List;

/**
 * Subject service interface.
 */
public interface SubjectService {

    /**
     * Saves a subject.
     *
     * @param subjectDto {@link SubjectDto} subject data transfer object
     * @return {@link SubjectDto} subject data transfer object
     */
    SubjectDto save(SubjectDto subjectDto);

    /**
     * Searches subject by its code.
     *
     * @param code to be used to search subject
     * @return {@link SubjectDto} subject data transfer object
     */
    SubjectDto findByCode(String code);

    /**
     * Retrieves all subjects.
     *
     * @return {@link List} of {@link SubjectDto} subject data transfer object
     */
    List<SubjectDto> findAll();
}
