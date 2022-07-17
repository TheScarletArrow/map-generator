package ru.scarletarrow.diplomv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.scarletarrow.diplomv1.entities.Role;
import ru.scarletarrow.diplomv1.repository.RoleRepository;

@Controller
public class GraphQLRoleController {

    @Autowired
    RoleRepository repository;

    @QueryMapping
    public Iterable<Role> roles() {
        return repository.findAll();
    }
}
