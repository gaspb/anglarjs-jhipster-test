package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.*;
import io.github.jhipster.sample.service.dto.UserDashboardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserDashboard and its DTO UserDashboardDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CompetenceMapper.class, })
public interface UserDashboardMapper extends EntityMapper <UserDashboardDTO, UserDashboard> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    UserDashboardDTO toDto(UserDashboard userDashboard);
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "projectLists", ignore = true)
    @Mapping(target = "suggestionLists", ignore = true)
    UserDashboard toEntity(UserDashboardDTO userDashboardDTO);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default UserDashboard fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDashboard userDashboard = new UserDashboard();
        userDashboard.setId(id);
        return userDashboard;
    }
}
