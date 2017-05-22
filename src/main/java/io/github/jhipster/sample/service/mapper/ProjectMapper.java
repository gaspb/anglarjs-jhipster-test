package io.github.jhipster.sample.service.mapper;

import io.github.jhipster.sample.domain.*;
import io.github.jhipster.sample.service.dto.ProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {CompetenceMapper.class, CompDomainMapper.class, UserDashboardMapper.class, })
public interface ProjectMapper extends EntityMapper <ProjectDTO, Project> {
    @Mapping(source = "domain.id", target = "domainId")
    @Mapping(source = "domain.name", target = "domainName")
    @Mapping(source = "author.id", target = "authorId")
    ProjectDTO toDto(Project project);
    @Mapping(source = "domainId", target = "domain")
    @Mapping(source = "authorId", target = "author")
    Project toEntity(ProjectDTO projectDTO);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }
}
