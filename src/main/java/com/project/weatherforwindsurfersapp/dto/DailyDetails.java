package com.project.weatherforwindsurfersapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyDetails {
    @JsonProperty("app_max_temp")
    private double apparentMaxTemp;
    @JsonProperty("app_min_temp")
    private double apparentMinTemp;
    @JsonProperty("clouds")
    private double clouds;
    @JsonProperty("clouds_hi")
    private double cloudsHigh;
    @JsonProperty("clouds_low")
    private double cloudsLow;
    @JsonProperty("clouds_mid")
    private double cloudsMid;
    @JsonProperty("datetime")
    private String dateTime;
    @JsonProperty("dewpt")
    private double averageDewPoint;
    @JsonProperty("high_temp")
    private double highestTemp;
    @JsonProperty("low_temp")
    private double lowestTemp;
    @JsonProperty("max_temp")
    private double maxTemp;
    @JsonProperty("min_temp")
    private double minTemp;
    @JsonProperty("moon_phase")
    private double moonPhase;
    @JsonProperty("moon_phase_lunation")
    private double moonPhaseFraction;
    @JsonProperty("moonrise_ts")
    private long moonriseTimestamp;
    @JsonProperty("moonset_ts")
    private long moonsetTimestamp;
    @JsonProperty("ozone")
    private double ozone;
    @JsonProperty("pop")
    private double chanceOfRain;
    @JsonProperty("precip")
    private double accumulatedPrecipitation;
    @JsonProperty("pres")
    private double pressure;
    @JsonProperty("rh")
    private double humidity;
    @JsonProperty("slp")
    private double seeLevelPressure;
    @JsonProperty("snow")
    private double snowfall;
    @JsonProperty("snow_depth")
    private double snowDepth;
    @JsonProperty("sunrise_ts")
    private long sunriseTimestamp;
    @JsonProperty("sunset_ts")
    private long sunsetTimestamp;
    @JsonProperty("temp")
    private double averageTemp;
    @JsonProperty("ts")
    private long timestamp;
    @JsonProperty("uv")
    private double uvIndex;
    @JsonProperty("valid_date")
    private String validDate;
    @JsonProperty("vis")
    private double visibility;
    @JsonProperty("weather")
    private WeatherDescription weatherDescription;
    @JsonProperty("wind_cdir")
    private String windDirectionCode;
    @JsonProperty("wind_cdir_full")
    private String windDirectionDescription;
    @JsonProperty("wind_dir")
    private long windDirectionDegrees;
    @JsonProperty("wind_gust_spd")
    private double windGuestSpeed;
    @JsonProperty("wind_spd")
    private double windSpeed;
}
