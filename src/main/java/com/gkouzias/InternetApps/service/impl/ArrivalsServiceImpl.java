package com.gkouzias.InternetApps.service.impl;

import com.gkouzias.InternetApps.domain.Arrival;
import com.gkouzias.InternetApps.repository.ArrivalsRepository;
import com.gkouzias.InternetApps.service.ArrivalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArrivalsServiceImpl implements ArrivalsService {

    @Autowired
    ArrivalsRepository arrivalsRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Arrival save(Arrival arrival) {
        return arrivalsRepository.save(arrival);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Arrival> findAll() {
        return arrivalsRepository.findAll();
    }

}
