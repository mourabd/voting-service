package com.subjects.votingservice.service.impl;

import com.subjects.votingservice.exception.NotFoundException;
import com.subjects.votingservice.mapping.SubjectMapper;
import com.subjects.votingservice.model.Subject;
import com.subjects.votingservice.repository.SubjectRepository;
import com.subjects.votingservice.service.SubjectService;
import com.subjects.votingservice.shared.dto.subject.SubjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of subject service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public SubjectDto save(SubjectDto subjectDto) {
        log.info("Saving subject from subject data transfer object {}", subjectDto);
        final Subject subject = subjectMapper.subjectDtoToSubject(subjectDto);
        final SubjectDto savedSubjectDto = subjectMapper.subjectToSubjectDto(subjectRepository.save(subject));
        log.info("Subject {} was saved.", subject);
        return savedSubjectDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubjectDto findByCode(String code) {
        log.info("Searching subject by code {}", code);
        final Subject subject = subjectRepository.findOneByCode(code).orElseThrow(NotFoundException::new);
        final SubjectDto subjectDto = subjectMapper.subjectToSubjectDto(subject);
        log.info("Subject {} was found.", subject);
        return subjectDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SubjectDto> findAll() {
        log.info("Retrieving all subjects");
        final List<Subject> subjects = subjectRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDateTime"));
        final List<SubjectDto> subjectDtoList = subjectMapper.subjectListToSubjectDtoList(subjects);
        log.info("Number of subjects retrieved: {}", subjects.size());
        return subjectDtoList;
    }
}
