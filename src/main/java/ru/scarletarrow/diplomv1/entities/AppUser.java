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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
                ", isAccountNonExpired=" + isAccountNonExpired +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", isCredentialsNonExpired=" + isCredentialsNonExpired +
                ", isEnabled=" + isEnabled +
                ", roles=" + roles +
                ", maps=" + maps +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;

    private String middleName;

    private Boolean isVerified;

    private String password;
    private String email;
    private  Boolean isAccountNonExpired;
    private  Boolean isAccountNonLocked;
    private  Boolean isCredentialsNonExpired;
    private  Boolean isEnabled;
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
        this.isAccountNonExpired=false;
        this.isAccountNonLocked=false;
        this.isCredentialsNonExpired=false;
        this.isEnabled=true;
    }

    public AppUser(Long id, String name, String username,  String middleName, String password, String email, Collection<Role> roleList) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.middleName = middleName;
        this.password = password;
        this.email = email;
        this.roles = roleList;
        this.isVerified = false;
        this.isAccountNonExpired=false;
        this.isAccountNonLocked=false;
        this.isCredentialsNonExpired=false;
        this.isEnabled=false;
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
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + middleName.hashCode();
        result = 31 * result + isVerified.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + isAccountNonExpired.hashCode();
        result = 31 * result + isAccountNonLocked.hashCode();
        result = 31 * result + isCredentialsNonExpired.hashCode();
        result = 31 * result + isEnabled.hashCode();
        result = 31 * result + roles.hashCode();
        result = 31 * result + maps.hashCode();
        return result;
    }
}
