package com.gkouzias.InternetApps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gkouzias.InternetApps.Errors.ApiError;
import com.gkouzias.InternetApps.model.App1DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api/app1")
public class App1Controller {

    @GetMapping("@{lat}@{lon}/{stop_id}")
    ResponseEntity<?> getApp1Controller(@PathVariable double lat, @PathVariable double lon, @PathVariable int stop_id){
        double tar_lon, tar_lat = tar_lon = 0;
        RestTemplate restTemplate = new RestTemplate();

        // use the web service to find stop name
        // get stop name - name contains itravel id
        final String nameURI = "http://147.102.16.56:8080/services/getDeviceName/"+stop_id;
        String res1 = restTemplate.getForObject(nameURI, String.class);


        // use the web service to get stop coordinates
        // we will use the coordinates to get distance from the stop
        final String coorURI = "http://147.102.16.56:8080/services/getDeviceCoords/"+stop_id;
        String res2= restTemplate.getForObject(coorURI, String.class);



        // if stop id exists
        if(res2.contains(",")){

            // get target lat and lon
            String tokens[] = res2.split(",");
            tar_lat = Double.parseDouble(tokens[0]);
            tar_lon = Double.parseDouble(tokens[1]);

            double distance = Math.sqrt(Math.pow(tar_lat - lat, 2) + Math.pow(tar_lon - lon, 2));

            // get itravel-id
            tokens = res1.split("-");
            int itravel_id = Integer.parseInt(tokens[1]);

            // get trajectory for itravel-id if it exists
            final String trajURI = "http://147.102.16.56:8080/services/getItravelIdTrajectories/"+itravel_id;
            String res3 = restTemplate.getForObject(trajURI, String.class);
            try {
                // map the result - json
                ObjectMapper mapper = new ObjectMapper();
                JsonNode actualObj = mapper.readTree(res3);
                JsonNode obj = actualObj.get(0);

                // if trajectory exists, keep timestamp
                String timestamp = "";
                if(obj.has("timestamp")) timestamp = obj.get("timestamp").toString();

                // response
                return new ResponseEntity<>(new App1DTO(distance, timestamp), HttpStatus.OK);


            }catch (JsonProcessingException jpe){
                jpe.printStackTrace();
                return new ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, jpe), HttpStatus.INTERNAL_SERVER_ERROR);
            }


        }else{
            // not found response
            ApiError err = new ApiError(HttpStatus.NOT_FOUND, "Device id not found");
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }

    }





}
