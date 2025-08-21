package com.aviral.journalApp.service;

import com.aviral.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private static final String API_KEY = "e950cec81a46e59701d93442db241521";

    private static final String API_URL = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalAPIUrl = API_URL.replace("CITY", city).replace("API_KEY", API_KEY);

        ResponseEntity<WeatherResponse> weatherResponse = restTemplate.exchange(finalAPIUrl, HttpMethod.GET, null, WeatherResponse.class);

        return weatherResponse.getBody();
    }
}
