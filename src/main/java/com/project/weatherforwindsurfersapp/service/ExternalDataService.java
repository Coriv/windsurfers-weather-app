package com.project.weatherforwindsurfersapp.service;

import com.project.weatherforwindsurfersapp.config.AdminConfig;
import com.project.weatherforwindsurfersapp.dto.WeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalDataService {

    private final RestTemplate restTemplate;
    private final AdminConfig config;

    public WeatherData fetchWeatherDataByCity(String cityName) {

        try{
            log.info("Rest call for WeatherData for parameter: " + cityName);
            var weatherData = restTemplate.getForObject(prepareUri(cityName), WeatherData.class);
            log.info("Retrieved new WeatherData object for parameter: " + cityName);
            return weatherData;
        } catch (RestClientException e) {
            log.error("RestClint Exception for parameter: " + cityName, e.getMessage());
        }
        return null;
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
