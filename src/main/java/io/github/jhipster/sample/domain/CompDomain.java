package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CompDomain.
 */
@Entity
@Table(name = "comp_domain")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "compdomain")
public class CompDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "domain")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Competence> competences = new HashSet<>();

    @OneToMany(mappedBy = "domain")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> projects = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CompDomain name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Competence> getCompetences() {
        return competences;
    }

    public CompDomain competences(Set<Competence> competences) {
        this.competences = competences;
        return this;
    }

    public CompDomain addCompetence(Competence competence) {
        this.competences.add(competence);
        competence.setDomain(this);
        return this;
    }

    public CompDomain removeCompetence(Competence competence) {
        this.competences.remove(competence);
        competence.setDomain(null);
        return this;
    }

    public void setCompetences(Set<Competence> competences) {
        this.competences = competences;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public CompDomain projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public CompDomain addProject(Project project) {
        this.projects.add(project);
        project.setDomain(this);
        return this;
    }

    public CompDomain removeProject(Project project) {
        this.projects.remove(project);
        project.setDomain(null);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompDomain compDomain = (CompDomain) o;
        if (compDomain.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compDomain.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompDomain{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
