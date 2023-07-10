package com.project.weatherforwindsurfersapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AdminConfig {

    @Value("${weatherbit.io.api.key}")
    private String API_KEY;
    @Value("${weatherbit.io.api.url}")
    private String URL;
    @Value("${weather.location.cities}")
    private List<String> cities;
    @Value("${weatherbit.io.max.limit.dates}")
    private long maxLimitForRequestedDates;

    public String API_KEY() {
        return API_KEY;
    }

    public String URL() {
        return URL;
    }

    public List<String> cities() {
        return cities;
    }

    public long maxLimitForFreeLicence() {
        return maxLimitForRequestedDates;
    }
}
