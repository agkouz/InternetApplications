package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.Path;
import com.gkouzias.InternetApps.model.PathDTO;

import java.util.List;

public interface PathsService {
    List<Path> findAll();                           // all
    PathDTO calculate_coords_distance(Path path);   // creates PathDTOs
}
