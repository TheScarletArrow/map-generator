package ru.scarletarrow.diplomv1.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import ru.scarletarrow.diplomv1.entities.Countries;
import ru.scarletarrow.diplomv1.entities.CustomMap;


@Controller(value = "MapController")
public class MapController {


    RestTemplate http = new RestTemplate();

    @QueryMapping
    public CustomMap[] maps() {
        return http.getForObject("http://maps:8181/api/v1/maps", CustomMap[].class);
    }

    @QueryMapping
    public Countries[] countries(){
        return http.getForObject("http://maps:8181/api/v1/countries", Countries[].class);
    }


    @QueryMapping
    public CustomMap[] mapsByOwner(@Argument Long id){
        return http.getForObject("http://maps:8181/api/v1/maps/owner/" + id, CustomMap[].class);
    }
}
