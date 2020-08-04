package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.DayWeather;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DayWeatherService {
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    DayWeather save(DayWeather dw);   // save object @ DB

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    List<DayWeather> findAll();

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    List<?> findAllSummed();
}
