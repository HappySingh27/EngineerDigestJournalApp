package com.dehlan.Journal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    /*
    * Since this class is annotated @RestController, therefore
    * return response of all the endpoints(methods) will by default
    * converted to JSON
    * */


    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }


}
