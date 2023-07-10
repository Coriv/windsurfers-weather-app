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
    @Value("${weather.locations}")
    private List<String> locations;
    @Value("${weatherbit.io.max.limit.dates}")
    private long maxLimitForRequestedDates;

    public String API_KEY() {
        return API_KEY;
    }

    public String URL() {
        return URL;
    }

    public List<String> getLocations() {
        return locations;
    }

    public long getMaxLimitForFreeLicence() {
        return maxLimitForRequestedDates;
    }
}
