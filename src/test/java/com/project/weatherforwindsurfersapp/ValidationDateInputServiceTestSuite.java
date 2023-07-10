package com.project.weatherforwindsurfersapp;

import com.project.weatherforwindsurfersapp.config.AdminConfig;
import com.project.weatherforwindsurfersapp.exception.AvailableRangeExceededException;
import com.project.weatherforwindsurfersapp.service.ValidateDateRangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationDateInputServiceTestSuite {

    @InjectMocks
    private ValidateDateRangeService validateDateRangeService;
    @Mock
    private AdminConfig adminConfig;

    @Test
    void checkIsDateInTheReachableRangeTest() throws AvailableRangeExceededException {
        //given
        LocalDate today = LocalDate.now();
        when(adminConfig.maxLimitForFreeLicence()).thenReturn(7l);
        //when & then
        assertDoesNotThrow(() -> validateDateRangeService.checkIfDateIsInReachableRange(today));
        assertDoesNotThrow(() -> validateDateRangeService.checkIfDateIsInReachableRange(today.plusDays(7)));
        assertThrows(AvailableRangeExceededException.class,
                () -> validateDateRangeService.checkIfDateIsInReachableRange(today.minusDays(1)));
        assertThrows(AvailableRangeExceededException.class,
                () -> validateDateRangeService.checkIfDateIsInReachableRange(today.plusDays(8)));
    }
}
