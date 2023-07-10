package com.project.weatherforwindsurfersapp.globalExceptionHandler;

import com.project.weatherforwindsurfersapp.exception.AvailableRangeExceededException;
import com.project.weatherforwindsurfersapp.exception.DailyDetailsDoesNotExistException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DailyDetailsDoesNotExistException.class)
    public ResponseEntity<Object> dailyDetailsDoesNotExistExceptionHandler(DailyDetailsDoesNotExistException e) {
        return new ResponseEntity<>("For given date we couldn't find any weather data.\n" +
                "Make sure you have entered the correct date int the format: \"yyyy-mm-dd\",\n" +
                "and that the date is in the extent of the nearest 7 days.", NOT_FOUND);
    }

    @Profile("test-validation")
    @ExceptionHandler(AvailableRangeExceededException.class)
    public ResponseEntity<Object> availableRangeExceededExceptionHandler(AvailableRangeExceededException e) {
        return new ResponseEntity<>("We are unable to provide data longer than 7 days .\n", BAD_REQUEST);
    }
}
