package com.gkouzias.InternetApps.Errors;


import org.springframework.stereotype.Service;

@Service
public class StopError {

    public final String NEGATIVE_LAT = "stop.lat.neg";
    public final String OVER_LAT = "stop.lat.over.accepted";

    public final String NEGATIVE_LON = "stop.lon_neg";
    public final String OVER_LON = "stop.lon.over";

    public final String BAD_NAME = "stop.name.missing";

    public final String EXISTS = "stop.exists";

}
