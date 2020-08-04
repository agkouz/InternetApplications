package com.gkouzias.InternetApps.service.impl;


import com.gkouzias.InternetApps.domain.Transport;
import com.gkouzias.InternetApps.repository.TransportRepository;
import com.gkouzias.InternetApps.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransportServiceImpl implements TransportService {
    @Autowired
    TransportRepository transportRepository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Transport> findAll() {
        return transportRepository.findAll();
    }

}
