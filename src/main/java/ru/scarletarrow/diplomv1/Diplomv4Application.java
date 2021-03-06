package ru.scarletarrow.diplomv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication()
@CrossOrigin("*")
@ConfigurationPropertiesScan("ru.scarletarrow.diplomv1")
public class Diplomv4Application {

    public static void main(String[] args) {
        SpringApplication.run(Diplomv4Application.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
