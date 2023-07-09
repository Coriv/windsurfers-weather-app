package com.project.weatherforwindsurfersapp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class AdminConfig {

    @Value("${weatherbit.io.api.key}")
    private String API_KEY;
    @Value("${weatherbit.io.api.url}")
    private String URL;
    @Value("${weather.location.cities}")
    private List<String> cities;
}
