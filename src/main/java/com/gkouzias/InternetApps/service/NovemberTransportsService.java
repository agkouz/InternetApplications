package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.NovemberTransport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NovemberTransportsService {

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    List<NovemberTransport> findAll();
}
