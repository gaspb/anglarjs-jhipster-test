package io.github.jhipster.sample.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Task entity.
 * @author The JHipster team.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10, max = 35)
    @Column(name = "title", length = 35, nullable = false)
    private String title;

    @NotNull
    @Size(min = 30)
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_needed_comp",
               joinColumns = @JoinColumn(name="projects_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="needed_comps_id", referencedColumnName="id"))
    private Set<Competence> neededComps = new HashSet<>();

    @ManyToOne
    private CompDomain domain;

    @ManyToOne
    private UserDashboard author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Project title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public Project image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Project imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Project creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public Project completionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
        return this;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public Set<Competence> getNeededComps() {
        return neededComps;
    }

    public Project neededComps(Set<Competence> competences) {
        this.neededComps = competences;
        return this;
    }

    public Project addNeededComp(Competence competence) {
        this.neededComps.add(competence);
        return this;
    }

    public Project removeNeededComp(Competence competence) {
        this.neededComps.remove(competence);
        return this;
    }

    public void setNeededComps(Set<Competence> competences) {
        this.neededComps = competences;
    }

    public CompDomain getDomain() {
        return domain;
    }

    public Project domain(CompDomain compDomain) {
        this.domain = compDomain;
        return this;
    }

    public void setDomain(CompDomain compDomain) {
        this.domain = compDomain;
    }

    public UserDashboard getAuthor() {
        return author;
    }

    public Project author(UserDashboard userDashboard) {
        this.author = userDashboard;
        return this;
    }

    public void setAuthor(UserDashboard userDashboard) {
        this.author = userDashboard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            "}";
    }
}
