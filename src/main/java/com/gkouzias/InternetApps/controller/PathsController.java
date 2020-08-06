package com.gkouzias.InternetApps.controller;


import com.gkouzias.InternetApps.domain.Path;
import com.gkouzias.InternetApps.service.PathsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


// controller for paths, used by APP2

@Slf4j
@RestController
@RequestMapping(value = "/api/app2")
public class PathsController {
    @Autowired
    private PathsService pathsService;

    // returns all paths with weather conditions
    @CrossOrigin(origins = "http://localhost:8081")     // allow remote access from http://localhost:8081 [CORS]
    @GetMapping("")
    ResponseEntity<?> getPathsController(){
        List<Path> paths = pathsService.findAll();
        if(paths.isEmpty()) return new ResponseEntity<>(ResponseEntity.notFound().build(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(paths.stream().map(pathsService::calculate_coords_distance).collect(Collectors.toList()), HttpStatus.OK);
    }

}
