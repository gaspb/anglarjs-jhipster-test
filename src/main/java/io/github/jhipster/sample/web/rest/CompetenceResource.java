package io.github.jhipster.sample.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.sample.service.CompetenceService;
import io.github.jhipster.sample.web.rest.util.HeaderUtil;
import io.github.jhipster.sample.web.rest.util.PaginationUtil;
import io.github.jhipster.sample.service.dto.CompetenceDTO;
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
 * REST controller for managing Competence.
 */
@RestController
@RequestMapping("/api")
public class CompetenceResource {

    private final Logger log = LoggerFactory.getLogger(CompetenceResource.class);

    private static final String ENTITY_NAME = "competence";
        
    private final CompetenceService competenceService;

    public CompetenceResource(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    /**
     * POST  /competences : Create a new competence.
     *
     * @param competenceDTO the competenceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new competenceDTO, or with status 400 (Bad Request) if the competence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/competences")
    @Timed
    public ResponseEntity<CompetenceDTO> createCompetence(@Valid @RequestBody CompetenceDTO competenceDTO) throws URISyntaxException {
        log.debug("REST request to save Competence : {}", competenceDTO);
        if (competenceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new competence cannot already have an ID")).body(null);
        }
        CompetenceDTO result = competenceService.save(competenceDTO);
        return ResponseEntity.created(new URI("/api/competences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /competences : Updates an existing competence.
     *
     * @param competenceDTO the competenceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated competenceDTO,
     * or with status 400 (Bad Request) if the competenceDTO is not valid,
     * or with status 500 (Internal Server Error) if the competenceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/competences")
    @Timed
    public ResponseEntity<CompetenceDTO> updateCompetence(@Valid @RequestBody CompetenceDTO competenceDTO) throws URISyntaxException {
        log.debug("REST request to update Competence : {}", competenceDTO);
        if (competenceDTO.getId() == null) {
            return createCompetence(competenceDTO);
        }
        CompetenceDTO result = competenceService.save(competenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, competenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /competences : get all the competences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of competences in body
     */
    @GetMapping("/competences")
    @Timed
    public ResponseEntity<List<CompetenceDTO>> getAllCompetences(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Competences");
        Page<CompetenceDTO> page = competenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/competences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /competences/:id : get the "id" competence.
     *
     * @param id the id of the competenceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the competenceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/competences/{id}")
    @Timed
    public ResponseEntity<CompetenceDTO> getCompetence(@PathVariable Long id) {
        log.debug("REST request to get Competence : {}", id);
        CompetenceDTO competenceDTO = competenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(competenceDTO));
    }

    /**
     * DELETE  /competences/:id : delete the "id" competence.
     *
     * @param id the id of the competenceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/competences/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
        log.debug("REST request to delete Competence : {}", id);
        competenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/competences?query=:query : search for the competence corresponding
     * to the query.
     *
     * @param query the query of the competence search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/competences")
    @Timed
    public ResponseEntity<List<CompetenceDTO>> searchCompetences(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Competences for query {}", query);
        Page<CompetenceDTO> page = competenceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/competences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
