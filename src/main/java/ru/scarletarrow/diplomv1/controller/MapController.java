package ru.scarletarrow.diplomv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletarrow.diplomv1.entities.CustomMap;
import ru.scarletarrow.diplomv1.service.MapService;

import java.util.List;

@Controller(value = "MapController")
public class MapController {

    @Autowired
    private MapService mapService;

    @QueryMapping
    public Iterable<CustomMap> maps() {
        return mapService.getMaps();
    }
}
