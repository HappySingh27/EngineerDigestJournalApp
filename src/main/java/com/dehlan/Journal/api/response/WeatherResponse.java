package com.dehlan.Journal.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class WeatherResponse {

    public Current current;

    @Data
    @NoArgsConstructor
    @Getter
    @Setter
    public class Current{

        @JsonProperty("observation_time") // observation_time - name of field in jsonResponse, we are updating name to onservationTime to avoid snake_Case
        public String observationTime;
        public int temperature;
        public int feelslike;

    }

}
