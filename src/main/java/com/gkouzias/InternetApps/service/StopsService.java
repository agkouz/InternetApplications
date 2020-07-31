package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.Stop;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StopsService {
    Stop save(Stop stop);   // save object @ DB


    // extra stuff
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    List<Stop> findAll();

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    boolean existsByName(String name);

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    Stop findById(int id);

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    Stop findByName(String name);


    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    List<Stop> findByNameStartingWith(String name);


    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    Stop findByNameAndLat(String name, double lat);



}
