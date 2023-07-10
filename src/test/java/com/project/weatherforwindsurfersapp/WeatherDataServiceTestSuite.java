package com.project.weatherforwindsurfersapp;

import com.project.weatherforwindsurfersapp.config.AdminConfig;
import com.project.weatherforwindsurfersapp.dto.DailyDetails;
import com.project.weatherforwindsurfersapp.dto.WeatherData;
import com.project.weatherforwindsurfersapp.exception.DailyDetailsDoesNotExistException;
import com.project.weatherforwindsurfersapp.service.ExternalDataService;
import com.project.weatherforwindsurfersapp.service.WeatherDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherDataServiceTestSuite {

    @InjectMocks
    private WeatherDataService weatherService;
    @Mock
    private AdminConfig config;
    @Mock
    private ExternalDataService externalDataService;
    private List<String> cities;
    private WeatherData krakowData;
    private WeatherData warsawData;
    private WeatherData poznanData;

    @BeforeEach
    void setUp() {
        //data
        cities = Arrays.asList("Krakow", "Warsaw", "Poznan");

        krakowData = new WeatherData();
        krakowData.setCityName("Krakow");
        warsawData = new WeatherData();
        warsawData.setCityName("Warsaw");
        poznanData = new WeatherData();
        poznanData.setCityName("Poznan");

        var day1 = LocalDate.now().toString();
        var day2 = LocalDate.now().plusDays(1).toString();
        var day3 = LocalDate.now().plusDays(2).toString();

        DailyDetails krakowDay1 = new DailyDetails();
        DailyDetails warsawDay1 = new DailyDetails();
        DailyDetails poznanDay1 = new DailyDetails();

        krakowDay1.setAverageTemp(30.0);
        warsawDay1.setAverageTemp(17.0);
        poznanDay1.setAverageTemp(12.0);

        krakowDay1.setWindSpeed(17.5);
        warsawDay1.setWindSpeed(7.5);
        poznanDay1.setWindSpeed(33.5);

        krakowDay1.setValidDate(day1);
        warsawDay1.setValidDate(day1);
        poznanDay1.setValidDate(day1);

        DailyDetails krakowDay2 = new DailyDetails();
        DailyDetails warsawDay2 = new DailyDetails();
        DailyDetails poznanDay2 = new DailyDetails();

        krakowDay2.setAverageTemp(23.0);
        warsawDay2.setAverageTemp(27.8);
        poznanDay2.setAverageTemp(23.8);

        krakowDay2.setWindSpeed(15.5);
        warsawDay2.setWindSpeed(1.5);
        poznanDay2.setWindSpeed(15.6);

        krakowDay2.setValidDate(day2);
        warsawDay2.setValidDate(day2);
        poznanDay2.setValidDate(day2);

        DailyDetails krakowDay3 = new DailyDetails();
        DailyDetails warsawDay3 = new DailyDetails();
        DailyDetails poznanDay3 = new DailyDetails();

        krakowDay3.setAverageTemp(21.0);
        warsawDay3.setAverageTemp(14.0);
        poznanDay3.setAverageTemp(22.0);

        krakowDay3.setWindSpeed(5.5);
        warsawDay3.setWindSpeed(15.5);
        poznanDay3.setWindSpeed(45.5);

        krakowDay3.setValidDate(day3);
        warsawDay3.setValidDate(day3);
        poznanDay3.setValidDate(day3);

        krakowData.setDailyDetailsList(Arrays.asList(krakowDay1, krakowDay2, krakowDay3));
        warsawData.setDailyDetailsList(Arrays.asList(warsawDay1, warsawDay2, warsawDay3));
        poznanData.setDailyDetailsList(Arrays.asList(poznanDay1, poznanDay2, poznanDay3));
    }

    @Test
    void findBestLocationOnGivenDateTest() {
        //given
        var day1 = LocalDate.now();
        var day2 = LocalDate.now().plusDays(1);
        var day3 = LocalDate.now().plusDays(2);
        //mocking
        when(config.cities()).thenReturn(cities);
        when(externalDataService.fetchWeatherDataByCity("Krakow")).thenReturn(krakowData);
        when(externalDataService.fetchWeatherDataByCity("Warsaw")).thenReturn(warsawData);
        when(externalDataService.fetchWeatherDataByCity("Poznan")).thenReturn(poznanData);
        // when
        var resultDay1 = weatherService.findBestLocationForGivenDate(day1);
        var resultDay2 = weatherService.findBestLocationForGivenDate(day2);
        var resultDay3 = weatherService.findBestLocationForGivenDate(day3);
        //then
        assertEquals("Krakow", resultDay1.getCitiName());
        assertEquals(30.0, resultDay1.getAverageTemp());
        assertEquals(17.5, resultDay1.getWindSpeed());
        assertEquals("Poznan", resultDay2.getCitiName());
        assertEquals(23.8, resultDay2.getAverageTemp());
        assertEquals(15.6, resultDay2.getWindSpeed());
        assertEquals("Warsaw", resultDay3.getCitiName());
        assertEquals(14.0, resultDay3.getAverageTemp());
        assertEquals(15.5, resultDay3.getWindSpeed());
        verify(config, times(3)).cities();
        verify(externalDataService, times(9)).fetchWeatherDataByCity(anyString());
    }

    @Test
    void calculateWeatherScoreTest() {
        //given
        var day1 = LocalDate.now();
        var day2 = LocalDate.now().plusDays(1);
        var day3 = LocalDate.now().plusDays(2);
        //mocking
        when(config.cities()).thenReturn(cities);
        when(externalDataService.fetchWeatherDataByCity("Krakow")).thenReturn(krakowData);
        when(externalDataService.fetchWeatherDataByCity("Warsaw")).thenReturn(warsawData);
        when(externalDataService.fetchWeatherDataByCity("Poznan")).thenReturn(poznanData);
        //when
        var resultDay1 = weatherService.findBestLocationForGivenDate(day1);
        var resultDay2 = weatherService.findBestLocationForGivenDate(day2);
        var resultDay3 = weatherService.findBestLocationForGivenDate(day3);
        //then
        assertEquals(82.5, resultDay1.getTotalScore());
        assertEquals(70.6, resultDay2.getTotalScore());
        assertEquals(60.5, resultDay3.getTotalScore());
        verify(config, times(3)).cities();
        verify(externalDataService, times(3)).fetchWeatherDataByCity("Warsaw");
    }

    @Test
    void dailyDetailsDoesNotExistForGivenDateTest() {
        //given
        var earlierDate = LocalDate.now().minusDays(10);
        var laterDate = LocalDate.now().plusDays(22);
        //mocking
        when(config.cities()).thenReturn(cities);
        when(externalDataService.fetchWeatherDataByCity("Krakow")).thenReturn(krakowData);
        when(externalDataService.fetchWeatherDataByCity("Warsaw")).thenReturn(warsawData);
        when(externalDataService.fetchWeatherDataByCity("Poznan")).thenReturn(poznanData);
        //when & then
        assertThrows(DailyDetailsDoesNotExistException.class,
                () -> weatherService.findBestLocationForGivenDate(earlierDate));
        assertThrows(DailyDetailsDoesNotExistException.class,
                () -> weatherService.findBestLocationForGivenDate(laterDate));
    }

    @Test
    void thereIsNoAnyGoodPlaceToWindsurfingBecauseOfTempTest() {
        //given
        var day1 = LocalDate.now();
        krakowData.getDailyDetailsList().get(0).setAverageTemp(4.99);
        warsawData.getDailyDetailsList().get(0).setAverageTemp(35.01);
        poznanData.getDailyDetailsList().get(0).setAverageTemp(-14.5);
        //mocking
        when(config.cities()).thenReturn(cities);
        when(externalDataService.fetchWeatherDataByCity("Krakow")).thenReturn(krakowData);
        when(externalDataService.fetchWeatherDataByCity("Warsaw")).thenReturn(warsawData);
        when(externalDataService.fetchWeatherDataByCity("Poznan")).thenReturn(poznanData);
        //when
        var location = weatherService.findBestLocationForGivenDate(day1);
        //then;
        assertNull(location);
        verify(config, times(1)).cities();
        verify(externalDataService, times(3)).fetchWeatherDataByCity(anyString());
    }

    @Test
    void thereIsNoAnyGoodPlaceToWindsurfingBecauseOfWindTest() {
        //given
        var day1 = LocalDate.now();
        krakowData.getDailyDetailsList().get(0).setWindSpeed(4.99);
        warsawData.getDailyDetailsList().get(0).setWindSpeed(18.01);
        poznanData.getDailyDetailsList().get(0).setWindSpeed(-14.5);
        //mocking
        when(config.cities()).thenReturn(cities);
        when(externalDataService.fetchWeatherDataByCity("Krakow")).thenReturn(krakowData);
        when(externalDataService.fetchWeatherDataByCity("Warsaw")).thenReturn(warsawData);
        when(externalDataService.fetchWeatherDataByCity("Poznan")).thenReturn(poznanData);
        //when
        var location = weatherService.findBestLocationForGivenDate(day1);
        //then
        assertNull(location);
        verify(config, times(1)).cities();
        verify(externalDataService, times(3)).fetchWeatherDataByCity(anyString());
    }
}
