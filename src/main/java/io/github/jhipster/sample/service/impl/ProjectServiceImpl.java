package io.github.jhipster.sample.service.impl;

import io.github.jhipster.sample.security.SecurityUtils;
import io.github.jhipster.sample.service.ProjectService;
import io.github.jhipster.sample.domain.Project;
import io.github.jhipster.sample.repository.ProjectRepository;
import io.github.jhipster.sample.repository.search.ProjectSearchRepository;
import io.github.jhipster.sample.service.UserService;
import io.github.jhipster.sample.service.dto.ProjectDTO;
import io.github.jhipster.sample.service.mapper.ProjectMapper;
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
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectMapper projectMapper;

    private final ProjectSearchRepository projectSearchRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, ProjectSearchRepository projectSearchRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.projectSearchRepository = projectSearchRepository;
        this.userService = userService;
    }

    /**
     * Save a project.
     *
     * @param projectDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        log.debug("Request to save Project : {}", projectDTO);
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        ProjectDTO result = projectMapper.toDto(project);
        projectSearchRepository.save(project);
        return result;
    }

    /**
     *  Get all the projects.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProjectDTO> findAll(Pageable pageable) { //BY_AUTHOR
        log.debug("Request to get all Projects");

        Page<Project> result = projectRepository.findAll( pageable);
        return result.map(project -> projectMapper.toDto(project));
    }
    /**
     *  Get all the projects from user.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProjectDTO> findByCurrentUser(Pageable pageable) { //BY_AUTHOR
        log.debug("Request to get all Projects");

        Page<Project> result = projectRepository.findByAuthorId(userService.getUserWithAuthorities().getId(), pageable);
        return result.map(project -> projectMapper.toDto(project));
    }
    /**
     *  Get one project by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProjectDTO findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        Project project = projectRepository.findOneWithEagerRelationships(id);
        ProjectDTO projectDTO = projectMapper.toDto(project);
        return projectDTO;
    }

    /**
     *  Delete the  project by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.delete(id);
        projectSearchRepository.delete(id);
    }

    /**
     * Search for the project corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProjectDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Projects for query {}", query);
        Page<Project> result = projectSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(project -> projectMapper.toDto(project));
    }


}
