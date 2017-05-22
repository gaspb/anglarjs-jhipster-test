package io.github.jhipster.sample.repository.search;

import io.github.jhipster.sample.domain.Project;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Project entity.
 */
public interface ProjectSearchRepository extends ElasticsearchRepository<Project, Long> {
}
