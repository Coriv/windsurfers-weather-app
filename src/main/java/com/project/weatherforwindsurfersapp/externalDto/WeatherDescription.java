package com.project.weatherforwindsurfersapp.externalDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDescription {

    private long code;
    private String icon;
    private String description;
}
