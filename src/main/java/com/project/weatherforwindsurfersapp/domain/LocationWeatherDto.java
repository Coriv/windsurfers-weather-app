package com.project.weatherforwindsurfersapp.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LocationWeatherDto {
    private String citiName;
    private double totalScore;
    private double averageTemp;
    private double windSpeed;
    private double latitude;
    private double longitude;
    private LocalDate date;
}
