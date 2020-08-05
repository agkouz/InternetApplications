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


    // FIND STOP BY ID - return null if not found
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public Stop findById(int id) {
        return stopsRepository.findById(id).orElse(null);
    }

    // FIND ALL
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Stop> findAll() {
        return stopsRepository.findAll();
    }




}
