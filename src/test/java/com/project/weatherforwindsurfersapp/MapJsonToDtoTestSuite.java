package com.project.weatherforwindsurfersapp;

import com.project.weatherforwindsurfersapp.service.ExternalDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MapJsonToDtoTestSuite {

    @Autowired
    private ExternalDataService externalDataService;

    @Test
    void postForNewObjectTest() {
        //given & when
        var weatherForCity = externalDataService.fetchWeatherDataByCity("Eindhoven");
        //when
        assertNotNull(weatherForCity);
        assertEquals(weatherForCity.getCityName(), "Eindhoven");
        var dailyWeatherData = weatherForCity.getDailyDetailsList();
        assertEquals(dailyWeatherData.size(), 7);
        assertNotNull(dailyWeatherData.get(0).getWeatherDescription());
        assertNotNull(dailyWeatherData.get(0).getAverageTemp());
        assertNotNull(dailyWeatherData.get(0).getWindSpeed());
    }
}
