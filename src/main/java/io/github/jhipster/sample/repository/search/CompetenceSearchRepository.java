package io.github.jhipster.sample.repository.search;

import io.github.jhipster.sample.domain.Competence;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Competence entity.
 */
public interface CompetenceSearchRepository extends ElasticsearchRepository<Competence, Long> {
}
