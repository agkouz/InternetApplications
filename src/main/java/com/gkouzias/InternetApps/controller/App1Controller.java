package com.gkouzias.InternetApps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.model.App1DTO;
import com.gkouzias.InternetApps.service.StopsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/app1")
public class App1Controller {


    @Autowired
    private StopsService stopsService;


    // get all stops
    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("")
    ResponseEntity<?> viewStopsController() throws JsonProcessingException {
        List<Stop> stops = stopsService.findAll();
        ModelMapper m = new ModelMapper();

        if(stops.isEmpty())  return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        else {
            List<App1DTO> res = new ArrayList<App1DTO>();
            for(Stop stop:stops) res.add(new App1DTO(stop));
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

    }

    // get speciffic stop information
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/{stop_id}")
    ResponseEntity<?> viewStop(@PathVariable int stop_id){
        Stop stop = stopsService.findById(stop_id);
        if(stop == null) return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(new App1DTO(stop), HttpStatus.OK);
    }


}
