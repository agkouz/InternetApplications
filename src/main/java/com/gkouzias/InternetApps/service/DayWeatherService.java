package com.gkouzias.InternetApps.service;

import com.gkouzias.InternetApps.domain.DayWeather;

import javax.persistence.Tuple;
import java.util.List;

public interface DayWeatherService {
    DayWeather save(DayWeather dw);   // save object @ DB
    List<DayWeather> findAll();
    List<Tuple> findMaxPerWeatherDay(String month1, String month2);
}
