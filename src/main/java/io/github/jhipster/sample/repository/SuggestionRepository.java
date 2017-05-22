package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Suggestion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Suggestion entity.
 */
@SuppressWarnings("unused")
public interface SuggestionRepository extends JpaRepository<Suggestion,Long> {

}
