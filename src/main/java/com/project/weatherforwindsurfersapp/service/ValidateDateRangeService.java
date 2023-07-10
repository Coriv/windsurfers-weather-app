package com.project.weatherforwindsurfersapp.service;

import com.project.weatherforwindsurfersapp.config.AdminConfig;
import com.project.weatherforwindsurfersapp.exception.AvailableRangeExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ValidateDateRangeService {

    private final AdminConfig config;

    public void checkIfDateIsInReachableRange(LocalDate date) throws AvailableRangeExceededException {
        if (!validDate(date)) {
            throw new AvailableRangeExceededException();
        }
    }

    private boolean validDate(LocalDate date) {
        var today = LocalDate.now();
        var limit = config.getMaxLimitForFreeLicence();
        boolean isToday = date.isEqual(today);
        boolean isAfterToday = date.isAfter(today);
        boolean isBeforeTheReachableExtent = date.isBefore(today.plusDays(limit + 1));

        return isToday || isAfterToday && isBeforeTheReachableExtent;
    }
}
