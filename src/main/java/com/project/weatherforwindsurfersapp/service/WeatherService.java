package com.project.weatherforwindsurfersapp.service;

import com.project.weatherforwindsurfersapp.config.AdminConfig;
import com.project.weatherforwindsurfersapp.dto.WeatherForCity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    private final AdminConfig adminConfig;

    public WeatherForCity fetchWeatherDataByCity(String cityName) {
        return restTemplate.getForObject(prepareUri(cityName), WeatherForCity.class);
    }

    private URI prepareUri(String cityName) {
        return UriComponentsBuilder.fromHttpUrl(adminConfig.getURL())
                .queryParam("city", cityName)
                .queryParam("key", adminConfig.getAPI_KEY())
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }
}
