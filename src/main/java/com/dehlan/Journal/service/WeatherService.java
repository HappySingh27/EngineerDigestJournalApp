package com.dehlan.Journal.service;

import com.dehlan.Journal.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    //Rest Template use krenge API hit krne k liye then JSON response ko Javaclass me convert krenge then
    //stored it in a object of Java class

    // User Conroller ke get me temperature bhejo

    // Rest template k liye ek method bana pdega jo uski bean lakkar degi.

    private static final String url = "https://api.weatherstack.com/current?access_key=a2489b6541ed95105459dce2faeb4d35&query=New York";

    @Autowired
    RestTemplate restTemplate;

    public int getTemperature(){
        WeatherResponse weatherResponse = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, WeatherResponse.class).getBody();
        WeatherResponse.Current current = weatherResponse.getCurrent();

        return current.getFeelslike();

    }

    @Bean
    public static RestTemplate getRT(){
        return new RestTemplate();
    }

}
