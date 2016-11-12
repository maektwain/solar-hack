package com.upscale.solarhack.repository;

import com.upscale.solarhack.domain.Solarprojects;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Solarprojects entity.
 */
@SuppressWarnings("unused")
public interface SolarprojectsRepository extends JpaRepository<Solarprojects,Long> {

}
