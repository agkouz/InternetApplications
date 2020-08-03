package com.gkouzias.InternetApps.service.impl;

import com.gkouzias.InternetApps.domain.NovemberTransport;
import com.gkouzias.InternetApps.repository.NovemberTransportRepository;
import com.gkouzias.InternetApps.service.NovemberTransportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovemberTransportsServiceImpl implements NovemberTransportsService {
    @Autowired
    NovemberTransportRepository novemberTransportRepository;


    @Override
    public List<NovemberTransport> findAll() {
        return novemberTransportRepository.findAll();
    }
}
