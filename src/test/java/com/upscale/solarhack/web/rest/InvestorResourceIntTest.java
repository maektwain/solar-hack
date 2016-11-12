package com.upscale.solarhack.web.rest;

import com.upscale.solarhack.SolarhackApp;

import com.upscale.solarhack.domain.Investor;
import com.upscale.solarhack.repository.InvestorRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvestorResource REST controller.
 *
 * @see InvestorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SolarhackApp.class)
public class InvestorResourceIntTest {

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final Integer DEFAULT_TENURE = 1;
    private static final Integer UPDATED_TENURE = 2;

    @Inject
    private InvestorRepository investorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restInvestorMockMvc;

    private Investor investor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvestorResource investorResource = new InvestorResource();
        ReflectionTestUtils.setField(investorResource, "investorRepository", investorRepository);
        this.restInvestorMockMvc = MockMvcBuilders.standaloneSetup(investorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Investor createEntity(EntityManager em) {
        Investor investor = new Investor()
                .amount(DEFAULT_AMOUNT)
                .tenure(DEFAULT_TENURE);
        return investor;
    }

    @Before
    public void initTest() {
        investor = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvestor() throws Exception {
        int databaseSizeBeforeCreate = investorRepository.findAll().size();

        // Create the Investor

        restInvestorMockMvc.perform(post("/api/investors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(investor)))
                .andExpect(status().isCreated());

        // Validate the Investor in the database
        List<Investor> investors = investorRepository.findAll();
        assertThat(investors).hasSize(databaseSizeBeforeCreate + 1);
        Investor testInvestor = investors.get(investors.size() - 1);
        assertThat(testInvestor.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInvestor.getTenure()).isEqualTo(DEFAULT_TENURE);
    }

    @Test
    @Transactional
    public void getAllInvestors() throws Exception {
        // Initialize the database
        investorRepository.saveAndFlush(investor);

        // Get all the investors
        restInvestorMockMvc.perform(get("/api/investors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(investor.getId().intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].tenure").value(hasItem(DEFAULT_TENURE)));
    }

    @Test
    @Transactional
    public void getInvestor() throws Exception {
        // Initialize the database
        investorRepository.saveAndFlush(investor);

        // Get the investor
        restInvestorMockMvc.perform(get("/api/investors/{id}", investor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(investor.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.tenure").value(DEFAULT_TENURE));
    }

    @Test
    @Transactional
    public void getNonExistingInvestor() throws Exception {
        // Get the investor
        restInvestorMockMvc.perform(get("/api/investors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvestor() throws Exception {
        // Initialize the database
        investorRepository.saveAndFlush(investor);
        int databaseSizeBeforeUpdate = investorRepository.findAll().size();

        // Update the investor
        Investor updatedInvestor = investorRepository.findOne(investor.getId());
        updatedInvestor
                .amount(UPDATED_AMOUNT)
                .tenure(UPDATED_TENURE);

        restInvestorMockMvc.perform(put("/api/investors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedInvestor)))
                .andExpect(status().isOk());

        // Validate the Investor in the database
        List<Investor> investors = investorRepository.findAll();
        assertThat(investors).hasSize(databaseSizeBeforeUpdate);
        Investor testInvestor = investors.get(investors.size() - 1);
        assertThat(testInvestor.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInvestor.getTenure()).isEqualTo(UPDATED_TENURE);
    }

    @Test
    @Transactional
    public void deleteInvestor() throws Exception {
        // Initialize the database
        investorRepository.saveAndFlush(investor);
        int databaseSizeBeforeDelete = investorRepository.findAll().size();

        // Get the investor
        restInvestorMockMvc.perform(delete("/api/investors/{id}", investor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Investor> investors = investorRepository.findAll();
        assertThat(investors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
