package io.github.jhipster.sample.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.sample.service.UserDashboardService;
import io.github.jhipster.sample.service.UserService;
import io.github.jhipster.sample.web.rest.util.HeaderUtil;
import io.github.jhipster.sample.service.dto.UserDashboardDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing UserDashboard.
 */
@RestController
@RequestMapping("/api")
public class UserDashboardResource {

    private final Logger log = LoggerFactory.getLogger(UserDashboardResource.class);

    private static final String ENTITY_NAME = "userDashboard";

    private final UserDashboardService userDashboardService;
    private final UserService userService;

    public UserDashboardResource(UserDashboardService userDashboardService, UserService userService) {
        this.userDashboardService = userDashboardService;
        this.userService=userService;
    }

    /**
     * POST  /user-dashboards : Create a new userDashboard.
     *
     * @param userDashboardDTO the userDashboardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userDashboardDTO, or with status 400 (Bad Request) if the userDashboard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-dashboards")
    @Timed
    public ResponseEntity<UserDashboardDTO> createUserDashboard(@RequestBody UserDashboardDTO userDashboardDTO) throws URISyntaxException {
        log.debug("REST request to save UserDashboard : {}", userDashboardDTO);
        if (userDashboardDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userDashboard cannot already have an ID")).body(null);
        }

        UserDashboardDTO result = userDashboardService.save(userDashboardDTO);
        return ResponseEntity.created(new URI("/api/user-dashboards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-dashboards : Updates an existing userDashboard.
     *
     * @param userDashboardDTO the userDashboardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userDashboardDTO,
     * or with status 400 (Bad Request) if the userDashboardDTO is not valid,
     * or with status 500 (Internal Server Error) if the userDashboardDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-dashboards")
    @Timed
    public ResponseEntity<UserDashboardDTO> updateUserDashboard(@RequestBody UserDashboardDTO userDashboardDTO) throws URISyntaxException {
        log.debug("REST request to update UserDashboard : {}", userDashboardDTO);
        if (userDashboardDTO.getId() == null) {

            return createUserDashboard(userDashboardDTO);
        }
        log.error("-----------test333 "+userService.getUserWithAuthorities().getId());
        UserDashboardDTO result = userDashboardService.save(userDashboardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDashboardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-dashboards : get all the userDashboards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userDashboards in body
     */
    @GetMapping("/user-dashboards")
    @Timed
    public List<UserDashboardDTO> getAllUserDashboards() {
        log.debug("REST request to get all UserDashboards");
        return userDashboardService.findAll();
    }

    /**
     * GET  /user-dashboards/:id : get the "id" userDashboard.
     *
     * @param id the id of the userDashboardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userDashboardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-dashboards/{id}")
    @Timed
    public ResponseEntity<UserDashboardDTO> getUserDashboard(@PathVariable Long id) {
        log.debug("REST request to get UserDashboard : {}", id);
        UserDashboardDTO userDashboardDTO = userDashboardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userDashboardDTO));
    }

    /**
     * DELETE  /user-dashboards/:id : delete the "id" userDashboard.
     *
     * @param id the id of the userDashboardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-dashboards/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserDashboard(@PathVariable Long id) {
        log.debug("REST request to delete UserDashboard : {}", id);
        userDashboardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-dashboards?query=:query : search for the userDashboard corresponding
     * to the query.
     *
     * @param query the query of the userDashboard search
     * @return the result of the search
     */
    @GetMapping("/_search/user-dashboards")
    @Timed
    public List<UserDashboardDTO> searchUserDashboards(@RequestParam String query) {
        log.debug("REST request to search UserDashboards for query {}", query);
        return userDashboardService.search(query);
    }


}
