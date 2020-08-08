package com.gkouzias.InternetApps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.model.StopDTO;
import com.gkouzias.InternetApps.service.StopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


// controller for stops used by APP1

@RestController
@RequestMapping(value = "/api/app1")
public class StopsController {

    @Autowired
    private StopsService stopsService;


    // get all stops
    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("/getStops/")
    ResponseEntity<?> getStopsController() throws JsonProcessingException {
        List<Stop> stops = stopsService.findAll();
        if(stops.isEmpty()) return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(stops.stream().map(StopDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    // get speciffic stop information
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/getStops/{stop_id}")
    ResponseEntity<?> getStopController(@PathVariable int stop_id){
        return new ResponseEntity<>(new StopDTO(stopsService.findById(stop_id)), HttpStatus.OK);
    }


}
