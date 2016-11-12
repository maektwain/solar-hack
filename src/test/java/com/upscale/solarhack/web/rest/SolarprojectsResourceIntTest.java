package com.upscale.solarhack.web.rest;

import com.upscale.solarhack.SolarhackApp;
import com.upscale.solarhack.domain.Solarprojects;
import com.upscale.solarhack.repository.SolarprojectsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SolarprojectsResource REST controller.
 *
 * @see SolarprojectsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SolarhackApp.class)
public class SolarprojectsResourceIntTest {

    private static final String DEFAULT_PRODUCTNAME = "AAAAA";
    private static final String UPDATED_PRODUCTNAME = "BBBBB";

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBB";

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;

    private static final Long DEFAULT_COST = 1L;
    private static final Long UPDATED_COST = 2L;

    private static final Long DEFAULT_RISKSCORE = 1L;
    private static final Long UPDATED_RISKSCORE = 2L;


    private static final Integer UPDATED_VENDOR_ID = 2;

    @Inject
    private SolarprojectsRepository solarprojectsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;


    private MockMvc restSolarprojectsMockMvc;

    private Solarprojects solarprojects;


    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SolarprojectsResource solarprojectsResource = new SolarprojectsResource();
        ReflectionTestUtils.setField(solarprojectsResource, "solarprojectsRepository", solarprojectsRepository);
        this.restSolarprojectsMockMvc = MockMvcBuilders.standaloneSetup(solarprojectsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solarprojects createEntity(EntityManager em) {
        Solarprojects solarprojects = new Solarprojects()
                .productname(DEFAULT_PRODUCTNAME)
                .productType(DEFAULT_PRODUCT_TYPE)
                .size(DEFAULT_SIZE)
                .cost(DEFAULT_COST)
                .riskscore(DEFAULT_RISKSCORE);
        return solarprojects;
    }

    @Before
    public void initTest() {
        solarprojects = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolarprojects() throws Exception {
        int databaseSizeBeforeCreate = solarprojectsRepository.findAll().size();

        // Create the Solarprojects

        restSolarprojectsMockMvc.perform(post("/api/solarprojects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(solarprojects)))
                .andExpect(status().isCreated());

        // Validate the Solarprojects in the database
        List<Solarprojects> solarprojects = solarprojectsRepository.findAll();
        assertThat(solarprojects).hasSize(databaseSizeBeforeCreate + 1);
        Solarprojects testSolarprojects = solarprojects.get(solarprojects.size() - 1);
        assertThat(testSolarprojects.getProductname()).isEqualTo(DEFAULT_PRODUCTNAME);
        assertThat(testSolarprojects.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testSolarprojects.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testSolarprojects.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testSolarprojects.getRiskscore()).isEqualTo(DEFAULT_RISKSCORE);

    }

    @Test
    @Transactional
    public void getAllSolarprojects() throws Exception {
        // Initialize the database
        solarprojectsRepository.saveAndFlush(solarprojects);

        // Get all the solarprojects
        restSolarprojectsMockMvc.perform(get("/api/solarprojects?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(solarprojects.getId().intValue())))
                .andExpect(jsonPath("$.[*].productname").value(hasItem(DEFAULT_PRODUCTNAME.toString())))
                .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
                .andExpect(jsonPath("$.[*].riskscore").value(hasItem(DEFAULT_RISKSCORE.intValue())));

    }

    @Test
    @Transactional
    public void getSolarprojects() throws Exception {
        // Initialize the database
        solarprojectsRepository.saveAndFlush(solarprojects);

        // Get the solarprojects
        restSolarprojectsMockMvc.perform(get("/api/solarprojects/{id}", solarprojects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solarprojects.getId().intValue()))
            .andExpect(jsonPath("$.productname").value(DEFAULT_PRODUCTNAME.toString()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.riskscore").value(DEFAULT_RISKSCORE.intValue()));

    }

    @Test
    @Transactional
    public void getNonExistingSolarprojects() throws Exception {
        // Get the solarprojects
        restSolarprojectsMockMvc.perform(get("/api/solarprojects/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolarprojects() throws Exception {
        // Initialize the database
        solarprojectsRepository.saveAndFlush(solarprojects);
        int databaseSizeBeforeUpdate = solarprojectsRepository.findAll().size();

        // Update the solarprojects
        Solarprojects updatedSolarprojects = solarprojectsRepository.findOne(solarprojects.getId());
        updatedSolarprojects
                .productname(UPDATED_PRODUCTNAME)
                .productType(UPDATED_PRODUCT_TYPE)
                .size(UPDATED_SIZE)
                .cost(UPDATED_COST)
                .riskscore(UPDATED_RISKSCORE);


        restSolarprojectsMockMvc.perform(put("/api/solarprojects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSolarprojects)))
                .andExpect(status().isOk());

        // Validate the Solarprojects in the database
        List<Solarprojects> solarprojects = solarprojectsRepository.findAll();
        assertThat(solarprojects).hasSize(databaseSizeBeforeUpdate);
        Solarprojects testSolarprojects = solarprojects.get(solarprojects.size() - 1);
        assertThat(testSolarprojects.getProductname()).isEqualTo(UPDATED_PRODUCTNAME);
        assertThat(testSolarprojects.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testSolarprojects.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testSolarprojects.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testSolarprojects.getRiskscore()).isEqualTo(UPDATED_RISKSCORE);
        assertThat(testSolarprojects.getVendor_id()).isEqualTo(UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    public void deleteSolarprojects() throws Exception {
        // Initialize the database
        solarprojectsRepository.saveAndFlush(solarprojects);
        int databaseSizeBeforeDelete = solarprojectsRepository.findAll().size();

        // Get the solarprojects
        restSolarprojectsMockMvc.perform(delete("/api/solarprojects/{id}", solarprojects.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Solarprojects> solarprojects = solarprojectsRepository.findAll();
        assertThat(solarprojects).hasSize(databaseSizeBeforeDelete - 1);
    }
}
