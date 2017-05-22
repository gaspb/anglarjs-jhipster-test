package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.*;
import io.github.jhipster.sample.service.dto.CompetenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Competence and its DTO CompetenceDTO.
 */
@Mapper(componentModel = "spring", uses = {CompDomainMapper.class, })
public interface CompetenceMapper extends EntityMapper <CompetenceDTO, Competence> {
    @Mapping(source = "domain.id", target = "domainId")
    @Mapping(source = "domain.name", target = "domainName")
    CompetenceDTO toDto(Competence competence); 
    @Mapping(source = "domainId", target = "domain")
    Competence toEntity(CompetenceDTO competenceDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Competence fromId(Long id) {
        if (id == null) {
            return null;
        }
        Competence competence = new Competence();
        competence.setId(id);
        return competence;
    }
}
