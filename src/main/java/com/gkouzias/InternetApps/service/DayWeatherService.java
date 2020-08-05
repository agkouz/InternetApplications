package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.DayWeather;
import com.gkouzias.InternetApps.domain.Transport;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

public interface DayWeatherService {
    DayWeather save(DayWeather dw);   // save object @ DB
    List<DayWeather> findAll();
    List<Tuple> findMaxPerWeatherDay(String month1, String month2);

    Map<String, Long> populateDayWeather(List<Transport> transports);
}
