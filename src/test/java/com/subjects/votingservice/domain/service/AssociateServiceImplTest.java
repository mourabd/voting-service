package com.subjects.votingservice.domain.service;

import com.subjects.votingservice.domain.businessobjects.associate.AssociateBo;
import com.subjects.votingservice.domain.exception.AssociateAlreadyRegisteredException;
import com.subjects.votingservice.domain.exception.AssociateNotFoundException;
import com.subjects.votingservice.domain.mapping.associate.AssociateMapper;
import com.subjects.votingservice.domain.service.impl.AssociateServiceImpl;
import com.subjects.votingservice.infrastructure.entities.Associate;
import com.subjects.votingservice.infrastructure.repository.AssociateRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.subjects.votingservice.helper.AssociateHelper.*;

/**
 * Associate service implementation test.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssociateServiceImplTest {

    @Mock
    private transient AssociateMapper associateMapper;

    @Mock
    private transient AssociateRepository associateRepository;

    @InjectMocks
    private transient AssociateServiceImpl associateServiceImpl;

    /**
     * Save should return associate response data transfer object when associate is saved.
     */
    @Test
    public void saveShouldReturnAssociateResponseDtoWhenAssociateIsSaved() {
        final Associate associate = buildAssociate();
        final AssociateBo associateBo = buildAssociateBo();
        Mockito.when(associateRepository.existsByCpf(CPF)).thenReturn(false);
        Mockito.when(associateMapper.map(associateBo)).thenReturn(associate);
        Mockito.when(associateRepository.save(associate)).thenReturn(associate);
        Mockito.when(associateMapper.map(associate)).thenReturn(associateBo);
        final AssociateBo response = associateServiceImpl.save(associateBo);

        Assert.assertEquals(CPF, response.getCpf());
        Assert.assertEquals(FIRST_NAME, response.getFirstName());
        Assert.assertEquals(LAST_NAME, response.getLastName());
    }

    /**
     * Save should throw associate already registered exception when associate is already registered.
     */
    @Test(expected = AssociateAlreadyRegisteredException.class)
    public void saveShouldThrowAssociateAlreadyRegisteredExceptionWhenAssociateIsAlreadyRegistered() {
        Mockito.when(associateRepository.existsByCpf(CPF)).thenReturn(true);
        associateServiceImpl.save(buildAssociateBo());
    }

    /**
     * Find by cpf should return associate response data transfer object when associate is found.
     */
    @Test
    public void findByCpfShouldReturnAssociateResponseDtoWhenAssociateIsFound() {
        final Associate associate = buildAssociate();
        Mockito.when(associateRepository.findOneByCpf(CPF)).thenReturn(Optional.ofNullable(associate));
        Mockito.when(associateMapper.map(associate)).thenReturn(buildAssociateBo());
        final AssociateBo associateBo = associateServiceImpl.findByCpf(CPF);

        Assert.assertEquals(CPF, associateBo.getCpf());
        Assert.assertEquals(FIRST_NAME, associateBo.getFirstName());
        Assert.assertEquals(LAST_NAME, associateBo.getLastName());
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
        Mockito.when(associateMapper.map(associates)).thenReturn(Arrays.asList(buildAssociateBo()));
        List<AssociateBo> associateBoList = associateServiceImpl.findAll();
        Assert.assertEquals(ASSOCIATE_LIST_ELEMENTS_NUMBER, associateBoList.size());
    }
}
