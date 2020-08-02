package com.gkouzias.InternetApps.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data                       // getters and setters for every col
@Entity
@Table(name="weather_conditions")
public class WeatherCondition {
    @Id
    @Column(name="id")
    private int id;


    @OneToOne
    @JoinColumn(name="for_origin_stop", referencedColumnName = "id")
    private Stop stopWc;

    @Column(name="weather_main")
    private String weather_main;

    @Column(name="weather_desc")
    private String weather_desc;


}
