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


}
