package ru.scarletarrow.diplomv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.scarletarrow.diplomv1.entities.Role;

@Repository(value = "RoleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
