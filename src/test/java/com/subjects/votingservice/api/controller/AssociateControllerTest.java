package com.subjects.votingservice.api.controller;

import com.subjects.votingservice.api.dto.associate.AssociateRequestDto;
import com.subjects.votingservice.api.dto.associate.AssociateResponseDto;
import com.subjects.votingservice.api.mapping.AssociateApiMapper;
import com.subjects.votingservice.domain.businessobjects.associate.AssociateBo;
import com.subjects.votingservice.domain.service.AssociateService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.subjects.votingservice.helper.AssociateHelper.*;

/**
 * Associate controller test.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssociateControllerTest {

    @Mock
    private transient AssociateApiMapper associateApiMapper;

    @Mock
    private transient AssociateService associateService;

    @InjectMocks
    private transient AssociateController associateController;

    /**
     * Find by cpf should return ok status when associate is found.
     */
    @Test
    public void findByCpfShouldReturnOkStatusWhenAssociateIsFound() {
        final AssociateBo associateBo = buildAssociateBo();
        Mockito.when(associateService.findByCpf(CPF)).thenReturn(associateBo);
        Mockito.when(associateApiMapper.map(associateBo)).thenReturn(buildAssociateResponseDto());
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
        final List<AssociateBo> associateBoList = Arrays.asList(buildAssociateBo());
        Mockito.when(associateService.findAll()).thenReturn(associateBoList);
        Mockito.when(associateApiMapper.map(associateBoList)).thenReturn(Arrays.asList(buildAssociateResponseDto()));
        final List<AssociateResponseDto> associateResponseDtos = associateController.findAll();
        Assert.assertEquals(ASSOCIATE_LIST_ELEMENTS_NUMBER, associateResponseDtos.size());
    }

    /**
     * Save associate should return ok status when associate is saved.
     */
    @Test
    public void saveAssociateShouldReturnOkStatusWhenAssociateIsSaved() {
        final AssociateRequestDto associateRequestDto = buildAssociateRequestDto();
        final AssociateBo associateBo = buildAssociateBo();
        Mockito.when(associateApiMapper.map(associateRequestDto)).thenReturn(associateBo);
        Mockito.when(associateService.save(associateBo)).thenReturn(associateBo);
        Mockito.when(associateApiMapper.map(associateBo)).thenReturn(buildAssociateResponseDto());
        final AssociateResponseDto associateResponseDto = associateController.saveAssociate(associateRequestDto);

        Assert.assertEquals(associateRequestDto.getCpf(), associateResponseDto.getCpf());
        Assert.assertEquals(associateRequestDto.getFirstName(), associateResponseDto.getFirstName());
        Assert.assertEquals(associateRequestDto.getLastName(), associateResponseDto.getLastName());
    }
}
