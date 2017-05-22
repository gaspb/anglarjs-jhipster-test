package io.github.jhipster.sample.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Competence entity.
 */
public class CompetenceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Long domainId;

    private String domainName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long compDomainId) {
        this.domainId = compDomainId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String compDomainName) {
        this.domainName = compDomainName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetenceDTO competenceDTO = (CompetenceDTO) o;
        if(competenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetenceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
