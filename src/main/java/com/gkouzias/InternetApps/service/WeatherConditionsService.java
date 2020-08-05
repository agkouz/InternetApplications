package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.Stop;
import com.gkouzias.InternetApps.domain.WeatherCondition;

public interface WeatherConditionsService {
    WeatherCondition save(WeatherCondition wc);         // save
    WeatherCondition findByStopWc(Stop origin_stop);    // find weather cond
}
