package com.project.weatherforwindsurfersapp.controller;

import com.project.weatherforwindsurfersapp.config.AdminConfig;
import com.project.weatherforwindsurfersapp.domain.LocationWeatherDto;
import com.project.weatherforwindsurfersapp.exception.AvailableRangeExceededException;
import com.project.weatherforwindsurfersapp.exception.DailyDetailsDoesNotExistException;
import com.project.weatherforwindsurfersapp.service.ValidateDateRangeService;
import com.project.weatherforwindsurfersapp.service.WeatherDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class LocationController {

    private final WeatherDataService weatherDataService;
    private final ValidateDateRangeService validationService;
    private final AdminConfig config;
    @GetMapping("/best-location/{date}")
    public ResponseEntity<LocationWeatherDto> fetchBestLocationForGivenDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws DailyDetailsDoesNotExistException, AvailableRangeExceededException {

        validationService.checkIfDateIsInReachableRange(date);
        var locationDetails = weatherDataService.findBestLocationForGivenDate(date);
        return ResponseEntity.ok(locationDetails);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<String>> fetchCheckedLocations() {
        return ResponseEntity.ok(config.getLocations());
    }

    @PostMapping("/add-location")
    public ResponseEntity<Void> addLocation(@RequestParam("city") String cityName) {
        config.getLocations().add(cityName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("remove-location")
    public ResponseEntity<Void> removeLocation(@RequestParam("city") String cityName) {
        config.getLocations().remove(cityName);
        return ResponseEntity.ok().build();
    }
}
