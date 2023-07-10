package com.project.weatherforwindsurfersapp.service;

import com.project.weatherforwindsurfersapp.config.AdminConfig;
import com.project.weatherforwindsurfersapp.externalDto.WeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ExternalDataService {

    private final RestTemplate restTemplate;
    private final AdminConfig config;

    public WeatherData fetchWeatherDataByCity(String cityName) {
        return restTemplate.getForObject(prepareUri(cityName), WeatherData.class);
    }

    private URI prepareUri(String cityName) {
        return UriComponentsBuilder.fromHttpUrl(config.URL())
                .queryParam("city", cityName)
                .queryParam("key", config.API_KEY())
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }
}
