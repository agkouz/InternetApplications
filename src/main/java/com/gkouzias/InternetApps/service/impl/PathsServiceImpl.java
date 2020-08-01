package com.gkouzias.InternetApps.service.impl;

import com.gkouzias.InternetApps.domain.Path;
import com.gkouzias.InternetApps.repository.PathsRepository;
import com.gkouzias.InternetApps.service.PathsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PathsServiceImpl implements PathsService {
    @Autowired
    PathsRepository pathsRepository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Path> findAll() {
        return pathsRepository.findAll();
    }
}