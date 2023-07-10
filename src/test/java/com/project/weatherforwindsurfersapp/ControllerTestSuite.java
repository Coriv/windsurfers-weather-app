package com.project.weatherforwindsurfersapp;

import com.project.weatherforwindsurfersapp.controller.LocationController;
import com.project.weatherforwindsurfersapp.dto.LocationDto;
import com.project.weatherforwindsurfersapp.service.WeatherDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
@WebMvcTest(LocationController.class)
public class ControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WeatherDataService weatherDataService;

    @Test
    void shouldFetchLocationDtoTest() throws Exception {
        //given
        LocationDto location = LocationDto.builder()
                .citiName("Amsterdam")
                .totalScore(55.0)
                .averageTemp(33)
                .windSpeed(15.5)
                .date(LocalDate.of(2023, 07, 10))
                .latitude(4.54)
                .longitude(52.22)
                .build();
        when(weatherDataService.findBestLocationForGivenDate(any(LocalDate.class))).thenReturn(location);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/best-location/2023-07-10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.citiName", is("Amsterdam")))
                .andExpect(jsonPath("$.totalScore", is(55.0)))
                .andExpect(jsonPath("$.averageTemp", is(33.0)))
                .andExpect(jsonPath("$.windSpeed", is(15.5)))
                .andExpect(jsonPath("$.date", is("2023-07-10")))
                .andExpect(jsonPath("$.latitude", is(4.54)))
                .andExpect(jsonPath("$.longitude", is(52.22)));
    }

    @Test
    void shouldReturnEmptyBodyTest() throws Exception {
        //given
        when(weatherDataService.findBestLocationForGivenDate(any(LocalDate.class))).thenReturn(null);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/best-location/2023-07-10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
