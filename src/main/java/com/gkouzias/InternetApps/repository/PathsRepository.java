package com.gkouzias.InternetApps.repository;

import com.gkouzias.InternetApps.domain.Path;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PathsRepository extends JpaRepository<Path, Integer> {
    List<Path> findAll();
}
