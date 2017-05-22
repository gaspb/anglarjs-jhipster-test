package io.github.jhipster.sample.service;

import io.github.jhipster.sample.service.dto.SuggestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Suggestion.
 */
public interface SuggestionService {

    /**
     * Save a suggestion.
     *
     * @param suggestionDTO the entity to save
     * @return the persisted entity
     */
    SuggestionDTO save(SuggestionDTO suggestionDTO);

    /**
     *  Get all the suggestions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SuggestionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" suggestion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SuggestionDTO findOne(Long id);

    /**
     *  Delete the "id" suggestion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the suggestion corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SuggestionDTO> search(String query, Pageable pageable);
}
