package com.gkouzias.InternetApps.controller;


import com.gkouzias.InternetApps.domain.DayWeather;
import com.gkouzias.InternetApps.domain.Transport;
import com.gkouzias.InternetApps.model.App3DTO;
import com.gkouzias.InternetApps.service.DayWeatherService;
import com.gkouzias.InternetApps.service.TransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/api/app3")
public class App3Controller {

    private String uri = "http://147.102.16.156:8090/services/getWeatherDisc/";
    
    @Autowired
    TransportService transportService;

    @Autowired
    DayWeatherService dayWeatherService;


    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("/populate")
    ResponseEntity<?> populateDayWeatherController(){
        List<Transport> transports = transportService.findAll();

        if(transports.isEmpty()) return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        else {
            RestTemplate restTemplate = new RestTemplate();
            HashMap<String, Long> dateUsers = new HashMap<>();
            for(Transport transport:transports){

                // accepted format for time period to call api
                String time_period = String.valueOf((int)transport.getTimePeriod());
                if(time_period.length() == 1) time_period = "0" + time_period;

                // call api foreach novemberTransport and get info
                //System.out.println("CALL -- " + transport.getEventDate() + " " + time_period);
                final String nameURI = uri + transport.getEventDate() + "/" + time_period;
                String res = restTemplate.getForObject(nameURI, String.class);

                //System.out.println("RES -- " + res);
                if(res == null) continue;
                res = "" + res.trim();


                long prev_users = 0;
                if(dateUsers.containsKey(transport.getEventDate()+";"+res)) prev_users = dateUsers.get(transport.getEventDate()+";"+res);
                dateUsers.put(transport.getEventDate()+";"+res, prev_users + transport.getTotalUsers());


            }
            dateUsers.forEach((k,v) -> {
                // insert into the database
                String tokens[] = k.split(";");
                DayWeather dw = new DayWeather(tokens[0], tokens[1], v);
                dayWeatherService.save(dw);
            });

            return new ResponseEntity<>(dateUsers, HttpStatus.OK);
        }

    }


    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("/getData/{month}")
    ResponseEntity<?> getTransportsDataController(@PathVariable String month ){
        if(!month.equals("08") && !month.equals("11")) return new ResponseEntity<>(ResponseEntity.badRequest().build(), HttpStatus.BAD_REQUEST);
        else {
            String month1 = "____" + month + "__";
            String month2 = "____" + String.valueOf(Integer.parseInt(month) + 1) + "__";
            List<Tuple> transportationInfo = dayWeatherService.findMaxPerWeatherDay(month1, month2);

            return new ResponseEntity<>(transportationInfo.stream().map(t -> new App3DTO((String)t.get("weather_class"), (int)t.get("total"), (String)t.get("event_date")))
                    .collect(Collectors.toList()), HttpStatus.OK);
        }


    }


}
