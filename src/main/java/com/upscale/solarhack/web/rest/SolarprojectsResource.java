package com.upscale.solarhack.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.upscale.solarhack.domain.Solarprojects;

import com.upscale.solarhack.repository.SolarprojectsRepository;
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
 * REST controller for managing Solarprojects.
 */
@RestController
@RequestMapping("/api")
public class SolarprojectsResource {

    private final Logger log = LoggerFactory.getLogger(SolarprojectsResource.class);

    @Inject
    private SolarprojectsRepository solarprojectsRepository;

    /**
     * POST  /solarprojects : Create a new solarprojects.
     *
     * @param solarprojects the solarprojects to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solarprojects, or with status 400 (Bad Request) if the solarprojects has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solarprojects")
    @Timed
    public ResponseEntity<Solarprojects> createSolarprojects(@RequestBody Solarprojects solarprojects) throws URISyntaxException {
        log.debug("REST request to save Solarprojects : {}", solarprojects);
        if (solarprojects.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("solarprojects", "idexists", "A new solarprojects cannot already have an ID")).body(null);
        }
        Solarprojects result = solarprojectsRepository.save(solarprojects);
        return ResponseEntity.created(new URI("/api/solarprojects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("solarprojects", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solarprojects : Updates an existing solarprojects.
     *
     * @param solarprojects the solarprojects to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solarprojects,
     * or with status 400 (Bad Request) if the solarprojects is not valid,
     * or with status 500 (Internal Server Error) if the solarprojects couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solarprojects")
    @Timed
    public ResponseEntity<Solarprojects> updateSolarprojects(@RequestBody Solarprojects solarprojects) throws URISyntaxException {
        log.debug("REST request to update Solarprojects : {}", solarprojects);
        if (solarprojects.getId() == null) {
            return createSolarprojects(solarprojects);
        }
        Solarprojects result = solarprojectsRepository.save(solarprojects);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("solarprojects", solarprojects.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solarprojects : get all the solarprojects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of solarprojects in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/solarprojects")
    @Timed
    public ResponseEntity<List<Solarprojects>> getAllSolarprojects(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Solarprojects");
        Page<Solarprojects> page = solarprojectsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/solarprojects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }



    /**
     * GET  /solarprojects/:id : get the "id" solarprojects.
     *
     * @param id the id of the solarprojects to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solarprojects, or with status 404 (Not Found)
     */
    @GetMapping("/solarprojects/{id}")
    @Timed
    public ResponseEntity<Solarprojects> getSolarprojects(@PathVariable Long id) {
        log.debug("REST request to get Solarprojects : {}", id);
        Solarprojects solarprojects = solarprojectsRepository.findOne(id);
        return Optional.ofNullable(solarprojects)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /solarprojects/:id : delete the "id" solarprojects.
     *
     * @param id the id of the solarprojects to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solarprojects/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolarprojects(@PathVariable Long id) {
        log.debug("REST request to delete Solarprojects : {}", id);
        solarprojectsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("solarprojects", id.toString())).build();
    }

}
