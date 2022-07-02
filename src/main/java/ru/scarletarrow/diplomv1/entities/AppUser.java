package ru.scarletarrow.diplomv1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
//@Table(name = "app_user", schema = "diplom")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@ToString(exclude = "maps")
public class AppUser {
    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", middleName='" + middleName + '\'' +
                ", isVerified=" + isVerified +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String username;

    private String middleName;

    private Boolean isVerified;

    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<CustomMap> maps = new ArrayList<>();
    public AppUser(Long id, String name, String username, String middleName, Boolean isVerified, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.middleName = middleName;
        this.isVerified = isVerified;
        this.email = email;
    }

    public AppUser(Long id, String name, String username, String middleName, Boolean isVerified, String password, String email, Collection<Role> roleList) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.middleName = middleName;
        this.isVerified = isVerified;
        this.password = password;
        this.email = email;
        this.roles = roleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser user = (AppUser) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
