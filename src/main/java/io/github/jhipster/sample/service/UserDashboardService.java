package io.github.jhipster.sample.service;

import io.github.jhipster.sample.service.dto.UserDashboardDTO;
import java.util.List;

/**
 * Service Interface for managing UserDashboard.
 */
public interface UserDashboardService {

    /**
     * Save a userDashboard.
     *
     * @param userDashboardDTO the entity to save
     * @return the persisted entity
     */
    UserDashboardDTO save(UserDashboardDTO userDashboardDTO);

    /**
     *  Get all the userDashboards.
     *  
     *  @return the list of entities
     */
    List<UserDashboardDTO> findAll();

    /**
     *  Get the "id" userDashboard.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserDashboardDTO findOne(Long id);

    /**
     *  Delete the "id" userDashboard.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userDashboard corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<UserDashboardDTO> search(String query);
}
