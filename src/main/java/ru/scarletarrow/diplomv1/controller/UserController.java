package ru.scarletarrow.diplomv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.scarletarrow.diplomv1.entities.AppUser;
import ru.scarletarrow.diplomv1.service.AppUserService;

@Controller
public class UserController {
    @Autowired
    AppUserService userService;

    @QueryMapping
    public Iterable<AppUser> users() {
        return userService.getUsers();
    }
}
