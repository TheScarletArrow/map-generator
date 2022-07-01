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
import ru.scarletarrow.diplomv1.repository.CountryRepository;
import ru.scarletarrow.diplomv1.repository.LocationRepository;
import ru.scarletarrow.diplomv1.service.AppUserService;
import ru.scarletarrow.diplomv1.service.MapService;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
    CommandLineRunner run(AppUserService userService,
                          AuthorRepository authorRepository,
                          BookRepository bookRepository,
                          MapService mapService,
                          CountryRepository countryRepository,
                          LocationRepository locationRepository) {
        return args -> {
            Author author1 = authorRepository.save(new Author(1L, "Anton#1"));
            Author author2 = authorRepository.save(new Author(2L, "Anton#1"));

            bookRepository.saveAll(List.of(
                    new Book("Book#1", "Publisher#1", author1),
                    new Book("Book#2", "Publisher#1", author2),
                    new Book("Book#3", "Publisher#1", author1),
                    new Book("Book#4", "Publisher#1", author1)));

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
            Arrays.stream(Location.values())
                    .forEach(location -> {
                        var location1 = locationRepository.save(new LocationClass(null, location.name()));
//                        int random = ThreadLocalRandom.current().nextInt(0, Location.values().length);
                        Arrays.stream(Countries.values()).forEach(country -> countryRepository.save(
                                new Country(null, country.name(), location1)));
                    });
            CustomMap map1 = new CustomMap(UUID.randomUUID(), "Map#1", CustomMapType.ECONOMICAL, List.of(Countries.ICELAND), user1);
            CustomMap map2 = new CustomMap(UUID.randomUUID(), "Map#2", CustomMapType.ECONOMICAL, List.of(Countries.ICELAND, Countries.IRELAND), user2);
            CustomMap map3 = new CustomMap(UUID.randomUUID(), "Map#3", CustomMapType.ECONOMICAL, List.of(Countries.ICELAND, Countries.IRELAND, Countries.UKRAINE), user3);
            CustomMap map4 = new CustomMap(UUID.randomUUID(), "Map#4", CustomMapType.ECONOMICAL, List.of(Countries.ICELAND, Countries.IRELAND, Countries.UKRAINE, Countries.RUSSIA), user4);
            CustomMap map5 = new CustomMap(UUID.randomUUID(), "Map#5", CustomMapType.POLITICAL, List.of(Countries.ICELAND, Countries.IRELAND, Countries.UKRAINE, Countries.RUSSIA, Countries.UNITEDSTATES), user2);

            mapService.saveMap(map1);
            mapService.saveMap(map2);
            mapService.saveMap(map3);
            mapService.saveMap(map4);
            mapService.saveMap(map5);

        };
    }
}
