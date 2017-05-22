package io.github.jhipster.sample.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.jhipster.sample.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.Competence.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.Competence.class.getName() + ".domains", jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.CompDomain.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.UserDashboard.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.UserDashboard.class.getName() + ".comps", jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.Project.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.Project.class.getName() + ".domains", jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.Project.class.getName() + ".authors", jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.Project.class.getName() + ".neededComps", jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.Suggestion.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.Suggestion.class.getName() + ".authors", jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.CompDomain.class.getName() + ".competences", jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.CompDomain.class.getName() + ".projects", jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.UserDashboard.class.getName() + ".projectLists", jcacheConfiguration);
            cm.createCache(io.github.jhipster.sample.domain.UserDashboard.class.getName() + ".suggestionLists", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
