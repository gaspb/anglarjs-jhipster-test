package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.*;
import io.github.jhipster.sample.service.dto.CompDomainDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CompDomain and its DTO CompDomainDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompDomainMapper extends EntityMapper <CompDomainDTO, CompDomain> {
    
    @Mapping(target = "competences", ignore = true)
    @Mapping(target = "projects", ignore = true)
    CompDomain toEntity(CompDomainDTO compDomainDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default CompDomain fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompDomain compDomain = new CompDomain();
        compDomain.setId(id);
        return compDomain;
    }
}
