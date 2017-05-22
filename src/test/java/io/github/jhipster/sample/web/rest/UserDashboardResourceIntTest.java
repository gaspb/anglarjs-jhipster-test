package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.JhipsterElasticsearchSampleApplicationApp;

import io.github.jhipster.sample.domain.UserDashboard;
import io.github.jhipster.sample.repository.UserDashboardRepository;
import io.github.jhipster.sample.service.UserDashboardService;
import io.github.jhipster.sample.repository.search.UserDashboardSearchRepository;
import io.github.jhipster.sample.service.UserService;
import io.github.jhipster.sample.service.dto.UserDashboardDTO;
import io.github.jhipster.sample.service.mapper.UserDashboardMapper;
import io.github.jhipster.sample.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserDashboardResource REST controller.
 *
 * @see UserDashboardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterElasticsearchSampleApplicationApp.class)
public class UserDashboardResourceIntTest {

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private UserDashboardRepository userDashboardRepository;

    @Autowired
    private UserDashboardMapper userDashboardMapper;

    @Autowired
    private UserDashboardService userDashboardService;

    @Autowired
    private UserDashboardSearchRepository userDashboardSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserDashboardMockMvc;

    private UserDashboard userDashboard;

    private UserService userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserDashboardResource userDashboardResource = new UserDashboardResource(userDashboardService, userService);
        this.restUserDashboardMockMvc = MockMvcBuilders.standaloneSetup(userDashboardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDashboard createEntity(EntityManager em) {
        UserDashboard userDashboard = new UserDashboard()
            .phone(DEFAULT_PHONE);
        return userDashboard;
    }

    @Before
    public void initTest() {
        userDashboardSearchRepository.deleteAll();
        userDashboard = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDashboard() throws Exception {
        int databaseSizeBeforeCreate = userDashboardRepository.findAll().size();

        // Create the UserDashboard
        UserDashboardDTO userDashboardDTO = userDashboardMapper.toDto(userDashboard);
        restUserDashboardMockMvc.perform(post("/api/user-dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDashboardDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDashboard in the database
        List<UserDashboard> userDashboardList = userDashboardRepository.findAll();
        assertThat(userDashboardList).hasSize(databaseSizeBeforeCreate + 1);
        UserDashboard testUserDashboard = userDashboardList.get(userDashboardList.size() - 1);
        assertThat(testUserDashboard.getPhone()).isEqualTo(DEFAULT_PHONE);

        // Validate the UserDashboard in Elasticsearch
        UserDashboard userDashboardEs = userDashboardSearchRepository.findOne(testUserDashboard.getId());
        assertThat(userDashboardEs).isEqualToComparingFieldByField(testUserDashboard);
    }

    @Test
    @Transactional
    public void createUserDashboardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDashboardRepository.findAll().size();

        // Create the UserDashboard with an existing ID
        userDashboard.setId(1L);
        UserDashboardDTO userDashboardDTO = userDashboardMapper.toDto(userDashboard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDashboardMockMvc.perform(post("/api/user-dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDashboardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserDashboard> userDashboardList = userDashboardRepository.findAll();
        assertThat(userDashboardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserDashboards() throws Exception {
        // Initialize the database
        userDashboardRepository.saveAndFlush(userDashboard);

        // Get all the userDashboardList
        restUserDashboardMockMvc.perform(get("/api/user-dashboards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDashboard.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getUserDashboard() throws Exception {
        // Initialize the database
        userDashboardRepository.saveAndFlush(userDashboard);

        // Get the userDashboard
        restUserDashboardMockMvc.perform(get("/api/user-dashboards/{id}", userDashboard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userDashboard.getId().intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserDashboard() throws Exception {
        // Get the userDashboard
        restUserDashboardMockMvc.perform(get("/api/user-dashboards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDashboard() throws Exception {
        // Initialize the database
        userDashboardRepository.saveAndFlush(userDashboard);
        userDashboardSearchRepository.save(userDashboard);
        int databaseSizeBeforeUpdate = userDashboardRepository.findAll().size();

        // Update the userDashboard
        UserDashboard updatedUserDashboard = userDashboardRepository.findOne(userDashboard.getId());
        updatedUserDashboard
            .phone(UPDATED_PHONE);
        UserDashboardDTO userDashboardDTO = userDashboardMapper.toDto(updatedUserDashboard);

        restUserDashboardMockMvc.perform(put("/api/user-dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDashboardDTO)))
            .andExpect(status().isOk());

        // Validate the UserDashboard in the database
        List<UserDashboard> userDashboardList = userDashboardRepository.findAll();
        assertThat(userDashboardList).hasSize(databaseSizeBeforeUpdate);
        UserDashboard testUserDashboard = userDashboardList.get(userDashboardList.size() - 1);
        assertThat(testUserDashboard.getPhone()).isEqualTo(UPDATED_PHONE);

        // Validate the UserDashboard in Elasticsearch
        UserDashboard userDashboardEs = userDashboardSearchRepository.findOne(testUserDashboard.getId());
        assertThat(userDashboardEs).isEqualToComparingFieldByField(testUserDashboard);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDashboard() throws Exception {
        int databaseSizeBeforeUpdate = userDashboardRepository.findAll().size();

        // Create the UserDashboard
        UserDashboardDTO userDashboardDTO = userDashboardMapper.toDto(userDashboard);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserDashboardMockMvc.perform(put("/api/user-dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDashboardDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDashboard in the database
        List<UserDashboard> userDashboardList = userDashboardRepository.findAll();
        assertThat(userDashboardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserDashboard() throws Exception {
        // Initialize the database
        userDashboardRepository.saveAndFlush(userDashboard);
        userDashboardSearchRepository.save(userDashboard);
        int databaseSizeBeforeDelete = userDashboardRepository.findAll().size();

        // Get the userDashboard
        restUserDashboardMockMvc.perform(delete("/api/user-dashboards/{id}", userDashboard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userDashboardExistsInEs = userDashboardSearchRepository.exists(userDashboard.getId());
        assertThat(userDashboardExistsInEs).isFalse();

        // Validate the database is empty
        List<UserDashboard> userDashboardList = userDashboardRepository.findAll();
        assertThat(userDashboardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserDashboard() throws Exception {
        // Initialize the database
        userDashboardRepository.saveAndFlush(userDashboard);
        userDashboardSearchRepository.save(userDashboard);

        // Search the userDashboard
        restUserDashboardMockMvc.perform(get("/api/_search/user-dashboards?query=id:" + userDashboard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDashboard.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDashboard.class);
        UserDashboard userDashboard1 = new UserDashboard();
        userDashboard1.setId(1L);
        UserDashboard userDashboard2 = new UserDashboard();
        userDashboard2.setId(userDashboard1.getId());
        assertThat(userDashboard1).isEqualTo(userDashboard2);
        userDashboard2.setId(2L);
        assertThat(userDashboard1).isNotEqualTo(userDashboard2);
        userDashboard1.setId(null);
        assertThat(userDashboard1).isNotEqualTo(userDashboard2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDashboardDTO.class);
        UserDashboardDTO userDashboardDTO1 = new UserDashboardDTO();
        userDashboardDTO1.setId(1L);
        UserDashboardDTO userDashboardDTO2 = new UserDashboardDTO();
        assertThat(userDashboardDTO1).isNotEqualTo(userDashboardDTO2);
        userDashboardDTO2.setId(userDashboardDTO1.getId());
        assertThat(userDashboardDTO1).isEqualTo(userDashboardDTO2);
        userDashboardDTO2.setId(2L);
        assertThat(userDashboardDTO1).isNotEqualTo(userDashboardDTO2);
        userDashboardDTO1.setId(null);
        assertThat(userDashboardDTO1).isNotEqualTo(userDashboardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userDashboardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userDashboardMapper.fromId(null)).isNull();
    }
}
