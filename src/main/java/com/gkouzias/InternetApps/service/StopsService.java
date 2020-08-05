package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.Stop;

import java.util.List;

public interface StopsService {
    Stop save(Stop stop);   // save object @ DB
    List<Stop> findAll();
    Stop findById(int id);
}
