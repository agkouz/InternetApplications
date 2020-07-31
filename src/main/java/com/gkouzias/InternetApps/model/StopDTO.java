package com.gkouzias.InternetApps.model;


import lombok.Data;

/*
*   Stop Entity Model
*   Each one will hold an Arrival Entity Model
*   that holds the next arrival
* */

@Data
public class StopDTO {
    private int id;
    private String name;
    private double lat;
    private double lon;
    private ArrivalDTO arrival;

}
