package com.subjects.votingservice.service;

import com.subjects.votingservice.exception.NotFoundException;
import com.subjects.votingservice.mapping.SubjectMapper;
import com.subjects.votingservice.model.Subject;
import com.subjects.votingservice.repository.SubjectRepository;
import com.subjects.votingservice.service.impl.SubjectServiceImpl;
import com.subjects.votingservice.shared.dto.subject.SubjectDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.subjects.votingservice.helper.SubjectHelper.*;

/**
 * Subject service implementation test.
 */
@RunWith(MockitoJUnitRunner.class)
public class SubjectServiceImplTest {

    private transient MockMvc mockMvc;

    @Mock
    private transient SubjectRepository subjectRepository;

    @Mock
    private transient SubjectMapper subjectMapper;

    @InjectMocks
    private transient SubjectServiceImpl subjectServiceImpl;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(subjectServiceImpl).build();
    }

    /**
     * Save should return subject data transfer object when subject is saved.
     */
    @Test
    public void saveShouldReturnSubjectDtoWhenSubjectIsSaved() {
        final Subject subject = buildSubject();
        final SubjectDto subjectDtoRequest = buildSubjectDto();
        Mockito.when(subjectMapper.subjectDtoToSubject(subjectDtoRequest)).thenReturn(subject);
        Mockito.when(subjectRepository.save(subject)).thenReturn(subject);
        Mockito.when(subjectMapper.subjectToSubjectDto(subject)).thenReturn(buildSubjectDto());
        final SubjectDto subjectDtoResponse = subjectServiceImpl.save(subjectDtoRequest);

        Assert.assertEquals(CODE, subjectDtoResponse.getCode());
        Assert.assertEquals(TITLE, subjectDtoResponse.getTitle());
        Assert.assertEquals(DESCRIPTION, subjectDtoResponse.getDescription());
    }

    /**
     * Find by code should return subject data transfer object when subject is found.
     */
    @Test
    public void findByCodeShouldReturnSubjectDtoWhenSubjectIsFound() {
        final Subject subject = buildSubject();
        Mockito.when(subjectRepository.findOneByCode(CODE)).thenReturn(Optional.ofNullable(subject));
        Mockito.when(subjectMapper.subjectToSubjectDto(subject)).thenReturn(buildSubjectDto());
        final SubjectDto subjectDto = subjectServiceImpl.findByCode(CODE);

        Assert.assertEquals(CODE, subjectDto.getCode());
        Assert.assertEquals(TITLE, subjectDto.getTitle());
        Assert.assertEquals(DESCRIPTION, subjectDto.getDescription());
    }

    /**
     * Find by code should throw not found exception when subject is not found.
     */
    @Test(expected = NotFoundException.class)
    public void findByCodeShouldThrowNotFoundExceptionWhenSubjectIsNotFound() {
        Mockito.when(subjectRepository.findOneByCode(CODE)).thenThrow(NotFoundException.class);
        subjectServiceImpl.findByCode(CODE);
    }

    /**
     * Find all should return subject data transfer object list when any subject is found.
     */
    @Test
    public void findAllShouldReturnSubjectDtoListWhenAnySubjectIsFound() {
        final List<Subject> subjects = Arrays.asList(buildSubject());
        Mockito.when(subjectRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDateTime"))).thenReturn(subjects);
        Mockito.when(subjectMapper.subjectListToSubjectDtoList(subjects)).thenReturn(Arrays.asList(buildSubjectDto()));
        List<SubjectDto> subjectDtoList = subjectServiceImpl.findAll();
        Assert.assertEquals(SUBJECT_LIST_ELEMENTS_NUMBER, subjectDtoList.size());
    }
}
