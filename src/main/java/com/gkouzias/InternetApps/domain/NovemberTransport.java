package com.gkouzias.InternetApps.domain;

import com.gkouzias.InternetApps.domain.NovemberTransportId.NovemberTransportId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Data                       // getters and setters for every col
@Entity
@IdClass(NovemberTransportId.class)
@Table(name = "november_day_time_transport")
public class NovemberTransport {

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
