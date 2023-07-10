package com.project.weatherforwindsurfersapp;

import com.project.weatherforwindsurfersapp.config.AdminConfig;
import com.project.weatherforwindsurfersapp.controller.LocationController;
import com.project.weatherforwindsurfersapp.domain.LocationWeatherDto;
import com.project.weatherforwindsurfersapp.service.ValidateDateRangeService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @MockBean
    private ValidateDateRangeService validateDateRangeService;
    @MockBean
    private AdminConfig adminConfig;

    @Test
    void shouldFetchLocationDtoTest() throws Exception {
        //given
        LocalDate today = LocalDate.now();
        LocationWeatherDto location = LocationWeatherDto.builder()
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
                        .get("/api/v1/best-location/" + today)
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
    void shouldFetchEmptyBodyTest() throws Exception {
        //given
        LocalDate today = LocalDate.now();
        when(weatherDataService.findBestLocationForGivenDate(any(LocalDate.class))).thenReturn(null);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/best-location/" + today)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldReturnLocationList() throws Exception {
        //given
        when(adminConfig.getLocations()).thenReturn(Arrays.asList("Amsterdam", "Berlin", "Paris"));
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/locations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0]", is("Amsterdam")))
                .andExpect(jsonPath("$[1]", is("Berlin")))
                .andExpect(jsonPath("$[2]", is("Paris")));
    }

    @Test
    void shouldAddLocationTest() throws Exception {
        //given
        String newCity = "London";
        List<String> locations = new ArrayList<>(Arrays.asList("Amsterdam", "Berlin", "Paris"));
        when(adminConfig.getLocations()).thenReturn(locations);
        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1//add-location?city=" + newCity)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(adminConfig.getLocations().size(), 4);
    }

    @Test
    void shouldRemoveLocationTest() throws Exception {
        // given
        String removedCity = "Berlin";
        List<String> locations = new ArrayList<>(Arrays.asList("Amsterdam", "Berlin", "Paris"));
        when(adminConfig.getLocations()).thenReturn(locations);
        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/remove-location?city=" + removedCity)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
        assertEquals(adminConfig.getLocations().size(), 2);
    }
}
