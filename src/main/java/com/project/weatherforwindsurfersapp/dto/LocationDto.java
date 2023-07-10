package com.project.weatherforwindsurfersapp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LocationDto {
    private String citiName;
    private double totalScore;
    private double averageTemp;
    private double windSpeed;
    private double latitude;
    private double longitude;
    private LocalDate date;
}
