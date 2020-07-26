package com.gkouzias.InternetApps.Errors;


import lombok.Getter;
import org.springframework.stereotype.Service;


@Service
public class StopError {
    public final String object = "stop";

    public final String NEGATIVE_LAT = "stop.lat.neg";
    public final String OVER_LAT = "stop.lat.over.accepted";

    public final String NEGATIVE_LON = "stop.lon_neg";
    public final String OVER_LON = "stop.lon.over";

    public final String BAD_NAME = "stop.name.missing";
    public final String EXISTS = "stop.exists";


    public final String NAME_MSG = "Name should be a non empty string";
    public final String LAT_MSG = "Stop lat should be valid double between 0 and 40]";
    public final String LON_MSG = "Stop lon should be valid double between 0 and 40]";
    public final String EXISTS_MSG = "Stop with given name already exists";
}
