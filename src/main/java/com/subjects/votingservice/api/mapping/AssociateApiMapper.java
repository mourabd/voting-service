package com.subjects.votingservice.api.mapping;

import com.subjects.votingservice.api.dto.associate.AssociateRequestDto;
import com.subjects.votingservice.api.dto.associate.AssociateResponseDto;
import com.subjects.votingservice.domain.businessobjects.associate.AssociateBo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Associate mapper.
 */
@Mapper
public interface AssociateApiMapper {

    /**
     * Maps associate business object from associate request data transfer object.
     *
     * @param associateRequestDto associate request data transfer object
     * @return {@link AssociateBo} associate business object
     */
    AssociateBo map(AssociateRequestDto associateRequestDto);

    /**
     * Maps associate response data transfer object from associate business object.
     *
     * @param associateBo associate business object
     * @return {@link AssociateResponseDto} associate response data transfer object
     */
    AssociateResponseDto map(AssociateBo associateBo);

    /**
     * Maps associate response data transfer object list from associate business object list.
     *
     * @param associateBos associate business object list
     * @return {@link List} of {@link AssociateResponseDto} associate response data transfer object
     */
    List<AssociateResponseDto> map(List<AssociateBo> associateBos);
}
