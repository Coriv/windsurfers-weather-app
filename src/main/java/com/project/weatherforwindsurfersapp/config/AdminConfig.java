package com.project.weatherforwindsurfersapp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class AdminConfig {

    @Value("${weatherbit.io.api.key}")
    private String API_KEY;
    @Value("${weatherbit.io.api.url}")
    private String URL;
}
