package ru.scarletarrow.diplomv1.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
//@Table(name = "app_user", schema = "diplom")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AppUser {
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

    @OneToMany
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
}
