package com.gkouzias.InternetApps.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data                       // getters and setters for every col

public class PathDTO {
    private int id;
    private String name;
    private StopDTO origin_stop;
    private StopDTO destination_stop;
    private double total_distance;


    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<PointDTO> coordinates = new ArrayList<>();

}
