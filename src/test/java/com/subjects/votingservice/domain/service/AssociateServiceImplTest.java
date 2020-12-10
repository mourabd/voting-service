package com.subjects.votingservice.domain.service;

import com.subjects.votingservice.domain.exception.AssociateAlreadyRegisteredException;
import com.subjects.votingservice.domain.exception.AssociateNotFoundException;
import com.subjects.votingservice.mapping.AssociateMapper;
import com.subjects.votingservice.infrastructure.entities.Associate;
import com.subjects.votingservice.infrastructure.repository.AssociateRepository;
import com.subjects.votingservice.domain.service.impl.AssociateServiceImpl;
import com.subjects.votingservice.api.dto.associate.AssociateRequestDto;
import com.subjects.votingservice.api.dto.associate.AssociateResponseDto;
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

import static com.subjects.votingservice.helper.AssociateHelper.*;

/**
 * Associate service implementation test.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssociateServiceImplTest {

    private transient MockMvc mockMvc;

    @Mock
    private transient AssociateRepository associateRepository;

    @Mock
    private transient AssociateMapper associateMapper;

    @InjectMocks
    private transient AssociateServiceImpl associateServiceImpl;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(associateServiceImpl).build();
    }

    /**
     * Save should return associate response data transfer object when associate is saved.
     */
    @Test
    public void saveShouldReturnAssociateResponseDtoWhenAssociateIsSaved() {
        final Associate associate = buildAssociate();
        final AssociateRequestDto associateRequestDto = buildAssociateRequestDto();
        Mockito.when(associateRepository.existsByCpf(CPF)).thenReturn(false);
        Mockito.when(associateMapper.associateRequestDtoToAssociate(associateRequestDto)).thenReturn(associate);
        Mockito.when(associateRepository.save(associate)).thenReturn(associate);
        Mockito.when(associateMapper.associateToAssociateResponseDto(associate)).thenReturn(buildAssociateResponseDto());
        final AssociateResponseDto associateResponseDto = associateServiceImpl.save(associateRequestDto);

        Assert.assertEquals(CPF, associateResponseDto.getCpf());
        Assert.assertEquals(FIRST_NAME, associateResponseDto.getFirstName());
        Assert.assertEquals(LAST_NAME, associateResponseDto.getLastName());
    }

    /**
     * Save should throw associate already registered exception when associate is already registered.
     */
    @Test(expected = AssociateAlreadyRegisteredException.class)
    public void saveShouldThrowAssociateAlreadyRegisteredExceptionWhenAssociateIsAlreadyRegistered() {
        Mockito.when(associateRepository.existsByCpf(CPF)).thenReturn(true);
        associateServiceImpl.save(buildAssociateRequestDto());
    }

    /**
     * Find by cpf should return associate response data transfer object when associate is found.
     */
    @Test
    public void findByCpfShouldReturnAssociateResponseDtoWhenAssociateIsFound() {
        final Associate associate = buildAssociate();
        Mockito.when(associateRepository.findOneByCpf(CPF)).thenReturn(Optional.ofNullable(associate));
        Mockito.when(associateMapper.associateToAssociateResponseDto(associate)).thenReturn(buildAssociateResponseDto());
        final AssociateResponseDto associateResponseDto = associateServiceImpl.findByCpf(CPF);

        Assert.assertEquals(CPF, associateResponseDto.getCpf());
        Assert.assertEquals(FIRST_NAME, associateResponseDto.getFirstName());
        Assert.assertEquals(LAST_NAME, associateResponseDto.getLastName());
    }

    /**
     * Find by cpf should throw associate not found exception when associate is not found.
     */
    @Test(expected = AssociateNotFoundException.class)
    public void findByCpfShouldThrowAssociateNotFoundExceptionWhenAssociateIsNotFound() {
        Mockito.when(associateRepository.findOneByCpf(CPF)).thenThrow(AssociateNotFoundException.class);
        associateServiceImpl.findByCpf(CPF);
    }

    /**
     * Find all should return associate response data transfer object list when any associate is found.
     */
    @Test
    public void findAllShouldReturnAssociateResponseDtoListWhenAnyAssociateIsFound() {
        final List<Associate> associates = Arrays.asList(buildAssociate());
        Mockito.when(associateRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName", "lastName"))).thenReturn(associates);
        Mockito.when(associateMapper.associatesToAssociateResponseDtoList(associates)).thenReturn(Arrays.asList(buildAssociateResponseDto()));
        List<AssociateResponseDto> associateResponseDtoList = associateServiceImpl.findAll();
        Assert.assertEquals(ASSOCIATE_LIST_ELEMENTS_NUMBER, associateResponseDtoList.size());
    }
}
