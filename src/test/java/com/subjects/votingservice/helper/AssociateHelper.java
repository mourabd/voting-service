package com.subjects.votingservice.helper;

import com.subjects.votingservice.model.Associate;
import com.subjects.votingservice.shared.dto.associate.AssociateRequestDto;
import com.subjects.votingservice.shared.dto.associate.AssociateResponseDto;

/**
 * Associate helper class.
 */
public final class AssociateHelper {

    public static final String CPF = "cpf";
    public static final String FIRST_NAME = "first name";
    public static final String LAST_NAME = "last name";
    public static final int ASSOCIATE_LIST_ELEMENTS_NUMBER = 1;

    /**
     * Builds a new instance of associate.
     *
     * @return new instance of {@link Associate}
     */
    public static Associate buildAssociate() {
        return new Associate(CPF, FIRST_NAME, LAST_NAME);
    }

    /**
     * Builds a new instance of associate request data transfer object.
     *
     * @return new instance of {@link AssociateRequestDto}
     */
    public static AssociateRequestDto buildAssociateRequestDto() {
        final AssociateRequestDto associateRequestDto = new AssociateRequestDto();
        associateRequestDto.setCpf(CPF);
        associateRequestDto.setFirstName(FIRST_NAME);
        associateRequestDto.setLastName(LAST_NAME);
        return associateRequestDto;
    }

    /**
     * Builds a new instance of associate response data transfer object.
     *
     * @return new instance of {@link AssociateResponseDto}
     */
    public static AssociateResponseDto buildAssociateResponseDto() {
        final AssociateResponseDto associateResponseDto = new AssociateResponseDto();
        associateResponseDto.setCpf(CPF);
        associateResponseDto.setFirstName(FIRST_NAME);
        associateResponseDto.setLastName(LAST_NAME);
        return associateResponseDto;
    }
}