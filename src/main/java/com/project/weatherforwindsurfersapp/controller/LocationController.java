package com.project.weatherforwindsurfersapp.controller;

import com.project.weatherforwindsurfersapp.dto.LocationDto;
import com.project.weatherforwindsurfersapp.exception.AvailableRangeExceededException;
import com.project.weatherforwindsurfersapp.exception.DailyDetailsDoesNotExistException;
import com.project.weatherforwindsurfersapp.service.ValidateDateRangeService;
import com.project.weatherforwindsurfersapp.service.WeatherDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/best-location")
@RequiredArgsConstructor
public class LocationController {

    private final WeatherDataService weatherDataService;
    private final ValidateDateRangeService validationService;
    @GetMapping("/{date}")
    public ResponseEntity<LocationDto> fetchBestLocationForGivenDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws DailyDetailsDoesNotExistException, AvailableRangeExceededException {

        validationService.checkIfDateIsInReachableRange(date);
        var locationDetails = weatherDataService.findBestLocationForGivenDate(date);
        return ResponseEntity.ok(locationDetails);
    }
}
