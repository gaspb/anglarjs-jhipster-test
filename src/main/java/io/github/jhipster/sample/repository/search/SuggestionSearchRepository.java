package io.github.jhipster.sample.repository.search;

import io.github.jhipster.sample.domain.Suggestion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Suggestion entity.
 */
public interface SuggestionSearchRepository extends ElasticsearchRepository<Suggestion, Long> {
}
