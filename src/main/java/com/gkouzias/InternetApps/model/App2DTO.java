package com.gkouzias.InternetApps.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gkouzias.InternetApps.domain.Path;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class App2DTO {

    // PATH
    private int id;
    private String name;
    private String polyline;

    // ORIGIN STOP
    private int origin_id;
    private String origin_name;
    private double origin_lat;
    private double origin_lon;
    private int origin_next_arrival_in;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime origin_last_arrival_at;

    // DESTINATION STOP
    private int dest_id;
    private String dest_name;
    private double dest_lat;
    private double dest_lon;
    private int dest_next_arrival_in;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dest_last_arrival_at;

    public App2DTO(Path path) {
        this.id = path.getId();
        this.name = path.getName();
        this.polyline = path.getPolyline();

        this.origin_id = path.getOrigin_stop().getId();
        this.origin_name = path.getOrigin_stop().getName();
        this.origin_lat = path.getOrigin_stop().getLat();
        this.origin_lon = path.getOrigin_stop().getLon();
        this.origin_next_arrival_in = path.getOrigin_stop().getArrival().getIn();
        this.origin_last_arrival_at = path.getOrigin_stop().getArrival().getLast_arrival();

        this.dest_id = path.getDestination_stop().getId();
        this.dest_name = path.getDestination_stop().getName();
        this.dest_lat = path.getDestination_stop().getLat();
        this.dest_lon = path.getDestination_stop().getLon();
        this.dest_next_arrival_in = path.getDestination_stop().getArrival().getIn();
        this.dest_last_arrival_at = path.getDestination_stop().getArrival().getLast_arrival();
    }
}
