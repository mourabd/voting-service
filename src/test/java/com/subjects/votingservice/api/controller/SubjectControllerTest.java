package com.subjects.votingservice.api.controller;

import com.subjects.votingservice.domain.service.SubjectService;
import com.subjects.votingservice.api.dto.subject.SubjectDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.subjects.votingservice.helper.SubjectHelper.*;

/**
 * Subject controller test.
 */
@RunWith(MockitoJUnitRunner.class)
public class SubjectControllerTest {

    private transient MockMvc mockMvc;

    @Mock
    private transient SubjectService subjectService;

    @InjectMocks
    private transient SubjectController subjectController;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    /**
     * Find by code should return ok status when subject is found.
     */
    @Test
    public void findByCodeShouldReturnOkStatusWhenSubjectIsFound() {
        Mockito.when(subjectService.findByCode(CODE)).thenReturn(buildSubjectDto());
        final SubjectDto subjectDto = subjectController.findByCode(CODE);

        Assert.assertEquals(CODE, subjectDto.getCode());
        Assert.assertEquals(TITLE, subjectDto.getTitle());
        Assert.assertEquals(DESCRIPTION, subjectDto.getDescription());
    }

    /**
     * Find all should return ok status when subject list is returned.
     */
    @Test
    public void findAllShouldReturnOkStatusWhenSubjectListIsReturned() {
        Mockito.when(subjectService.findAll()).thenReturn(Arrays.asList(buildSubjectDto()));
        final List<SubjectDto> subjectDtos = subjectController.findAll();
        Assert.assertEquals(SUBJECT_LIST_ELEMENTS_NUMBER, subjectDtos.size());
    }

    /**
     * Save subject should return ok status when subject is saved.
     */
    @Test
    public void saveSubjectShouldReturnOkStatusWhenSubjectIsSaved() {
        final SubjectDto subjectDtoRequest = buildSubjectDto();
        Mockito.when(subjectService.save(subjectDtoRequest)).thenReturn(buildSubjectDto());
        final SubjectDto subjectDtoResponse = subjectController.saveSubject(subjectDtoRequest);

        Assert.assertEquals(subjectDtoRequest.getCode(), subjectDtoResponse.getCode());
        Assert.assertEquals(subjectDtoRequest.getTitle(), subjectDtoResponse.getTitle());
        Assert.assertEquals(subjectDtoRequest.getDescription(), subjectDtoResponse.getDescription());
    }
}
