package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.*;
import io.github.jhipster.sample.service.dto.SuggestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Suggestion and its DTO SuggestionDTO.
 */
@Mapper(componentModel = "spring", uses = {UserDashboardMapper.class, })
public interface SuggestionMapper extends EntityMapper <SuggestionDTO, Suggestion> {
    @Mapping(source = "author.id", target = "authorId")

    SuggestionDTO toDto(Suggestion suggestion);
    @Mapping(source = "authorId", target = "author")
    Suggestion toEntity(SuggestionDTO suggestionDTO);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Suggestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Suggestion suggestion = new Suggestion();
        suggestion.setId(id);
        return suggestion;
    }
}
