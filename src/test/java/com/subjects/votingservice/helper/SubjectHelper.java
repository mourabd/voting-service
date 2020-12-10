package com.subjects.votingservice.helper;

import com.subjects.votingservice.infrastructure.entities.Subject;
import com.subjects.votingservice.api.dto.subject.SubjectDto;

/**
 * Subject helper class.
 */
public final class SubjectHelper {

    public static final String CODE = "code";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final int SUBJECT_LIST_ELEMENTS_NUMBER = 1;

    /**
     * Builds a new instance of subject.
     *
     * @return new instance of {@link Subject}
     */
    public static Subject buildSubject() {
        final Subject subject = new Subject(CODE);
        subject.setTitle(TITLE);
        subject.setDescription(DESCRIPTION);
        return subject;
    }

    /**
     * Builds a new instance of subject data transfer object.
     *
     * @return new instance of {@link SubjectDto}
     */
    public static SubjectDto buildSubjectDto() {
        final SubjectDto subjectDto = new SubjectDto();
        subjectDto.setCode(CODE);
        subjectDto.setTitle(TITLE);
        subjectDto.setDescription(DESCRIPTION);
        return subjectDto;
    }
}
