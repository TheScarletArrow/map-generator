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
import ru.scarletarrow.diplomv1.repository.MapRepository;
import ru.scarletarrow.diplomv1.service.AppUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController(value = "AppUserController")
@RequestMapping("/api/")
public class AppUserController {
    @Autowired @Qualifier("AppUserServiceImpl")
    private AppUserService userService;

    @Autowired
    private MapRepository mapRepository;
    @GetMapping("/void")
    public void add(){
        AppUser user1 = userService.getUser("adyurkov");
        AppUser user2 = userService.getUser("will");
        AppUser user3 = userService.getUser("jim");
        AppUser user4 = userService.getUser("arnold");

        user1.getMaps().addAll(mapRepository.findByOwner_Id(user1.getId()));
        user2.getMaps().addAll(mapRepository.findByOwner_Id(user2.getId()));
        user3.getMaps().addAll(mapRepository.findByOwner_Id(user3.getId()));
        user4.getMaps().addAll(mapRepository.findByOwner_Id(user4.getId()));

        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        userService.saveUser(user4);
    }

    @GetMapping("/v1/users")
    public ResponseEntity<List<AppUser>> getUsers(){
        return  ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/v1/users")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/v1/usersaddRole")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUsername form){
        userService.addRoleToUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
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
    static class RoleToUsername{
        private String userName;
        private String roleName;
    }
}
