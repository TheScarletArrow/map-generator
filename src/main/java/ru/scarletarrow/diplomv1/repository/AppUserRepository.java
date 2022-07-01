package ru.scarletarrow.diplomv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.scarletarrow.diplomv1.entities.AppUser;

@Repository(value = "AppUserRepository")
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

}
