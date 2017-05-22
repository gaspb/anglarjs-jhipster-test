package io.github.jhipster.sample.service.impl;

import io.github.jhipster.sample.service.UserDashboardService;
import io.github.jhipster.sample.domain.UserDashboard;
import io.github.jhipster.sample.repository.UserDashboardRepository;
import io.github.jhipster.sample.repository.search.UserDashboardSearchRepository;
import io.github.jhipster.sample.service.UserService;
import io.github.jhipster.sample.service.dto.UserDashboardDTO;
import io.github.jhipster.sample.service.mapper.UserDashboardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserDashboard.
 */
@Service
@Transactional
public class UserDashboardServiceImpl implements UserDashboardService{

    private final Logger log = LoggerFactory.getLogger(UserDashboardServiceImpl.class);

    private final UserDashboardRepository userDashboardRepository;
 private final UserService userService;
    private final UserDashboardMapper userDashboardMapper;

    private final UserDashboardSearchRepository userDashboardSearchRepository;

    public UserDashboardServiceImpl(UserService userService, UserDashboardRepository userDashboardRepository, UserDashboardMapper userDashboardMapper, UserDashboardSearchRepository userDashboardSearchRepository) {
        this.userService = userService;
        this.userDashboardRepository = userDashboardRepository;
        this.userDashboardMapper = userDashboardMapper;
        this.userDashboardSearchRepository = userDashboardSearchRepository;
    }

    /**
     * Save a userDashboard.
     *
     * @param userDashboardDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserDashboardDTO save(UserDashboardDTO userDashboardDTO) {
        log.debug("Request to save UserDashboard : {}", userDashboardDTO);

        UserDashboard userDashboard = userDashboardMapper.toEntity(userDashboardDTO);
      //  userDashboard.setUser(userService.getUserWithAuthorities());
       // log.info("-----------test "+userService.getUserWithAuthorities().getId());
        userDashboard = userDashboardRepository.save(userDashboard);
        UserDashboardDTO result = userDashboardMapper.toDto(userDashboard);
        userDashboardSearchRepository.save(userDashboard);
        return result;
    }

    /**
     *  Get all the userDashboards.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDashboardDTO> findAll() {
        log.debug("Request to get all UserDashboards");
        List<UserDashboardDTO> result = userDashboardRepository.findAllWithEagerRelationships().stream()
            .map(userDashboardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one userDashboard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserDashboardDTO findOne(Long id) {
        log.debug("Request to get UserDashboard : {}", id);
        UserDashboard userDashboard = userDashboardRepository.findOneWithEagerRelationships(id);
        UserDashboardDTO userDashboardDTO = userDashboardMapper.toDto(userDashboard);
        return userDashboardDTO;
    }

    /**
     *  Delete the  userDashboard by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserDashboard : {}", id);
        userDashboardRepository.delete(id);
        userDashboardSearchRepository.delete(id);
    }

    /**
     * Search for the userDashboard corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDashboardDTO> search(String query) {
        log.debug("Request to search UserDashboards for query {}", query);
        return StreamSupport
            .stream(userDashboardSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(userDashboardMapper::toDto)
            .collect(Collectors.toList());
    }
}
