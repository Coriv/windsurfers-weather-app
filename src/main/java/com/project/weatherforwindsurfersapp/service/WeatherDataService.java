package com.project.weatherforwindsurfersapp.service;

import com.project.weatherforwindsurfersapp.config.AdminConfig;
import com.project.weatherforwindsurfersapp.domain.LocationWeatherDto;
import com.project.weatherforwindsurfersapp.exception.DailyDetailsDoesNotExistException;
import com.project.weatherforwindsurfersapp.externalDto.DailyDetails;
import com.project.weatherforwindsurfersapp.externalDto.WeatherData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherDataService {
    private final ExternalDataService externalDataService;
    private final AdminConfig adminConfig;
    public LocationWeatherDto findBestLocationForGivenDate(LocalDate date) {
        return prepareWeatherDataForCities().stream()
                .map((weather) -> prepareSearchedLocationDtos(weather, date))
                .filter(city -> areTheConditionsInRange(city.getWindSpeed(), city.getAverageTemp()))
                .max(Comparator.comparingDouble(LocationWeatherDto::getTotalScore))
                .orElse(null);
    }

    private List<WeatherData> prepareWeatherDataForCities() {
        return adminConfig.getLocations().stream()
                .map(externalDataService::fetchWeatherDataByCity)
                .collect(Collectors.toList());
    }

    private LocationWeatherDto prepareSearchedLocationDtos(WeatherData weatherData, LocalDate date) {
        var weatherDetails = fetchDailyDetailsOnGivenDate(weatherData, date);
        var score = calculateWeatherScore(weatherDetails);
        return LocationWeatherDto.builder()
                .citiName(weatherData.getCityName())
                .totalScore(score)
                .averageTemp(weatherDetails.getAverageTemp())
                .windSpeed(weatherDetails.getWindSpeed())
                .latitude(Double.valueOf(weatherData.getLatitude()))
                .longitude(Double.valueOf(weatherData.getLongitude()))
                .date(LocalDate.parse(weatherDetails.getValidDate()))
                .build();
    }

    private DailyDetails fetchDailyDetailsOnGivenDate(WeatherData weatherData, LocalDate localDate) throws DailyDetailsDoesNotExistException {
        return weatherData.getDailyDetailsList().stream()
                .filter(dailyDetails -> LocalDate.parse(dailyDetails.getValidDate()).equals(localDate))
                .findAny()
                .orElseThrow(DailyDetailsDoesNotExistException::new);
    }

    private double calculateWeatherScore(DailyDetails weather) {
        var windSpeed = weather.getWindSpeed();
        var avgTemp = weather.getAverageTemp();
        return areTheConditionsInRange(windSpeed, avgTemp) == false ?
                -1 : windSpeed * 3 + avgTemp;
    }

    private boolean areTheConditionsInRange(double windSpeed, double avgTemp) {
        return windSpeed >= 5 && windSpeed <= 18 && avgTemp >= 5 && avgTemp <= 35;
    }
}
