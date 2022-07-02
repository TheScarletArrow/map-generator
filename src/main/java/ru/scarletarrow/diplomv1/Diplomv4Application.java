package ru.scarletarrow.diplomv1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.scarletarrow.diplomv1.entities.*;
import ru.scarletarrow.diplomv1.repository.MapRepository;
import ru.scarletarrow.diplomv1.service.AppUserService;

import java.util.*;

@SpringBootApplication()
@CrossOrigin("*")
public class Diplomv4Application {

    public static void main(String[] args) {
        SpringApplication.run(Diplomv4Application.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(AppUserService userService)  {
        return args -> {


            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));


            final AppUser user1 = new AppUser(null, "John Travolta", "adyurkov", "None", true, "1234", "anton@anton.ru", new ArrayList<>());
            userService.saveUser(user1);
            final AppUser user2 = new AppUser(null, "John Travolta", "will", "None", true, "1234", "anton@anton.ru", new ArrayList<>());
            userService.saveUser(user2);
            final AppUser user3 = new AppUser(null, "John Travolta", "jim", "None", true, "1234", "anton@anton.ru", new ArrayList<>());
            userService.saveUser(user3);
            final AppUser user4 = new AppUser(null, "John Travolta", "arnold", "None", true, "1234", "anton@anton.ru", new ArrayList<>());
            userService.saveUser(user4);


            userService.addRoleToUser("adyurkov", "ROLE_USER");
            userService.addRoleToUser("will", "ROLE_MANAGER");
            userService.addRoleToUser("jim", "ROLE_ADMIN");
            userService.addRoleToUser("adyurkov", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_USER");



        };
    }
}
