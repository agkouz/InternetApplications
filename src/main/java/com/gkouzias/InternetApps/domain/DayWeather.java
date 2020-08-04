package com.gkouzias.InternetApps.domain;


import com.gkouzias.InternetApps.domain.DayWeatherId.DayWeatherId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
*   TABLE -- holds total users of each weather_class, event_date
*       20190801 CLEAR 100
*       20190802 CLEAR 50
*       20191101 CLEAR 200
*       ...
* */

@NoArgsConstructor
@AllArgsConstructor
@Data                       // getters and setters for every col
@Entity
@IdClass(DayWeatherId.class)
@Table(name = "day_weather")
public class DayWeather {

    @Id
    @Column(name="event_date")
    private String eventDate;

    @Id
    @Column(name="weather_class")
    private String weatherClass;

    @Column(name="total_users")
    private long totalUsers;
}
