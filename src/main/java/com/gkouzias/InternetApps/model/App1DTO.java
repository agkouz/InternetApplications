package com.gkouzias.InternetApps.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gkouzias.InternetApps.domain.Stop;

import java.time.LocalDateTime;



public class App1DTO {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private double lat;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private double lon;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private int next_arrival_in;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime last_arrival_at;

    public App1DTO(Stop stop) {
        this.id = stop.getId();
        this.name = stop.getName();
        this.lat = stop.getLat();
        this.lon = stop.getLon();
        this.next_arrival_in = stop.getArrival().getIn();
        this.last_arrival_at = stop.getArrival().getLast_arrival();
    }

    /*
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private double distance;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;


    public App1DTO(double distance, String timestamp) {
        this.distance = distance;
        if(!timestamp.isEmpty()){
            String tokens[] = timestamp.split("[.]");
            tokens = tokens[0].split("\"");
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.timestamp = LocalDateTime.parse(tokens[1], format);
        }
    }

     */
}
