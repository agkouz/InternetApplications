package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.domain.WeatherCondition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface WeatherConditionsService {
    WeatherCondition save(WeatherCondition wc);
    WeatherCondition findByStopWc(Stop origin_stop);
}
