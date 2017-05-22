package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.UserDashboard;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UserDashboard entity.
 */
@SuppressWarnings("unused")
public interface UserDashboardRepository extends JpaRepository<UserDashboard,Long> {

    @Query("select distinct userDashboard from UserDashboard userDashboard left join fetch userDashboard.comps")
    List<UserDashboard> findAllWithEagerRelationships();

    @Query("select userDashboard from UserDashboard userDashboard left join fetch userDashboard.comps where userDashboard.id =:id")
    UserDashboard findOneWithEagerRelationships(@Param("id") Long id);

}
