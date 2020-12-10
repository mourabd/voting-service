package com.subjects.votingservice.domain.mapping.associate;

import com.subjects.votingservice.domain.businessobjects.associate.AssociateBo;
import com.subjects.votingservice.infrastructure.entities.Associate;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Associate business object mapper.
 */
@Mapper
public interface AssociateMapper {

    /**
     * Maps associate entity from associate business object object.
     *
     * @param associateBo associate business object
     * @return {@link Associate} associate entity
     */
    Associate map(AssociateBo associateBo);

    /**
     * Maps associate business object object from associate entity.
     *
     * @param associate associate entity
     * @return {@link AssociateBo} associate business object
     */
    AssociateBo map(Associate associate);

    /**
     * Maps associate business object list object from associate entity list.
     *
     * @param associates associate entity list
     * @return {@link AssociateBo} associate business object list
     */
    List<AssociateBo> map(List<Associate> associates);
}
