package com.gkouzias.InternetApps.controller;


import com.gkouzias.InternetApps.domain.NovemberTransport;
import com.gkouzias.InternetApps.service.NovemberTransportsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/app3")
public class App3Controller {

    // http://147.102.16.56:8090/services/getWeatherDisc/20191105/15
    private String uri = "http://147.102.16.156:8090/services/getWeatherDisc/";

    @Autowired
    NovemberTransportsService novemberTransportsService;

    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("/november")
    ResponseEntity<?> viewNovemberTransports(){
        List<NovemberTransport> novemberTransports = novemberTransportsService.findAll();
        if(novemberTransports.isEmpty()) return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        else {
            RestTemplate restTemplate = new RestTemplate();
            for(NovemberTransport novemberTransport:novemberTransports){
                String time_period = String.valueOf((int)novemberTransport.getTimePeriod());
                if(time_period.length() == 1) time_period = "0" + time_period;  // accepted format

                final String nameURI = uri + novemberTransport.getEventDate() + "/" + time_period;
                String res = restTemplate.getForObject(nameURI, String.class);
                System.out.println(res);
            }

            return new ResponseEntity<>(novemberTransports, HttpStatus.OK);
        }

    }


}
