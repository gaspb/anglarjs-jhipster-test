package io.github.jhipster.sample.repository.search;

import io.github.jhipster.sample.domain.UserDashboard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserDashboard entity.
 */
public interface UserDashboardSearchRepository extends ElasticsearchRepository<UserDashboard, Long> {
}
