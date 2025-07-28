package com.dehlan.Journal.service;

import com.dehlan.Journal.Utilities.APIcache;
import com.dehlan.Journal.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    /*
    * We should not hardcode API key's in our Code, hardcoding will expose them to other users who have access to code
    * Instead we should inject them from properties/yml file using @Value annotation,
    * or we can store them in DB and load them using Application cache with the help of @PostConstruct
    *
    * @Value reads value from properties.yml file and inject it in the field annotated with @Value
    * @Value cannot inject values into static fields because Spring injects only into instance fields
    * managed by the container, and static fields belong to the class, not to any bean instance.
    * */

    private String url = "https://api.weatherstack.com/current?access_key=weatherAPIkey&query=New York";

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    APIcache apiCache;

    public int getTemperature(){
        /*
        * HttpEntity - study this class, used send body required for POST request
        * we can send header also using overloaded method of this class
        * */

        System.out.println(apiCache.map.get("weatherAPI"));
        String apiKey = url.replace("weatherAPIkey",apiCache.map.get("weatherAPI"));
        System.out.println(apiKey);
        WeatherResponse weatherResponse = restTemplate.exchange(apiKey, HttpMethod.GET, HttpEntity.EMPTY, WeatherResponse.class).getBody();
        WeatherResponse.Current current = weatherResponse.getCurrent();

        return current.getFeelslike();

    }

    @Bean
    public static RestTemplate getRT(){
        return new RestTemplate();
    }

}
