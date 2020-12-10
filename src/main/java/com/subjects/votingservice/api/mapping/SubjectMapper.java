package com.subjects.votingservice.api.mapping;

import com.subjects.votingservice.infrastructure.entities.Subject;
import com.subjects.votingservice.api.dto.subject.SubjectDto;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.UUID;

/**
 * Subject mapper.
 */
@Mapper
public interface SubjectMapper {

    /**
     * Maps subject data transfer object list from subject entity list.
     *
     * @param subjectList subject entity list
     * @return {@link List} of {@link SubjectDto} subject data transfer object
     */
    List<SubjectDto> subjectListToSubjectDtoList(List<Subject> subjectList);

    /**
     * Maps subject entity from subject data transfer object.
     *
     * @param subjectDto subject data transfer object
     * @return {@link Subject} subject entity
     */
    Subject subjectDtoToSubject(SubjectDto subjectDto);

    /**
     * Maps subject data transfer object from subject entity.
     *
     * @param subject subject entity
     * @return {@link SubjectDto} subject data transfer object
     */
    SubjectDto subjectToSubjectDto(Subject subject);

    /**
     * Updates subject code.
     *
     * @param subjectDto subject data transfer object
     * @param subject    subject entity
     * @return {@link Subject} subject entity
     */
    @AfterMapping
    default Subject updateSubjectCode(SubjectDto subjectDto, @MappingTarget Subject subject) {
        if (StringUtils.isBlank(subject.getCode())) {
            subject.setCode(UUID.randomUUID().toString());
        }
        return subject;
    }
}
