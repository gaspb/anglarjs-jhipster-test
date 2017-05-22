package io.github.jhipster.sample.service;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.sample.domain.*;
import io.github.jhipster.sample.repository.*;
import io.github.jhipster.sample.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final CompDomainRepository compDomainRepository;

    private final CompDomainSearchRepository compDomainSearchRepository;

    private final CompetenceRepository competenceRepository;

    private final CompetenceSearchRepository competenceSearchRepository;

    private final ProjectRepository projectRepository;

    private final ProjectSearchRepository projectSearchRepository;

    private final SuggestionRepository suggestionRepository;

    private final SuggestionSearchRepository suggestionSearchRepository;

    private final UserDashboardRepository userDashboardRepository;

    private final UserDashboardSearchRepository userDashboardSearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchIndexService(
        UserRepository userRepository,
        UserSearchRepository userSearchRepository,
        CompDomainRepository compDomainRepository,
        CompDomainSearchRepository compDomainSearchRepository,
        CompetenceRepository competenceRepository,
        CompetenceSearchRepository competenceSearchRepository,
        ProjectRepository projectRepository,
        ProjectSearchRepository projectSearchRepository,
        SuggestionRepository suggestionRepository,
        SuggestionSearchRepository suggestionSearchRepository,
        UserDashboardRepository userDashboardRepository,
        UserDashboardSearchRepository userDashboardSearchRepository,
        ElasticsearchTemplate elasticsearchTemplate) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.compDomainRepository = compDomainRepository;
        this.compDomainSearchRepository = compDomainSearchRepository;
        this.competenceRepository = competenceRepository;
        this.competenceSearchRepository = competenceSearchRepository;
        this.projectRepository = projectRepository;
        this.projectSearchRepository = projectSearchRepository;
        this.suggestionRepository = suggestionRepository;
        this.suggestionSearchRepository = suggestionSearchRepository;
        this.userDashboardRepository = userDashboardRepository;
        this.userDashboardSearchRepository = userDashboardSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Async
    @Timed
    public void reindexAll() {
        reindexForClass(CompDomain.class, compDomainRepository, compDomainSearchRepository);
        reindexForClass(Competence.class, competenceRepository, competenceSearchRepository);
        reindexForClass(Project.class, projectRepository, projectSearchRepository);
        reindexForClass(Suggestion.class, suggestionRepository, suggestionSearchRepository);
        reindexForClass(UserDashboard.class, userDashboardRepository, userDashboardSearchRepository);
        reindexForClass(User.class, userRepository, userSearchRepository);

        log.info("Elasticsearch: Successfully performed reindexing");
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            try {
                Method m = jpaRepository.getClass().getMethod("findAllWithEagerRelationships");
                elasticsearchRepository.save((List<T>) m.invoke(jpaRepository));
            } catch (Exception e) {
                elasticsearchRepository.save(jpaRepository.findAll());
            }
        }
        log.info("Elasticsearch: Indexed all rows for " + entityClass.getSimpleName());
    }
}
