package com.gkouzias.InternetApps.service.impl;

import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.repository.StopsRepository;
import com.gkouzias.InternetApps.service.StopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StopsServiceImpl implements StopsService {

    @Autowired
    private StopsRepository stopsRepository;

    // SAVE STOP
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Stop save(Stop stop) {
        return stopsRepository.save(stop);
    }

    // EXISTS BY NAME
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public boolean existsByName(String name) {
        return stopsRepository.existsByName(name);
    }


    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public Stop findByNameAndLat(String name, double lat) {
        return stopsRepository.findByNameAndLat(name, lat).orElseThrow(() -> new RuntimeException("find by name and lat xxx"));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Stop> findByNameStartingWith(String name) {
        return stopsRepository.findByNameStartingWith(name);
    }





}
