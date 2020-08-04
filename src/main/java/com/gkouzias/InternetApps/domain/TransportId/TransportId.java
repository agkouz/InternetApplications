package com.gkouzias.InternetApps.domain.TransportId;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class TransportId implements Serializable {
    private String eventDate;
    private double timePeriod;
}
