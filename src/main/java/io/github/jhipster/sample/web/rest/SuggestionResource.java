package io.github.jhipster.sample.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.sample.service.SuggestionService;
import io.github.jhipster.sample.web.rest.util.HeaderUtil;
import io.github.jhipster.sample.web.rest.util.PaginationUtil;
import io.github.jhipster.sample.service.dto.SuggestionDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Suggestion.
 */
@RestController
@RequestMapping("/api")
public class SuggestionResource {

    private final Logger log = LoggerFactory.getLogger(SuggestionResource.class);

    private static final String ENTITY_NAME = "suggestion";
        
    private final SuggestionService suggestionService;

    public SuggestionResource(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    /**
     * POST  /suggestions : Create a new suggestion.
     *
     * @param suggestionDTO the suggestionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new suggestionDTO, or with status 400 (Bad Request) if the suggestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/suggestions")
    @Timed
    public ResponseEntity<SuggestionDTO> createSuggestion(@Valid @RequestBody SuggestionDTO suggestionDTO) throws URISyntaxException {
        log.debug("REST request to save Suggestion : {}", suggestionDTO);
        if (suggestionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new suggestion cannot already have an ID")).body(null);
        }
        SuggestionDTO result = suggestionService.save(suggestionDTO);
        return ResponseEntity.created(new URI("/api/suggestions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /suggestions : Updates an existing suggestion.
     *
     * @param suggestionDTO the suggestionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated suggestionDTO,
     * or with status 400 (Bad Request) if the suggestionDTO is not valid,
     * or with status 500 (Internal Server Error) if the suggestionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/suggestions")
    @Timed
    public ResponseEntity<SuggestionDTO> updateSuggestion(@Valid @RequestBody SuggestionDTO suggestionDTO) throws URISyntaxException {
        log.debug("REST request to update Suggestion : {}", suggestionDTO);
        if (suggestionDTO.getId() == null) {
            return createSuggestion(suggestionDTO);
        }
        SuggestionDTO result = suggestionService.save(suggestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, suggestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /suggestions : get all the suggestions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of suggestions in body
     */
    @GetMapping("/suggestions")
    @Timed
    public ResponseEntity<List<SuggestionDTO>> getAllSuggestions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Suggestions");
        Page<SuggestionDTO> page = suggestionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/suggestions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /suggestions/:id : get the "id" suggestion.
     *
     * @param id the id of the suggestionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the suggestionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/suggestions/{id}")
    @Timed
    public ResponseEntity<SuggestionDTO> getSuggestion(@PathVariable Long id) {
        log.debug("REST request to get Suggestion : {}", id);
        SuggestionDTO suggestionDTO = suggestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(suggestionDTO));
    }

    /**
     * DELETE  /suggestions/:id : delete the "id" suggestion.
     *
     * @param id the id of the suggestionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/suggestions/{id}")
    @Timed
    public ResponseEntity<Void> deleteSuggestion(@PathVariable Long id) {
        log.debug("REST request to delete Suggestion : {}", id);
        suggestionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/suggestions?query=:query : search for the suggestion corresponding
     * to the query.
     *
     * @param query the query of the suggestion search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/suggestions")
    @Timed
    public ResponseEntity<List<SuggestionDTO>> searchSuggestions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Suggestions for query {}", query);
        Page<SuggestionDTO> page = suggestionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/suggestions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
