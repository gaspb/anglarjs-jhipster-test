package io.github.jhipster.sample.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Project entity.
 */
public class ProjectDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 10, max = 35)
    private String title;

    @NotNull
    @Size(min = 30)
    private String description;

    @Lob
    private byte[] image;
    private String imageContentType;

    private LocalDate creationDate;

    private LocalDate completionDate;

    private Set<CompetenceDTO> neededComps = new HashSet<>();

    private Long domainId;

    private String domainName;

    private Long authorId;

    private String authorUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public Set<CompetenceDTO> getNeededComps() {
        return neededComps;
    }

    public void setNeededComps(Set<CompetenceDTO> competences) {
        this.neededComps = competences;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long userDashboardId) {
        this.authorId = userDashboardId;
    }

    public String getAuthorUser() {
        return authorUser;
    }

    public void setAuthorUser(String userDashboardUser) {
        this.authorUser = userDashboardUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;
        if(projectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", image='" + getImage() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            "}";
    }
}
