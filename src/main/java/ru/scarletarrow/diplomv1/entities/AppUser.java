package ru.scarletarrow.diplomv1.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "app_user", schema = "diplom")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AppUser{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String username;

    private String middleName;

    private Boolean isVerified;

    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roleList = new ArrayList<>();

    public AppUser(Long id, String name, String username, String middleName, Boolean isVerified, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.middleName = middleName;
        this.isVerified = isVerified;
        this.email = email;
    }
}
