package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.Arrival;

import java.util.List;

public interface ArrivalsService {
    Arrival save(Arrival arrival);  // to update arrival

    List<Arrival> findAll();        // to get all arrivals

}
