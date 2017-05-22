package io.github.jhipster.sample.service.impl;

import io.github.jhipster.sample.service.CompetenceService;
import io.github.jhipster.sample.domain.Competence;
import io.github.jhipster.sample.repository.CompetenceRepository;
import io.github.jhipster.sample.repository.search.CompetenceSearchRepository;
import io.github.jhipster.sample.service.dto.CompetenceDTO;
import io.github.jhipster.sample.service.mapper.CompetenceMapper;
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
 * Service Implementation for managing Competence.
 */
@Service
@Transactional
public class CompetenceServiceImpl implements CompetenceService{

    private final Logger log = LoggerFactory.getLogger(CompetenceServiceImpl.class);

    private final CompetenceRepository competenceRepository;

    private final CompetenceMapper competenceMapper;

    private final CompetenceSearchRepository competenceSearchRepository;

    public CompetenceServiceImpl(CompetenceRepository competenceRepository, CompetenceMapper competenceMapper, CompetenceSearchRepository competenceSearchRepository) {
        this.competenceRepository = competenceRepository;
        this.competenceMapper = competenceMapper;
        this.competenceSearchRepository = competenceSearchRepository;
    }

    /**
     * Save a competence.
     *
     * @param competenceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompetenceDTO save(CompetenceDTO competenceDTO) {
        log.debug("Request to save Competence : {}", competenceDTO);
        Competence competence = competenceMapper.toEntity(competenceDTO);
        competence = competenceRepository.save(competence);
        CompetenceDTO result = competenceMapper.toDto(competence);
        competenceSearchRepository.save(competence);
        return result;
    }

    /**
     *  Get all the competences.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompetenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Competences");
        Page<Competence> result = competenceRepository.findAll(pageable);
        return result.map(competence -> competenceMapper.toDto(competence));
    }

    /**
     *  Get one competence by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CompetenceDTO findOne(Long id) {
        log.debug("Request to get Competence : {}", id);
        Competence competence = competenceRepository.findOne(id);
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);
        return competenceDTO;
    }

    /**
     *  Delete the  competence by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competence : {}", id);
        competenceRepository.delete(id);
        competenceSearchRepository.delete(id);
    }

    /**
     * Search for the competence corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompetenceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Competences for query {}", query);
        Page<Competence> result = competenceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(competence -> competenceMapper.toDto(competence));
    }
}
