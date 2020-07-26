package com.gkouzias.InternetApps.repository;

import com.gkouzias.InternetApps.domain.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StopsRepository extends JpaRepository<Stop, Integer> {

    // extra stuff
    boolean existsByName(String name);                              // for duplicates
    Optional<Stop> findByNameAndLat(String name, double lat);       // name,lat search
    List<Stop> findByNameStartingWith(String name);                 // starting with
}
