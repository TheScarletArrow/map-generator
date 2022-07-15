package ru.scarletarrow.diplomv1.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.scarletarrow.diplomv1.entities.AppUser;
import ru.scarletarrow.diplomv1.entities.Role;
import ru.scarletarrow.diplomv1.repository.AppUserRepository;
import ru.scarletarrow.diplomv1.repository.RoleRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service(value = "AppUserServiceImpl")
@Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    @Autowired
    @Qualifier("AppUserRepository")
    private AppUserRepository appUserRepository;
    @Autowired
    @Qualifier("RoleRepository")
    private  RoleRepository roleRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;



    @Override
    @Transactional

    public AppUser saveUser(AppUser user) {
        log.info("saved user " + user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("username="+user.getUsername());
        log.info("password="+user.getPassword());
        return appUserRepository.save(user);
    }

    @Override @Transactional

    public Role saveRole(Role role) {
        log.info("saved role " + role);
        return roleRepository.save(role);
    }

    @Override @Transactional

    public void addRoleToUser(String username, String rolename) {
        AppUser user = appUserRepository.findByUsername(username);
        Role role = roleRepository.findByName(rolename);
        user.getRoles().add(role);
    }

    @Override @Transactional

    public AppUser getUser(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override @Transactional

    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    @Override @Transactional

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
