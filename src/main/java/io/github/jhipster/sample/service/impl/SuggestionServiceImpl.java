package io.github.jhipster.sample.service.impl;

import io.github.jhipster.sample.service.SuggestionService;
import io.github.jhipster.sample.domain.Suggestion;
import io.github.jhipster.sample.repository.SuggestionRepository;
import io.github.jhipster.sample.repository.search.SuggestionSearchRepository;
import io.github.jhipster.sample.service.dto.SuggestionDTO;
import io.github.jhipster.sample.service.mapper.SuggestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Suggestion.
 */
@Service
@Transactional
public class SuggestionServiceImpl implements SuggestionService{

    private final Logger log = LoggerFactory.getLogger(SuggestionServiceImpl.class);
    
    private final SuggestionRepository suggestionRepository;

    private final SuggestionMapper suggestionMapper;

    private final SuggestionSearchRepository suggestionSearchRepository;

    public SuggestionServiceImpl(SuggestionRepository suggestionRepository, SuggestionMapper suggestionMapper, SuggestionSearchRepository suggestionSearchRepository) {
        this.suggestionRepository = suggestionRepository;
        this.suggestionMapper = suggestionMapper;
        this.suggestionSearchRepository = suggestionSearchRepository;
    }

    /**
     * Save a suggestion.
     *
     * @param suggestionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SuggestionDTO save(SuggestionDTO suggestionDTO) {
        log.debug("Request to save Suggestion : {}", suggestionDTO);
        Suggestion suggestion = suggestionMapper.toEntity(suggestionDTO);
        suggestion = suggestionRepository.save(suggestion);
        SuggestionDTO result = suggestionMapper.toDto(suggestion);
        suggestionSearchRepository.save(suggestion);
        return result;
    }

    /**
     *  Get all the suggestions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SuggestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Suggestions");
        Page<Suggestion> result = suggestionRepository.findAll(pageable);
        return result.map(suggestion -> suggestionMapper.toDto(suggestion));
    }

    /**
     *  Get one suggestion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SuggestionDTO findOne(Long id) {
        log.debug("Request to get Suggestion : {}", id);
        Suggestion suggestion = suggestionRepository.findOne(id);
        SuggestionDTO suggestionDTO = suggestionMapper.toDto(suggestion);
        return suggestionDTO;
    }

    /**
     *  Delete the  suggestion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Suggestion : {}", id);
        suggestionRepository.delete(id);
        suggestionSearchRepository.delete(id);
    }

    /**
     * Search for the suggestion corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SuggestionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Suggestions for query {}", query);
        Page<Suggestion> result = suggestionSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(suggestion -> suggestionMapper.toDto(suggestion));
    }
}
