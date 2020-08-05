package com.gkouzias.InternetApps.model;


import com.gkouzias.InternetApps.domain.Stop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

/*
*   Stop Entity Model
*   Each one will hold an Arrival Entity Model
*   that holds the next arrival
* */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StopDTO {
    private int id;
    private String name;
    private double lat;
    private double lon;
    private ArrivalDTO arrival;
    private LocalDateTime next_arrival;


    public StopDTO(Stop stop){
        ModelMapper m = new ModelMapper();

        this.id = stop.getId();
        this.name = stop.getName();
        this.lat = stop.getLat();
        this.lon = stop.getLon();
        this.arrival = m.map(stop.getArrival(), ArrivalDTO.class);

        this.next_arrival = stop.getArrival().getLast_arrival().plusMinutes(stop.getArrival().getIn());

    }
}
