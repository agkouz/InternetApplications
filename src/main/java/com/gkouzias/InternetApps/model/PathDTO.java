package com.gkouzias.InternetApps.model;


import lombok.Data;

@Data                       // getters and setters for every col

public class PathDTO {
    private int id;
    private String name;
    StopDTO origin_stop;
    StopDTO destination_stop;
    String polyline;
}
