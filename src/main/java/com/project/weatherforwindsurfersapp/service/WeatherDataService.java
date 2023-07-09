package com.project.weatherforwindsurfersapp.service;

import com.project.weatherforwindsurfersapp.config.AdminConfig;
import com.project.weatherforwindsurfersapp.dto.LocationDto;
import com.project.weatherforwindsurfersapp.dto.WeatherData;
import com.project.weatherforwindsurfersapp.dto.DailyDetails;
import com.project.weatherforwindsurfersapp.exception.DailyDetailsDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherDataService {

    private final AdminConfig config;
    private final ExternalDataService externalDataService;

    private List<WeatherData> prepareWeatherDataForCities() {
        return config.getCities().stream()
                .map(externalDataService::fetchWeatherDataByCity)
                .collect(Collectors.toList());
    }
//        List<WeatherData> datas = new ArrayList<>();
//        var cos = config.getCities();
//        for (String s : cos) {
//            var ad = externalDataService.fetchWeatherDataByCity(s);
//            datas.add(ad);
//        }
//        return datas;

    public LocationDto findBestLocationForGivenDate(LocalDate date) throws DailyDetailsDoesNotExistException {
        return prepareWeatherDataForCities().stream()
                .map((weather) -> prepareLocation(weather, date))
                .filter(location -> location.getTotalScore() > -1)
                .max(Comparator.comparingDouble(LocationDto::getTotalScore))
                .orElse(null);
    }

    private LocationDto prepareLocation(WeatherData weatherData, LocalDate date) throws DailyDetailsDoesNotExistException {
        var weatherDetails = fetchDailyDetailsOnGivenDate(weatherData, date);
        var score = calculateWeatherScore(weatherDetails);
        return LocationDto.builder()
                .citiName(weatherData.getCityName())
                .totalScore(score)
                .averageTemp(weatherDetails.getAverageTemp())
                .windSpeed(weatherDetails.getWindSpeed())
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
