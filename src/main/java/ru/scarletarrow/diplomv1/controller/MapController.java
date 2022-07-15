package ru.scarletarrow.diplomv1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import ru.scarletarrow.diplomv1.entities.Countries;
import ru.scarletarrow.diplomv1.entities.CustomMap;
import ru.scarletarrow.diplomv1.exception.BadRequestException;
import ru.scarletarrow.diplomv1.exception.NotAuthorizedException;
import ru.scarletarrow.diplomv1.exception.NotFoundException;

import java.io.IOException;
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
        if (offset == null) offset = 0;

        final Countries[] response = http.getForObject("http://maps:8181/api/v1/countries", Countries[].class);
        if (limit == null) limit = response.length;
        return Arrays.stream(response).skip(offset).limit(limit).toArray(Countries[]::new);

    }


    @QueryMapping
    public CustomMap[] mapsByOwner(@Argument Long id) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody requestBody = RequestBody.create("username=adyurkov&password=1234", mediaType);
        Request request = new Request.Builder()
                .url("http://localhost:8080/login")
                .method("POST", requestBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();
        var body = responseBody.string();
        String access_token = body.substring(body.indexOf("access_token") + 15, body.indexOf(",") - 1);



        MediaType mediaType1 = MediaType.parse("text/plain");
        RequestBody requestBody1 = RequestBody.create("", mediaType1);
        Request request1 = new Request.Builder()
                .url("http://maps:8181/api/v1/maps/owner/" + id)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + access_token)
                .build();
        var response1 = client.newCall(request1).execute();
        var body1 = response1.body().string();


        ObjectMapper mapper = new ObjectMapper();

        return switch (response1.code()) {
            case 200 -> mapper.readValue(body1, CustomMap[].class);
            case 401 -> throw new NotAuthorizedException();
            case 400 -> throw new BadRequestException();
            case 404 -> throw new NotFoundException();
            case 500 -> throw new RuntimeException();
            default -> null;

        };
    }
}
