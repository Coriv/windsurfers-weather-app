package com.project.weatherforwindsurfersapp.globalExceptionHandler;

import com.project.weatherforwindsurfersapp.exception.AvailableRangeExceededException;
import com.project.weatherforwindsurfersapp.exception.DailyDetailsDoesNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DailyDetailsDoesNotExistException.class)
    public ResponseEntity<Object> dailyDetailsDoesNotExistExceptionHandler(DailyDetailsDoesNotExistException e) {
        return new ResponseEntity<>("For given date we couldn't find any weather data.", NOT_FOUND);
    }

    @ExceptionHandler(AvailableRangeExceededException.class)
    public ResponseEntity<Object> availableRangeExceededExceptionHandler(AvailableRangeExceededException e) {
        return new ResponseEntity<>("We are unable to provide weather data for this day.\n", BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>("Pass argument in the format: \"yyyy-mm-dd\"", BAD_REQUEST);
    }

}
