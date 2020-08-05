package com.gkouzias.InternetApps.repository;


import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.domain.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Integer> {
    // returns weather condition of given stop - used to update weather conditions of each stop
    Optional<WeatherCondition> findByStopWc(Stop origin_stop);
}
