package com.gkouzias.InternetApps.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class App1DTO {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private double distance;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;


    public App1DTO(double distance, String timestamp) {
        this.distance = distance;
        if(!timestamp.isEmpty()){
            String tokens[] = timestamp.split("[.]");
            tokens = tokens[0].split("\"");
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.timestamp = LocalDateTime.parse(tokens[1], format);
        }
    }
}
