package io.github.jhipster.sample.service;

import io.github.jhipster.sample.service.dto.CompDomainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing CompDomain.
 */
public interface CompDomainService {

    /**
     * Save a compDomain.
     *
     * @param compDomainDTO the entity to save
     * @return the persisted entity
     */
    CompDomainDTO save(CompDomainDTO compDomainDTO);

    /**
     *  Get all the compDomains.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CompDomainDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" compDomain.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CompDomainDTO findOne(Long id);

    /**
     *  Delete the "id" compDomain.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the compDomain corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CompDomainDTO> search(String query, Pageable pageable);
}
