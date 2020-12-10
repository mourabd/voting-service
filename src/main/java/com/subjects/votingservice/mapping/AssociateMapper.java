package com.subjects.votingservice.mapping;

import com.subjects.votingservice.infrastructure.entities.Associate;
import com.subjects.votingservice.api.dto.associate.AssociateRequestDto;
import com.subjects.votingservice.api.dto.associate.AssociateResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Associate mapper.
 */
@Mapper
public interface AssociateMapper {

    /**
     * Maps associate entity from associate request data transfer object.
     *
     * @param associateRequestDto associate request data transfer object
     * @return {@link Associate} associate entity
     */
    Associate associateRequestDtoToAssociate(AssociateRequestDto associateRequestDto);

    /**
     * Maps associate response data transfer object from associate entity.
     *
     * @param associate associate entity
     * @return {@link AssociateResponseDto} associate response data transfer object
     */
    AssociateResponseDto associateToAssociateResponseDto(Associate associate);

    /**
     * Maps associate response data transfer object list from associate entity list.
     *
     * @param associates associate entity list
     * @return {@link List} of {@link AssociateResponseDto} associate response data transfer object
     */
    List<AssociateResponseDto> associatesToAssociateResponseDtoList(List<Associate> associates);
}
