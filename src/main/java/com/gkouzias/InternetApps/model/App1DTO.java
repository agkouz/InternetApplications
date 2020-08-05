package com.gkouzias.InternetApps.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gkouzias.InternetApps.domain.Stop;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class App1DTO {

    private int id;
    private String name;
    private double lat;
    private double lon;
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

}
