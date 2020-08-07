package com.gkouzias.InternetApps.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data                       // getters and setters for every col
@Entity
@Table(name="arrivals")
public class Arrival {
    @Id
    @Column(name="id")
    private int id;

    // foreign key `for_stop` column
    // references stops.id
    @OneToOne
    @JoinColumn(name="for_stop", referencedColumnName = "id",nullable = false)
    private Stop stop;

    @Column(name="next_in")
    private int in;

    @Column(name="last_arrival")
    private LocalDateTime last_arrival;

}
