package com.subjects.votingservice.controller;

import com.subjects.votingservice.service.AssociateService;
import com.subjects.votingservice.shared.dto.associate.AssociateRequestDto;
import com.subjects.votingservice.shared.dto.associate.AssociateResponseDto;
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

import static com.subjects.votingservice.helper.AssociateHelper.*;

/**
 * Associate controller test.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssociateControllerTest {

    private transient MockMvc mockMvc;

    @Mock
    private transient AssociateService associateService;

    @InjectMocks
    private transient AssociateController associateController;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(associateController).build();
    }

    /**
     * Find by cpf should return ok status when associate is found.
     */
    @Test
    public void findByCpfShouldReturnOkStatusWhenAssociateIsFound() {
        Mockito.when(associateService.findByCpf(CPF)).thenReturn(buildAssociateResponseDto());
        final AssociateResponseDto associateResponseDto = associateController.findByCpf(CPF);

        Assert.assertEquals(CPF, associateResponseDto.getCpf());
        Assert.assertEquals(FIRST_NAME, associateResponseDto.getFirstName());
        Assert.assertEquals(LAST_NAME, associateResponseDto.getLastName());
    }

    /**
     * find all should return ok status when associate list is returned.
     */
    @Test
    public void findAllShouldReturnOkStatusWhenAssociateListIsReturned() {
        Mockito.when(associateService.findAll()).thenReturn(Arrays.asList(buildAssociateResponseDto()));
        final List<AssociateResponseDto> associateResponseDtos = associateController.findAll();
        Assert.assertEquals(ASSOCIATE_LIST_ELEMENTS_NUMBER, associateResponseDtos.size());
    }

    /**
     * Save associate should return ok status when associate is saved.
     */
    @Test
    public void saveAssociateShouldReturnOkStatusWhenAssociateIsSaved() {
        final AssociateRequestDto associateRequestDto = buildAssociateRequestDto();
        Mockito.when(associateService.save(associateRequestDto)).thenReturn(buildAssociateResponseDto());
        final AssociateResponseDto associateResponseDto = associateController.saveAssociate(associateRequestDto);

        Assert.assertEquals(associateRequestDto.getCpf(), associateResponseDto.getCpf());
        Assert.assertEquals(associateRequestDto.getFirstName(), associateResponseDto.getFirstName());
        Assert.assertEquals(associateRequestDto.getLastName(), associateResponseDto.getLastName());
    }
}
