package com.gkouzias.InternetApps.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class DayWeatherDTO {

    private String weather_class;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate event_date;
    private String day;
    private int total;


    public DayWeatherDTO(String weather_class, int total, String event_date) {
        this.weather_class = weather_class;
        this.total = total;

        String date = event_date.substring(0,4) + "-" + event_date.substring(4,6) + "-" + event_date.substring(6,8);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.event_date = LocalDate.parse(date, formatter);
        day = this.event_date.getDayOfWeek().name();


    }
}
