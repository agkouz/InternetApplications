package com.gkouzias.InternetApps.controller;


import com.gkouzias.InternetApps.domain.Path;
import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.domain.WeatherCondition;
import com.gkouzias.InternetApps.model.App2DTO;
import com.gkouzias.InternetApps.service.PathsService;
import com.gkouzias.InternetApps.service.StopsService;
import com.gkouzias.InternetApps.service.WeatherConditionsService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/app2")
public class App2Controller {
    @Autowired
    private PathsService pathsService;

    private String api_key = "c6f4574a00f81f656b556669e116595e";

    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("")
    ResponseEntity<?> viewStopsController(){
        List<Path> paths = pathsService.findAll();
        if(paths.isEmpty()) return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        else {
            List<App2DTO> app2DTOs = new ArrayList<>();
            for(Path path:paths)  app2DTOs.add(new App2DTO(path));
            return new ResponseEntity<>(app2DTOs, HttpStatus.OK);
        }

    }


    @Autowired
    private WeatherConditionsService weatherConditionsService;

    @Autowired
    private StopsService stopsService;


    // call weather api each 5 minutes to update our db with weather conditions
    @Scheduled(fixedDelay = 5*60*1000)
    public void weatherUpdate() {
        System.out.println("start");
        RestTemplate restTemplate = new RestTemplate();
        List<Stop> stops = stopsService.findAll();
        for(Stop stop:stops){
            // for each stop do a request using lat and lon to get it's weather info
            final String nameURI = "http://api.openweathermap.org/data/2.5/weather?lat="+stop.getLat()+"&lon="+stop.getLon()+"&APPID="+api_key;
            String res = restTemplate.getForObject(nameURI, String.class);
            JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();

            // get main and desc
            String weather_main = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").toString();
            String weather_desc = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").toString();
            weather_main = weather_main.replace("\"",""); weather_main = weather_main.replace("\"","");
            weather_desc = weather_desc.replace("\"", ""); weather_desc = weather_desc.replace("\"", "");

            // update each stop weather condition
            WeatherCondition wc = weatherConditionsService.findByStopWc(stop);
            wc.setWeather_desc(weather_desc);
            wc.setWeather_main(weather_main);
            weatherConditionsService.save(wc);
        }
        System.out.println("stop");
    }


    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("/test")
    ResponseEntity<?> test(){
        //WeatherCondition weatherCondition = weatherConditionService.findByStopWc(11);
        Stop origin = stopsService.findById(11);
        WeatherCondition weatherCondition = weatherConditionsService.findByStopWc(origin);
        weatherCondition.setWeather_main("aaa");
        weatherConditionsService.save(weatherCondition);
        return new ResponseEntity<>("a", HttpStatus.OK);
    }


}
