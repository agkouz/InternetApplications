package com.gkouzias.InternetApps.controller;


import com.gkouzias.InternetApps.domain.Transport;
import com.gkouzias.InternetApps.model.DayWeatherDTO;
import com.gkouzias.InternetApps.service.DayWeatherService;
import com.gkouzias.InternetApps.service.TransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;


// controller for weather statistics used by APP3

@Slf4j
@RestController
@RequestMapping(value = "/api/app3")
public class WeatherStatisticsController {

    @Autowired
    TransportService transportService;

    @Autowired
    DayWeatherService dayWeatherService;


    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("/getData/{month}")
    ResponseEntity<?> getTransportsDataController(@PathVariable String month ){
        if(!month.equals("08") && !month.equals("11")) return new ResponseEntity<>(ResponseEntity.badRequest().build(), HttpStatus.BAD_REQUEST);
        else {
            // if day_weather is empty, we need to populate
            if(dayWeatherService.count() == 0){
                List<Transport> transports = transportService.findAll();
                if(transports.isEmpty()) return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
                dayWeatherService.populateDayWeather(transports);
            }

            // if day_weather is populated, find max transports accordingly
            String month1 = "____" + month + "__";
            String month2 = "____" + String.valueOf(Integer.parseInt(month) + 1) + "__";
            List<Tuple> transportationInfo = dayWeatherService.findMaxPerWeatherDay(month1, month2);
            return new ResponseEntity<>(transportationInfo.stream().map(t -> new DayWeatherDTO((String)t.get("weather_class"), (int)t.get("total"), (String)t.get("event_date")))
                    .collect(Collectors.toList()), HttpStatus.OK);
        }

    }


}
