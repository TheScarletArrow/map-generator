package ru.scarletarrow.diplomv1.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import ru.scarletarrow.diplomv1.entities.Countries;
import ru.scarletarrow.diplomv1.entities.CustomMap;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;


@Controller(value = "MapController")
public class MapController {


    RestTemplate http = new RestTemplate();

    @QueryMapping
    public CustomMap[] maps() {
        return http.getForObject("http://maps:8181/api/v1/maps", CustomMap[].class);
    }

    @QueryMapping
    public Countries[] countries(@Argument Integer limit, @Argument Integer offset) {
        if(offset==null) offset=0;

        final Countries[] response = http.getForObject("http://maps:8181/api/v1/countries", Countries[].class);
        if(limit==null) limit=response.length;
        return Arrays.stream(response).skip(offset).limit(limit).toArray(Countries[]::new);

    }


    @QueryMapping
    public CustomMap[] mapsByOwner(@Argument Long id){
        return http.getForObject("http://maps:8181/api/v1/maps/owner/" + id, CustomMap[].class);
    }
}
