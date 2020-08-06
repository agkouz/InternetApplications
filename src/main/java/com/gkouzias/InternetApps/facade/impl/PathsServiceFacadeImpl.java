package com.gkouzias.InternetApps.facade.impl;

import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.domain.WeatherCondition;
import com.gkouzias.InternetApps.facade.PathsServiceFacade;
import com.gkouzias.InternetApps.service.StopsService;
import com.gkouzias.InternetApps.service.WeatherConditionsService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class PathsServiceFacadeImpl implements PathsServiceFacade {

    @Autowired
    StopsService stopsService;
    @Autowired
    WeatherConditionsService weatherConditionsService;


    @Scheduled(fixedDelay = 5*60*1000)
    public void weatherUpdate() {
        log.info("start - weather update");

        final String api_key = "c6f4574a00f81f656b556669e116595e";
        RestTemplate restTemplate = new RestTemplate();
        List<Stop> stops = stopsService.findAll();
        stops.forEach(stop -> {
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

        });

        log.info("end - weather update");
    }
}
