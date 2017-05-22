package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.JhipsterElasticsearchSampleApplicationApp;

import io.github.jhipster.sample.domain.Suggestion;
import io.github.jhipster.sample.repository.SuggestionRepository;
import io.github.jhipster.sample.service.SuggestionService;
import io.github.jhipster.sample.repository.search.SuggestionSearchRepository;
import io.github.jhipster.sample.service.dto.SuggestionDTO;
import io.github.jhipster.sample.service.mapper.SuggestionMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SuggestionResource REST controller.
 *
 * @see SuggestionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterElasticsearchSampleApplicationApp.class)
public class SuggestionResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private SuggestionMapper suggestionMapper;

    @Autowired
    private SuggestionService suggestionService;

    @Autowired
    private SuggestionSearchRepository suggestionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSuggestionMockMvc;

    private Suggestion suggestion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SuggestionResource suggestionResource = new SuggestionResource(suggestionService);
        this.restSuggestionMockMvc = MockMvcBuilders.standaloneSetup(suggestionResource)
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
    public static Suggestion createEntity(EntityManager em) {
        Suggestion suggestion = new Suggestion()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .creationDate(DEFAULT_CREATION_DATE);
        return suggestion;
    }

    @Before
    public void initTest() {
        suggestionSearchRepository.deleteAll();
        suggestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuggestion() throws Exception {
        int databaseSizeBeforeCreate = suggestionRepository.findAll().size();

        // Create the Suggestion
        SuggestionDTO suggestionDTO = suggestionMapper.toDto(suggestion);
        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestionDTO)))
            .andExpect(status().isCreated());

        // Validate the Suggestion in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeCreate + 1);
        Suggestion testSuggestion = suggestionList.get(suggestionList.size() - 1);
        assertThat(testSuggestion.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSuggestion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSuggestion.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSuggestion.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testSuggestion.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the Suggestion in Elasticsearch
        Suggestion suggestionEs = suggestionSearchRepository.findOne(testSuggestion.getId());
        assertThat(suggestionEs).isEqualToComparingFieldByField(testSuggestion);
    }

    @Test
    @Transactional
    public void createSuggestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suggestionRepository.findAll().size();

        // Create the Suggestion with an existing ID
        suggestion.setId(1L);
        SuggestionDTO suggestionDTO = suggestionMapper.toDto(suggestion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = suggestionRepository.findAll().size();
        // set the field null
        suggestion.setTitle(null);

        // Create the Suggestion, which fails.
        SuggestionDTO suggestionDTO = suggestionMapper.toDto(suggestion);

        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestionDTO)))
            .andExpect(status().isBadRequest());

        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = suggestionRepository.findAll().size();
        // set the field null
        suggestion.setDescription(null);

        // Create the Suggestion, which fails.
        SuggestionDTO suggestionDTO = suggestionMapper.toDto(suggestion);

        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestionDTO)))
            .andExpect(status().isBadRequest());

        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSuggestions() throws Exception {
        // Initialize the database
        suggestionRepository.saveAndFlush(suggestion);

        // Get all the suggestionList
        restSuggestionMockMvc.perform(get("/api/suggestions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suggestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSuggestion() throws Exception {
        // Initialize the database
        suggestionRepository.saveAndFlush(suggestion);

        // Get the suggestion
        restSuggestionMockMvc.perform(get("/api/suggestions/{id}", suggestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suggestion.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSuggestion() throws Exception {
        // Get the suggestion
        restSuggestionMockMvc.perform(get("/api/suggestions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuggestion() throws Exception {
        // Initialize the database
        suggestionRepository.saveAndFlush(suggestion);
        suggestionSearchRepository.save(suggestion);
        int databaseSizeBeforeUpdate = suggestionRepository.findAll().size();

        // Update the suggestion
        Suggestion updatedSuggestion = suggestionRepository.findOne(suggestion.getId());
        updatedSuggestion
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE);
        SuggestionDTO suggestionDTO = suggestionMapper.toDto(updatedSuggestion);

        restSuggestionMockMvc.perform(put("/api/suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestionDTO)))
            .andExpect(status().isOk());

        // Validate the Suggestion in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeUpdate);
        Suggestion testSuggestion = suggestionList.get(suggestionList.size() - 1);
        assertThat(testSuggestion.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSuggestion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSuggestion.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSuggestion.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testSuggestion.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the Suggestion in Elasticsearch
        Suggestion suggestionEs = suggestionSearchRepository.findOne(testSuggestion.getId());
        assertThat(suggestionEs).isEqualToComparingFieldByField(testSuggestion);
    }

    @Test
    @Transactional
    public void updateNonExistingSuggestion() throws Exception {
        int databaseSizeBeforeUpdate = suggestionRepository.findAll().size();

        // Create the Suggestion
        SuggestionDTO suggestionDTO = suggestionMapper.toDto(suggestion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSuggestionMockMvc.perform(put("/api/suggestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestionDTO)))
            .andExpect(status().isCreated());

        // Validate the Suggestion in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSuggestion() throws Exception {
        // Initialize the database
        suggestionRepository.saveAndFlush(suggestion);
        suggestionSearchRepository.save(suggestion);
        int databaseSizeBeforeDelete = suggestionRepository.findAll().size();

        // Get the suggestion
        restSuggestionMockMvc.perform(delete("/api/suggestions/{id}", suggestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean suggestionExistsInEs = suggestionSearchRepository.exists(suggestion.getId());
        assertThat(suggestionExistsInEs).isFalse();

        // Validate the database is empty
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSuggestion() throws Exception {
        // Initialize the database
        suggestionRepository.saveAndFlush(suggestion);
        suggestionSearchRepository.save(suggestion);

        // Search the suggestion
        restSuggestionMockMvc.perform(get("/api/_search/suggestions?query=id:" + suggestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suggestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suggestion.class);
        Suggestion suggestion1 = new Suggestion();
        suggestion1.setId(1L);
        Suggestion suggestion2 = new Suggestion();
        suggestion2.setId(suggestion1.getId());
        assertThat(suggestion1).isEqualTo(suggestion2);
        suggestion2.setId(2L);
        assertThat(suggestion1).isNotEqualTo(suggestion2);
        suggestion1.setId(null);
        assertThat(suggestion1).isNotEqualTo(suggestion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuggestionDTO.class);
        SuggestionDTO suggestionDTO1 = new SuggestionDTO();
        suggestionDTO1.setId(1L);
        SuggestionDTO suggestionDTO2 = new SuggestionDTO();
        assertThat(suggestionDTO1).isNotEqualTo(suggestionDTO2);
        suggestionDTO2.setId(suggestionDTO1.getId());
        assertThat(suggestionDTO1).isEqualTo(suggestionDTO2);
        suggestionDTO2.setId(2L);
        assertThat(suggestionDTO1).isNotEqualTo(suggestionDTO2);
        suggestionDTO1.setId(null);
        assertThat(suggestionDTO1).isNotEqualTo(suggestionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(suggestionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(suggestionMapper.fromId(null)).isNull();
    }
}
