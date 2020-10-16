package com.subjects.votingservice.mapping;

import com.subjects.votingservice.model.Subject;
import com.subjects.votingservice.shared.dto.subject.SubjectDto;
import com.subjects.votingservice.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

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
            subject.setCode(Utils.generateCode(subject.getTitle()));
        }
        return subject;
    }
}
