package com.gkouzias.InternetApps.model;

import lombok.Data;

import java.time.LocalDateTime;

/*
*   Arrival Entity Model
*   Will hold next arrival information
* */

@Data
public class ArrivalDTO {
    private int id;
    private int in;
    private LocalDateTime last_arrival;
}
