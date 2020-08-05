package com.gkouzias.InternetApps.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gkouzias.InternetApps.domain.Path;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class App2DTO {

    // PATH
    private int id;
    private String name;
    private double total_distance;

    // ORIGIN STOP
    private int origin_id;
    private String origin_name;
    private double origin_lat;
    private double origin_lon;
    private String origin_weather_main;
    private String origin_weather_desc;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime origin_next_arrival_at;

    // DESTINATION STOP
    private int dest_id;
    private String dest_name;
    private double dest_lat;
    private double dest_lon;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dest_next_arrival_at;

    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<Point> coordinates;


    @Data
    @Getter
    @AllArgsConstructor
    private class Point{
        private double lat;
        private double lon;
    }


    /**
     * lat1, lon1 Start point lat2, lon2 End point
     * @returns Distance in Meters
     */
    private void calc_dst(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        this.total_distance += distance;
    }




    public App2DTO(Path path) {

        //PATH
        this.id = path.getId();
        this.name = path.getName();


        // ORIGIN STOP
        this.origin_id = path.getOrigin_stop().getId();
        this.origin_name = path.getOrigin_stop().getName();
        this.origin_lat = path.getOrigin_stop().getLat();
        this.origin_lon = path.getOrigin_stop().getLon();
        this.origin_next_arrival_at = path.getOrigin_stop().getArrival().getLast_arrival().plusMinutes(path.getOrigin_stop().getArrival().getIn()); // calculate next arrival
        this.origin_weather_main = path.getOrigin_stop().getWeatherCondition().getWeather_main();
        this.origin_weather_desc = path.getOrigin_stop().getWeatherCondition().getWeather_desc();

        // DESTINATION STOP
        this.dest_id = path.getDestination_stop().getId();
        this.dest_name = path.getDestination_stop().getName();
        this.dest_lat = path.getDestination_stop().getLat();
        this.dest_lon = path.getDestination_stop().getLon();
        this.dest_next_arrival_at = path.getDestination_stop().getArrival().getLast_arrival().plusMinutes(path.getDestination_stop().getArrival().getIn()); // calculate next arrival


        // calculate distance
        // -------------------------------------------------------------------------------------------------------------
        this.coordinates = new ArrayList<>();
        String coords[] = path.getPolyline().split(" "); // split polyline string given using space
        this.total_distance = 0;   // init total distance
        double prev_lat = this.origin_lat;  // init starting point
        double prev_lon = this.origin_lon;

        for(String coord:coords){
            String num_tokens[] = coord.split(",");
            if(num_tokens.length > 1) {
                calc_dst(prev_lat, prev_lon, Double.parseDouble(num_tokens[1]), Double.parseDouble(num_tokens[0]));
                prev_lat = Double.parseDouble(num_tokens[1]);
                prev_lon = Double.parseDouble(num_tokens[0]);
                this.coordinates.add(new Point(prev_lat, prev_lon));
            }
        }
        // -------------------------------------------------------------------------------------------------------------

    }
}
