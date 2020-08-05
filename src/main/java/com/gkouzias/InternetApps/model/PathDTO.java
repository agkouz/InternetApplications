package com.gkouzias.InternetApps.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/*
*   PathDTO will return
*       > path id
*       > path name
*       > path origin stop ( StopDTO )
*       > path destination stop ( StopDTO )
*       > path total distance ( calculated from service )
*       > path polyline coordinates ( calculated from service )
*
* */
@Data
public class PathDTO {
    private int id;
    private String name;
    private StopDTO origin_stop;
    private StopDTO destination_stop;
    private double total_distance;

    // must be json array for leaflet to draw
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<PointDTO> coordinates = new ArrayList<>();


}
