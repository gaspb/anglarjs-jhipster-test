package io.github.jhipster.sample.service.impl;

import io.github.jhipster.sample.service.CompDomainService;
import io.github.jhipster.sample.domain.CompDomain;
import io.github.jhipster.sample.repository.CompDomainRepository;
import io.github.jhipster.sample.repository.search.CompDomainSearchRepository;
import io.github.jhipster.sample.service.dto.CompDomainDTO;
import io.github.jhipster.sample.service.mapper.CompDomainMapper;
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
 * Service Implementation for managing CompDomain.
 */
@Service
@Transactional
public class CompDomainServiceImpl implements CompDomainService{

    private final Logger log = LoggerFactory.getLogger(CompDomainServiceImpl.class);
    
    private final CompDomainRepository compDomainRepository;

    private final CompDomainMapper compDomainMapper;

    private final CompDomainSearchRepository compDomainSearchRepository;

    public CompDomainServiceImpl(CompDomainRepository compDomainRepository, CompDomainMapper compDomainMapper, CompDomainSearchRepository compDomainSearchRepository) {
        this.compDomainRepository = compDomainRepository;
        this.compDomainMapper = compDomainMapper;
        this.compDomainSearchRepository = compDomainSearchRepository;
    }

    /**
     * Save a compDomain.
     *
     * @param compDomainDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompDomainDTO save(CompDomainDTO compDomainDTO) {
        log.debug("Request to save CompDomain : {}", compDomainDTO);
        CompDomain compDomain = compDomainMapper.toEntity(compDomainDTO);
        compDomain = compDomainRepository.save(compDomain);
        CompDomainDTO result = compDomainMapper.toDto(compDomain);
        compDomainSearchRepository.save(compDomain);
        return result;
    }

    /**
     *  Get all the compDomains.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompDomainDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompDomains");
        Page<CompDomain> result = compDomainRepository.findAll(pageable);
        return result.map(compDomain -> compDomainMapper.toDto(compDomain));
    }

    /**
     *  Get one compDomain by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CompDomainDTO findOne(Long id) {
        log.debug("Request to get CompDomain : {}", id);
        CompDomain compDomain = compDomainRepository.findOne(id);
        CompDomainDTO compDomainDTO = compDomainMapper.toDto(compDomain);
        return compDomainDTO;
    }

    /**
     *  Delete the  compDomain by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompDomain : {}", id);
        compDomainRepository.delete(id);
        compDomainSearchRepository.delete(id);
    }

    /**
     * Search for the compDomain corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompDomainDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompDomains for query {}", query);
        Page<CompDomain> result = compDomainSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(compDomain -> compDomainMapper.toDto(compDomain));
    }
}
