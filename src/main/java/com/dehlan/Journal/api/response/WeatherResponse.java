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

    //Request
    //location
    //Current
    // only above 3  classes needed


    // avoid snake case use camel case -? @JSONProperty("")


    // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */

    public Current current;

    @Data
    @NoArgsConstructor
    @Getter
    @Setter
    public class Current{

        @JsonProperty("observation_time")
        public String observationTime;
        public int temperature;
        public int feelslike;

    }

}
