package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.Stop;

import java.util.List;

public interface StopsService {
    Stop save(Stop stop);   // save
    List<Stop> findAll();   // all
    Stop findById(int id);  // by id
}
