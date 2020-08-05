package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.Path;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PathsService {
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    List<Path> findAll();



}
