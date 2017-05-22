package io.github.jhipster.sample.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Competence.
 */
@Entity
@Table(name = "competence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "competence")
public class Competence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private CompDomain domain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Competence name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompDomain getDomain() {
        return domain;
    }

    public Competence domain(CompDomain compDomain) {
        this.domain = compDomain;
        return this;
    }

    public void setDomain(CompDomain compDomain) {
        this.domain = compDomain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Competence competence = (Competence) o;
        if (competence.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Competence{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
