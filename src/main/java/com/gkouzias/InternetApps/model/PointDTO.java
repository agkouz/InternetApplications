package com.gkouzias.InternetApps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
*   PointDTO is used by PathDTO for coordinates
*
* */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PointDTO {
    private double lat;
    private double lon;
}
