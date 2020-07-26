package com.gkouzias.InternetApps.model;


import lombok.Data;

@Data
public class StopDTO {
    private int id;
    private String name;
    private double lon;
    private double lat;
}
