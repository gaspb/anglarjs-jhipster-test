package io.github.jhipster.sample.repository.search;

import io.github.jhipster.sample.domain.CompDomain;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CompDomain entity.
 */
public interface CompDomainSearchRepository extends ElasticsearchRepository<CompDomain, Long> {
}
