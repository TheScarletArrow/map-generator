package ru.scarletarrow.diplomv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.scarletarrow.diplomv1.entities.Role;
import ru.scarletarrow.diplomv1.service.AppUserService;

import java.net.URI;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {
    @Autowired
    private  AppUserService userService;

    public RoleController(AppUserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/roles").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
}
