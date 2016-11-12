package com.upscale.solarhack.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.upscale.solarhack.domain.Investor;

import com.upscale.solarhack.repository.InvestorRepository;
import com.upscale.solarhack.web.rest.util.HeaderUtil;
import com.upscale.solarhack.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Investor.
 */
@RestController
@RequestMapping("/api")
public class InvestorResource {

    private final Logger log = LoggerFactory.getLogger(InvestorResource.class);
        
    @Inject
    private InvestorRepository investorRepository;

    /**
     * POST  /investors : Create a new investor.
     *
     * @param investor the investor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new investor, or with status 400 (Bad Request) if the investor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/investors")
    @Timed
    public ResponseEntity<Investor> createInvestor(@RequestBody Investor investor) throws URISyntaxException {
        log.debug("REST request to save Investor : {}", investor);
        if (investor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("investor", "idexists", "A new investor cannot already have an ID")).body(null);
        }
        Investor result = investorRepository.save(investor);
        return ResponseEntity.created(new URI("/api/investors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("investor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /investors : Updates an existing investor.
     *
     * @param investor the investor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated investor,
     * or with status 400 (Bad Request) if the investor is not valid,
     * or with status 500 (Internal Server Error) if the investor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/investors")
    @Timed
    public ResponseEntity<Investor> updateInvestor(@RequestBody Investor investor) throws URISyntaxException {
        log.debug("REST request to update Investor : {}", investor);
        if (investor.getId() == null) {
            return createInvestor(investor);
        }
        Investor result = investorRepository.save(investor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("investor", investor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /investors : get all the investors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of investors in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/investors")
    @Timed
    public ResponseEntity<List<Investor>> getAllInvestors(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Investors");
        Page<Investor> page = investorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/investors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /investors/:id : get the "id" investor.
     *
     * @param id the id of the investor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the investor, or with status 404 (Not Found)
     */
    @GetMapping("/investors/{id}")
    @Timed
    public ResponseEntity<Investor> getInvestor(@PathVariable Long id) {
        log.debug("REST request to get Investor : {}", id);
        Investor investor = investorRepository.findOne(id);
        return Optional.ofNullable(investor)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /investors/:id : delete the "id" investor.
     *
     * @param id the id of the investor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/investors/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvestor(@PathVariable Long id) {
        log.debug("REST request to delete Investor : {}", id);
        investorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("investor", id.toString())).build();
    }

}
