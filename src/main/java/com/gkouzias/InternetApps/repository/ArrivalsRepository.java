package com.gkouzias.InternetApps.repository;

import com.gkouzias.InternetApps.domain.Arrival;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArrivalsRepository extends JpaRepository<Arrival, Integer> {
}
