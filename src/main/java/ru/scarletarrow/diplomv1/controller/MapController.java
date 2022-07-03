package ru.scarletarrow.diplomv1.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import ru.scarletarrow.diplomv1.entities.Countries;
import ru.scarletarrow.diplomv1.entities.CustomMap;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


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
    public CustomMap[] mapsByOwner(@Argument Long id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", "adyurkov");
        parameters.put("password", "1234");
        String form = parameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/login"))
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var body = response.body().toString();

        String access_token = body.substring(body.indexOf("access_token") + 15, body.indexOf(",")-1);



        HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create("http://maps:8181/api/v1/maps/owner/" + id))
                .header("Authorization", "Bearer " + access_token)
                .GET().build();
        HttpResponse<?> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response1.body().toString(), CustomMap[].class);
    }
}
