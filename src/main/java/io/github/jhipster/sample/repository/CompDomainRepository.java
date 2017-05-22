package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.CompDomain;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CompDomain entity.
 */
@SuppressWarnings("unused")
public interface CompDomainRepository extends JpaRepository<CompDomain,Long> {

}
