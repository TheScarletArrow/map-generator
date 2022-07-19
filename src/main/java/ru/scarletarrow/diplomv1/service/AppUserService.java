package ru.scarletarrow.diplomv1.service;

import ru.scarletarrow.diplomv1.entities.AppUser;
import ru.scarletarrow.diplomv1.entities.Role;

import java.util.List;
import java.util.Optional;

public interface AppUserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String user, String role);
    AppUser getUser(String username);
    List<AppUser> getUsers();
    AppUser getUserById(Long id);
    boolean existsByUsername(String username);
}
