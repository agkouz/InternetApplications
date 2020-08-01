package com.gkouzias.InternetApps.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data                       // getters and setters for every col
@Entity
@Table(name="paths")
public class Path {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    // one stop is used as origin at many paths
    // a path has only one stop as origin (cannot have > 1)
    @ManyToOne
    @JoinColumn(name="origin", referencedColumnName = "id")
    Stop origin_stop;

    @ManyToOne
    @JoinColumn(name="destination", referencedColumnName = "id")
    Stop destination_stop;

    @Column(name="polyline")
    String polyline;

}
