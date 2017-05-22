package io.github.jhipster.sample.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.sample.service.CompDomainService;
import io.github.jhipster.sample.web.rest.util.HeaderUtil;
import io.github.jhipster.sample.web.rest.util.PaginationUtil;
import io.github.jhipster.sample.service.dto.CompDomainDTO;
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
 * REST controller for managing CompDomain.
 */
@RestController
@RequestMapping("/api")
public class CompDomainResource {

    private final Logger log = LoggerFactory.getLogger(CompDomainResource.class);

    private static final String ENTITY_NAME = "compDomain";
        
    private final CompDomainService compDomainService;

    public CompDomainResource(CompDomainService compDomainService) {
        this.compDomainService = compDomainService;
    }

    /**
     * POST  /comp-domains : Create a new compDomain.
     *
     * @param compDomainDTO the compDomainDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compDomainDTO, or with status 400 (Bad Request) if the compDomain has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comp-domains")
    @Timed
    public ResponseEntity<CompDomainDTO> createCompDomain(@Valid @RequestBody CompDomainDTO compDomainDTO) throws URISyntaxException {
        log.debug("REST request to save CompDomain : {}", compDomainDTO);
        if (compDomainDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new compDomain cannot already have an ID")).body(null);
        }
        CompDomainDTO result = compDomainService.save(compDomainDTO);
        return ResponseEntity.created(new URI("/api/comp-domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comp-domains : Updates an existing compDomain.
     *
     * @param compDomainDTO the compDomainDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compDomainDTO,
     * or with status 400 (Bad Request) if the compDomainDTO is not valid,
     * or with status 500 (Internal Server Error) if the compDomainDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comp-domains")
    @Timed
    public ResponseEntity<CompDomainDTO> updateCompDomain(@Valid @RequestBody CompDomainDTO compDomainDTO) throws URISyntaxException {
        log.debug("REST request to update CompDomain : {}", compDomainDTO);
        if (compDomainDTO.getId() == null) {
            return createCompDomain(compDomainDTO);
        }
        CompDomainDTO result = compDomainService.save(compDomainDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compDomainDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comp-domains : get all the compDomains.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of compDomains in body
     */
    @GetMapping("/comp-domains")
    @Timed
    public ResponseEntity<List<CompDomainDTO>> getAllCompDomains(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CompDomains");
        Page<CompDomainDTO> page = compDomainService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comp-domains");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comp-domains/:id : get the "id" compDomain.
     *
     * @param id the id of the compDomainDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compDomainDTO, or with status 404 (Not Found)
     */
    @GetMapping("/comp-domains/{id}")
    @Timed
    public ResponseEntity<CompDomainDTO> getCompDomain(@PathVariable Long id) {
        log.debug("REST request to get CompDomain : {}", id);
        CompDomainDTO compDomainDTO = compDomainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compDomainDTO));
    }

    /**
     * DELETE  /comp-domains/:id : delete the "id" compDomain.
     *
     * @param id the id of the compDomainDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comp-domains/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompDomain(@PathVariable Long id) {
        log.debug("REST request to delete CompDomain : {}", id);
        compDomainService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/comp-domains?query=:query : search for the compDomain corresponding
     * to the query.
     *
     * @param query the query of the compDomain search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/comp-domains")
    @Timed
    public ResponseEntity<List<CompDomainDTO>> searchCompDomains(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of CompDomains for query {}", query);
        Page<CompDomainDTO> page = compDomainService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/comp-domains");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
