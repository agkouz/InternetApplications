package com.gkouzias.InternetApps.repository;


import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.domain.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Integer> {

    Optional<WeatherCondition> findById(int id);
    Optional<WeatherCondition> findByStopWc(Stop origin_stop);
}
