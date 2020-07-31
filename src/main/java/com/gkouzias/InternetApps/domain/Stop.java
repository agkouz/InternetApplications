package com.gkouzias.InternetApps.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Data                       // getters and setters for every col
@Entity
@Table(name="stops")
public class Stop {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="lat")
    private double lat;

    @Column(name="lon")
    private double lon;

    @OneToOne(mappedBy = "stop")
    private Arrival arrival;
}
