package com.gkouzias.InternetApps.domain;


/*
*   VIEW -- holds total user transports grouped
*   by event_date and hour
*       20190801 00 2
*       20190801 01 24
*       20190801 02 34
*       20190801 03 234
*       ...
*
* */

import com.gkouzias.InternetApps.domain.TransportId.TransportId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data                       // getters and setters for every col
@Entity
@IdClass(TransportId.class)
@Table(name = "day_time_transports")
public class Transport {

    @Column(name="total_users")
    private int totalUsers;

    @Column(name="cell_municipality")
    private String cellMunicipality;

    @Column(name="prev_cell_municipality")
    private String pcellMunicipality;

    @Id
    @Column(name="event_date")
    private String eventDate;

    @Id
    @Column(name="time_period")
    private double timePeriod;

    @Column(name="week_day")
    private double weekDay;
}
