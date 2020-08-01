package com.gkouzias.InternetApps.controller;


import com.gkouzias.InternetApps.domain.Path;
import com.gkouzias.InternetApps.model.App2DTO;
import com.gkouzias.InternetApps.service.PathsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
