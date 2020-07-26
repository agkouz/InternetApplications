package com.gkouzias.InternetApps.domain;


import com.gkouzias.InternetApps.validation.StopValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


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

    @Column(name="lon")
    private double lon;

    @Column(name="lat")
    private double lat;
}
