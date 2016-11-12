package com.upscale.solarhack.repository;

import com.upscale.solarhack.domain.Investor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Investor entity.
 */
@SuppressWarnings("unused")
public interface InvestorRepository extends JpaRepository<Investor,Long> {

    @Query("select investor from Investor investor where investor.user.login = ?#{principal.username}")
    List<Investor> findByUserIsCurrentUser();

}
