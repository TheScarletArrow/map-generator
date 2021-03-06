package ru.scarletarrow.diplomv1.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.scarletarrow.diplomv1.entities.AppUser;
import ru.scarletarrow.diplomv1.entities.Role;
import ru.scarletarrow.diplomv1.repository.RoleRepository;
import ru.scarletarrow.diplomv1.security.JwtConfig;
import ru.scarletarrow.diplomv1.security.JwtSecretKey;
import ru.scarletarrow.diplomv1.service.AppUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController(value = "AppUserController")
@RequestMapping("/api/")
public class UserRestController {
    @Autowired
    @Qualifier("AppUserServiceImpl")
    private AppUserService userService;

    @Autowired
    private RoleRepository roleRepository;
    private final JwtConfig jwtConfig;

    public UserRestController(JwtConfig jwtConfig, JwtSecretKey jwtSecretKey) {
        this.jwtConfig = jwtConfig;
    }

    @GetMapping("/void1")
    public void voidMethod() {

        userService.saveRole(new Role(null, "ROLE_USER"));
        userService.saveRole(new Role(null, "ROLE_MANAGER"));
        userService.saveRole(new Role(null, "ROLE_ADMIN"));
        userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));


        final AppUser user1 = new AppUser(null, "John Travolta", "adyurkov", "None",  "1234", "anton@anton.ru", new ArrayList<>());
        userService.saveUser(user1);
        final AppUser user2 = new AppUser(null, "John Travolta", "will", "None",  "1234", "anton@anton.ru", new ArrayList<>());
        userService.saveUser(user2);
        final AppUser user3 = new AppUser(null, "John Travolta", "jim", "None",  "1234", "anton@anton.ru", new ArrayList<>());
        userService.saveUser(user3);
        final AppUser user4 = new AppUser(null, "John Travolta", "arnold", "None",  "1234", "anton@anton.ru", new ArrayList<>());
        userService.saveUser(user4);


        userService.addRoleToUser("adyurkov", "ROLE_USER");
        userService.addRoleToUser("will", "ROLE_MANAGER");
        userService.addRoleToUser("jim", "ROLE_ADMIN");
        userService.addRoleToUser("adyurkov", "ROLE_SUPER_ADMIN");
        userService.addRoleToUser("arnold", "ROLE_ADMIN");
        userService.addRoleToUser("arnold", "ROLE_USER");
    }
    @PostMapping("/register/test")
    public ResponseEntity<?> register(){
        AppUser user = new AppUser(
                null,
                "Anton",
                "AAAN",
                "Yurkov",
                "1212",
                "anton20001701@yandex.ru",
                List.of(roleRepository.findByName("ROLE_USER")));
        HashMap<String, String> map = new HashMap<>();
        map.put("error", "User with this username already exists");
        if(userService.existsByUsername(user.getUsername())){
            return ResponseEntity.status(BAD_REQUEST).body(map);
        }
        return ResponseEntity.status(CREATED).body(userService.saveUser(user));

    }

    @PostMapping("/v1/users/register")
    public ResponseEntity<AppUser> registerUser(@RequestBody AppUser user) {
        userService.saveUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }
    @GetMapping("/v1/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/v1/users/{id}")
    public ResponseEntity<AppUser> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }
    @PostMapping("/v1/users")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/v1/usersaddRole")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUsername form) {
        userService.addRoleToUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                String username = decodedJWT.getSubject();

                AppUser user = userService.getUser(username);
                Date date = new Date();
                DateTime today = new DateTime(date).plusDays(1);
                DateTime dateTime = new DateTime(date).plusHours(3);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(dateTime.toDate())
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> tokens = new HashMap<>();
                tokens.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @Data
    static class RoleToUsername {
        private String userName;
        private String roleName;
    }
}
