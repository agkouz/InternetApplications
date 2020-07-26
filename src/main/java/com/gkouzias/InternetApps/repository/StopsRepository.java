package com.gkouzias.InternetApps.repository;

import com.gkouzias.InternetApps.domain.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StopsRepository extends JpaRepository<Stop, Integer> {

    // extra stuff
    boolean existsByName(String name);                              // for duplicates

    Optional<Stop> findById(int id);                                // find stop by id
    Optional<Stop> findByName(String name);                         // find stop by name
    List<Stop> findByNameStartingWith(String name);       // find stops starting with ..

    Optional<Stop> findByNameAndLat(String name, double lat);       // name,lat search

}
