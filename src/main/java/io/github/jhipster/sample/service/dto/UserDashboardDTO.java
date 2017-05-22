package io.github.jhipster.sample.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserDashboard entity.
 */
public class UserDashboardDTO implements Serializable {

    private Long id;

    private String phone;

    private Long userId;

    private String userLogin;

    private Set<CompetenceDTO> comps = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Set<CompetenceDTO> getComps() {
        return comps;
    }

    public void setComps(Set<CompetenceDTO> competences) {
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

        UserDashboardDTO userDashboardDTO = (UserDashboardDTO) o;
        if(userDashboardDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDashboardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDashboardDTO{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
