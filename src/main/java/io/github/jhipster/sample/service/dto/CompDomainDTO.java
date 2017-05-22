package io.github.jhipster.sample.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CompDomain entity.
 */
public class CompDomainDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompDomainDTO compDomainDTO = (CompDomainDTO) o;
        if(compDomainDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compDomainDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompDomainDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
