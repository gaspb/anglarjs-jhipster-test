package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Competence;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Competence entity.
 */
@SuppressWarnings("unused")
public interface CompetenceRepository extends JpaRepository<Competence,Long> {

}
