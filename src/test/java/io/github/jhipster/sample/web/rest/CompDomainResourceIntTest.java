package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.JhipsterElasticsearchSampleApplicationApp;

import io.github.jhipster.sample.domain.CompDomain;
import io.github.jhipster.sample.repository.CompDomainRepository;
import io.github.jhipster.sample.service.CompDomainService;
import io.github.jhipster.sample.repository.search.CompDomainSearchRepository;
import io.github.jhipster.sample.service.dto.CompDomainDTO;
import io.github.jhipster.sample.service.mapper.CompDomainMapper;
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
 * Test class for the CompDomainResource REST controller.
 *
 * @see CompDomainResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterElasticsearchSampleApplicationApp.class)
public class CompDomainResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CompDomainRepository compDomainRepository;

    @Autowired
    private CompDomainMapper compDomainMapper;

    @Autowired
    private CompDomainService compDomainService;

    @Autowired
    private CompDomainSearchRepository compDomainSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompDomainMockMvc;

    private CompDomain compDomain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompDomainResource compDomainResource = new CompDomainResource(compDomainService);
        this.restCompDomainMockMvc = MockMvcBuilders.standaloneSetup(compDomainResource)
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
    public static CompDomain createEntity(EntityManager em) {
        CompDomain compDomain = new CompDomain()
            .name(DEFAULT_NAME);
        return compDomain;
    }

    @Before
    public void initTest() {
        compDomainSearchRepository.deleteAll();
        compDomain = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompDomain() throws Exception {
        int databaseSizeBeforeCreate = compDomainRepository.findAll().size();

        // Create the CompDomain
        CompDomainDTO compDomainDTO = compDomainMapper.toDto(compDomain);
        restCompDomainMockMvc.perform(post("/api/comp-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compDomainDTO)))
            .andExpect(status().isCreated());

        // Validate the CompDomain in the database
        List<CompDomain> compDomainList = compDomainRepository.findAll();
        assertThat(compDomainList).hasSize(databaseSizeBeforeCreate + 1);
        CompDomain testCompDomain = compDomainList.get(compDomainList.size() - 1);
        assertThat(testCompDomain.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the CompDomain in Elasticsearch
        CompDomain compDomainEs = compDomainSearchRepository.findOne(testCompDomain.getId());
        assertThat(compDomainEs).isEqualToComparingFieldByField(testCompDomain);
    }

    @Test
    @Transactional
    public void createCompDomainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compDomainRepository.findAll().size();

        // Create the CompDomain with an existing ID
        compDomain.setId(1L);
        CompDomainDTO compDomainDTO = compDomainMapper.toDto(compDomain);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompDomainMockMvc.perform(post("/api/comp-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compDomainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CompDomain> compDomainList = compDomainRepository.findAll();
        assertThat(compDomainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = compDomainRepository.findAll().size();
        // set the field null
        compDomain.setName(null);

        // Create the CompDomain, which fails.
        CompDomainDTO compDomainDTO = compDomainMapper.toDto(compDomain);

        restCompDomainMockMvc.perform(post("/api/comp-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compDomainDTO)))
            .andExpect(status().isBadRequest());

        List<CompDomain> compDomainList = compDomainRepository.findAll();
        assertThat(compDomainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompDomains() throws Exception {
        // Initialize the database
        compDomainRepository.saveAndFlush(compDomain);

        // Get all the compDomainList
        restCompDomainMockMvc.perform(get("/api/comp-domains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compDomain.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCompDomain() throws Exception {
        // Initialize the database
        compDomainRepository.saveAndFlush(compDomain);

        // Get the compDomain
        restCompDomainMockMvc.perform(get("/api/comp-domains/{id}", compDomain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compDomain.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompDomain() throws Exception {
        // Get the compDomain
        restCompDomainMockMvc.perform(get("/api/comp-domains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompDomain() throws Exception {
        // Initialize the database
        compDomainRepository.saveAndFlush(compDomain);
        compDomainSearchRepository.save(compDomain);
        int databaseSizeBeforeUpdate = compDomainRepository.findAll().size();

        // Update the compDomain
        CompDomain updatedCompDomain = compDomainRepository.findOne(compDomain.getId());
        updatedCompDomain
            .name(UPDATED_NAME);
        CompDomainDTO compDomainDTO = compDomainMapper.toDto(updatedCompDomain);

        restCompDomainMockMvc.perform(put("/api/comp-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compDomainDTO)))
            .andExpect(status().isOk());

        // Validate the CompDomain in the database
        List<CompDomain> compDomainList = compDomainRepository.findAll();
        assertThat(compDomainList).hasSize(databaseSizeBeforeUpdate);
        CompDomain testCompDomain = compDomainList.get(compDomainList.size() - 1);
        assertThat(testCompDomain.getName()).isEqualTo(UPDATED_NAME);

        // Validate the CompDomain in Elasticsearch
        CompDomain compDomainEs = compDomainSearchRepository.findOne(testCompDomain.getId());
        assertThat(compDomainEs).isEqualToComparingFieldByField(testCompDomain);
    }

    @Test
    @Transactional
    public void updateNonExistingCompDomain() throws Exception {
        int databaseSizeBeforeUpdate = compDomainRepository.findAll().size();

        // Create the CompDomain
        CompDomainDTO compDomainDTO = compDomainMapper.toDto(compDomain);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompDomainMockMvc.perform(put("/api/comp-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compDomainDTO)))
            .andExpect(status().isCreated());

        // Validate the CompDomain in the database
        List<CompDomain> compDomainList = compDomainRepository.findAll();
        assertThat(compDomainList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompDomain() throws Exception {
        // Initialize the database
        compDomainRepository.saveAndFlush(compDomain);
        compDomainSearchRepository.save(compDomain);
        int databaseSizeBeforeDelete = compDomainRepository.findAll().size();

        // Get the compDomain
        restCompDomainMockMvc.perform(delete("/api/comp-domains/{id}", compDomain.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean compDomainExistsInEs = compDomainSearchRepository.exists(compDomain.getId());
        assertThat(compDomainExistsInEs).isFalse();

        // Validate the database is empty
        List<CompDomain> compDomainList = compDomainRepository.findAll();
        assertThat(compDomainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCompDomain() throws Exception {
        // Initialize the database
        compDomainRepository.saveAndFlush(compDomain);
        compDomainSearchRepository.save(compDomain);

        // Search the compDomain
        restCompDomainMockMvc.perform(get("/api/_search/comp-domains?query=id:" + compDomain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compDomain.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompDomain.class);
        CompDomain compDomain1 = new CompDomain();
        compDomain1.setId(1L);
        CompDomain compDomain2 = new CompDomain();
        compDomain2.setId(compDomain1.getId());
        assertThat(compDomain1).isEqualTo(compDomain2);
        compDomain2.setId(2L);
        assertThat(compDomain1).isNotEqualTo(compDomain2);
        compDomain1.setId(null);
        assertThat(compDomain1).isNotEqualTo(compDomain2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompDomainDTO.class);
        CompDomainDTO compDomainDTO1 = new CompDomainDTO();
        compDomainDTO1.setId(1L);
        CompDomainDTO compDomainDTO2 = new CompDomainDTO();
        assertThat(compDomainDTO1).isNotEqualTo(compDomainDTO2);
        compDomainDTO2.setId(compDomainDTO1.getId());
        assertThat(compDomainDTO1).isEqualTo(compDomainDTO2);
        compDomainDTO2.setId(2L);
        assertThat(compDomainDTO1).isNotEqualTo(compDomainDTO2);
        compDomainDTO1.setId(null);
        assertThat(compDomainDTO1).isNotEqualTo(compDomainDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(compDomainMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(compDomainMapper.fromId(null)).isNull();
    }
}
