package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserDashboard.
 */
@Entity
@Table(name = "user_dashboard")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userdashboard")
public class UserDashboard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @OneToOne

    private User user;



    @OneToMany(mappedBy = "author")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> projectLists = new HashSet<>();

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Suggestion> suggestionLists = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_dashboard_comp",
               joinColumns = @JoinColumn(name="user_dashboards_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="comps_id", referencedColumnName="id"))
    private Set<Competence> comps = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public UserDashboard phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public UserDashboard user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Project> getProjectLists() {
        return projectLists;
    }

    public UserDashboard projectLists(Set<Project> projects) {
        this.projectLists = projects;
        return this;
    }

    public UserDashboard addProjectList(Project project) {
        this.projectLists.add(project);
        project.setAuthor(this);
        return this;
    }

    public UserDashboard removeProjectList(Project project) {
        this.projectLists.remove(project);
        project.setAuthor(null);
        return this;
    }

    public void setProjectLists(Set<Project> projects) {
        this.projectLists = projects;
    }

    public Set<Suggestion> getSuggestionLists() {
        return suggestionLists;
    }

    public UserDashboard suggestionLists(Set<Suggestion> suggestions) {
        this.suggestionLists = suggestions;
        return this;
    }

    public UserDashboard addSuggestionList(Suggestion suggestion) {
        this.suggestionLists.add(suggestion);
        suggestion.setAuthor(this);
        return this;
    }

    public UserDashboard removeSuggestionList(Suggestion suggestion) {
        this.suggestionLists.remove(suggestion);
        suggestion.setAuthor(null);
        return this;
    }

    public void setSuggestionLists(Set<Suggestion> suggestions) {
        this.suggestionLists = suggestions;
    }

    public Set<Competence> getComps() {
        return comps;
    }

    public UserDashboard comps(Set<Competence> competences) {
        this.comps = competences;
        return this;
    }

    public UserDashboard addComp(Competence competence) {
        this.comps.add(competence);
        return this;
    }

    public UserDashboard removeComp(Competence competence) {
        this.comps.remove(competence);
        return this;
    }

    public void setComps(Set<Competence> competences) {
        this.comps = competences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDashboard userDashboard = (UserDashboard) o;
        if (userDashboard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDashboard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDashboard{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
